package zhiwenyan.cmccaifu.com.androidadvanced.selectImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import zhiwenyan.cmccaifu.com.androidadvanced.R;

public class TestImageActivity extends AppCompatActivity {
    private ArrayList<String> mImageList;
    private static final int SELECT_IMAGE_REQUEST = 0x0011;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        mImageView =(ImageView ) findViewById(R.id.iv);
    }

    //选择图片
    public void selectImage(View view) {
        //这样传统参数有没有问题？？？
        //没有问题，但是不符合框架思想
        //参数传递的优化 SelectImageActivity这个类别人是看不到的，只能用，中间搞一层不要让开发者关注太多
//        Intent intent = new Intent(this, SelectImageActivity.class);
//        intent.putExtra(SelectImageActivity.EXTRA_SELECT_COUNT, 9);
//        intent.putExtra(SelectImageActivity.EXTRA_SELECT_MODE, SelectImageActivity.MODE_MULTI);
//        intent.putStringArrayListExtra(SelectImageActivity.EXTRA_DEFAULT_SELECTED_LIST, mImageList);
//        intent.putExtra(SelectImageActivity.EXTRA_SHOW_CAMERA, true);
//        startActivityForResult(intent, SELECT_IMAGE_REQUEST);
        //只关注想要什么，良好的封装性，不要暴露太多
        ImageSelector.create().multi().count(9).showCamera(true).origin(mImageList).
                start(this, SELECT_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE_REQUEST && data != null) {
                mImageList = getIntent().getStringArrayListExtra(SelectImageActivity.EXTRA_RESULT);
                Log.i("TestImageActivity", "onActivityResult: " + mImageList.toString() + "," + mImageList.size());
            }
        }


        // 从选取相册的Activity中返回后

//        Uri imageUri = data.getData();
//        String[] filePathColumns = {MediaStore.Images.Media.DATA};
//        Cursor c = getContentResolver().query(imageUri, filePathColumns, null, null, null);
//        c.moveToFirst();
//        int columnIndex = c.getColumnIndex(filePathColumns[0]);
//        String imagePath = c.getString(columnIndex);
//        c.close();
        String imagePath = mImageList.get(0);

        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(imagePath, options);
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
        int minLen = Math.min(height, width); // 原图的最小边长
        if (minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
            float ratio = ( float ) minLen / 100.0f; // 计算像素压缩比例
            inSampleSize = ( int ) ratio;
        }
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
        Bitmap bm = BitmapFactory.decodeFile(imagePath, options); // 解码文件
        Log.w("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);


    }
}
