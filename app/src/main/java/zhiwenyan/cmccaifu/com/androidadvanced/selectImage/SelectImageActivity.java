package zhiwenyan.cmccaifu.com.androidadvanced.selectImage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.adapter.SelectImageListAdapter;

public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener, SelectImageListener {
    //带过来的key

    // 带过来的Key
    // 是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    // 总共可以选择多少张图片的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    // 原始的图片路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECTED_LIST = "EXTRA_DEFAULT_SELECTED_LIST";
    // 选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    // 返回选择图片列表的EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    // 加载所有的数据
    private static final int LOADER_TYPE = 0x0021;
    /*****************
     * 获取传递过来的参数
     *****************/
    // 选择图片的模式 - 多选
    public static final int MODE_MULTI = 0x0011;
    // 选择图片的模式 - 单选
    public static int MODE_SINGLE = 0x0012;
    //单选或者多选
    private int mMode = MODE_MULTI;
    // int类型的图片张数，
    private int mMaxCount = 8;
    // boolean类型是否显示拍照按钮
    private boolean mShowCamera = true;
    // ArrayList<String> 选择好的图片
    private ArrayList<String> mResultList;
    private RecyclerView mRecyclerView;
    private TextView mSelectNumTv;
    private TextView mSelectPreviewTv;
    private TextView mSelectFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        mRecyclerView = (RecyclerView) findViewById(R.id.image_list_rv);
        mSelectNumTv = (TextView) findViewById(R.id.select_num);
        mSelectPreviewTv = (TextView) findViewById(R.id.select_preview);
        mSelectFinish = (TextView) findViewById(R.id.select_finish);
        mSelectFinish.setOnClickListener(this);
        initData();
        //获取过来的参数
        //ContentProvider内存卡中获取本地图片
    }

    private void initData() {
        Intent intent = getIntent();
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE, mMode);
        mMaxCount = intent.getIntExtra(EXTRA_SELECT_COUNT, mMaxCount);
        mShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, mShowCamera);
        mResultList = intent.getStringArrayListExtra(EXTRA_DEFAULT_SELECTED_LIST);
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }

        // 2.初始化本地图片数据
        initImageList();

        // 3.改变显示
        exchangeViewShow();
    }

    /**
     * ContentProvider内存卡中获取本地图片
     */
    private void initImageList() {
        //耗时操作 开线程
        getLoaderManager().initLoader(LOADER_TYPE, null, mLoaderCallbacks);
    }

    /**
     * 改变布局显示 需要及时更新
     */
    private void exchangeViewShow() {
        if (mResultList.size() > 0) {
            //至少选择一张
            mSelectPreviewTv.setEnabled(true);
            mSelectPreviewTv.setOnClickListener(this);
        } else {
            mSelectPreviewTv.setEnabled(false);
            mSelectPreviewTv.setOnClickListener(null);
        }
        mSelectNumTv.setText(mResultList.size() + "/" + mMaxCount);
    }

    /**
     * 加载图片的LoaderCallbacks
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

        // 首先检查指定的id是否存在，如果不存在才会触发该方法，通过该方法才能创建一个loader。
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            // 查询数据库一样 语句
            CursorLoader cursorLoader = new CursorLoader(SelectImageActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR "
                            + IMAGE_PROJECTION[3] + "=? ",
                    new String[]{"image/jpeg", "image/png"}, IMAGE_PROJECTION[2] + " DESC");
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //解析 封装到集合
            // 解析，封装到集合  只保存String路径
            if (data != null && data.getCount() > 0) {
                ArrayList<String> images = new ArrayList<>();

                // 如果需要显示拍照，就在第一个位置上加一个空String
                if (mShowCamera) {
                    images.add("");
                }
                // 不断的遍历循环
                while (data.moveToNext()) {
                    // 只保存路径
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    images.add(path);
                }

                // 显示列表数据
                showImageList(images);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    /**
     * 显示图片列表
     *
     * @param images
     */
    private void showImageList(ArrayList<String> images) {
        SelectImageListAdapter selectImageListAdapter = new SelectImageListAdapter(
                this, images, R.layout.media_chooser_item, mResultList, mMaxCount);
        selectImageListAdapter.setSelectImageListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(selectImageListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_finish:
                Intent intent = new Intent();
                intent.putStringArrayListExtra(EXTRA_RESULT, mResultList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    @Override
    public void select() {
        exchangeViewShow();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //1.第一个把图片加载到集合中
        //2.调用sureSelect()方法
        //3.通知本地图片有所改变，下次进来可以找到这张图片。发送广播
        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(mTempFile));
    }
}
