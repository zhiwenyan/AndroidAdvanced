package zhiwenyan.cmccaifu.com.androidadvanced.selectImage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import zhiwenyan.cmccaifu.com.androidadvanced.R;

public class TestImageActivity extends AppCompatActivity {
    private ArrayList<String> mImageList;
    private static final int SELECT_IMAGE_REQUEST = 0x0011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
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
    }
}
