package zhiwenyan.cmccaifu.com.androidadvanced.activity;

import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import butterknife.OnClick;
import zhiwenyan.cmccaifu.com.androidadvanced.BaseApplication;
import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.base.BaseActivity;
import zhiwenyan.cmccaifu.com.androidadvanced.exception.ExceptionCrashHandler;
import zhiwenyan.cmccaifu.com.androidadvanced.http.HttpCallBack;
import zhiwenyan.cmccaifu.com.androidadvanced.http.HttpUtils;
import zhiwenyan.cmccaifu.com.androidadvanced.mondel.DiscoverListResult;
import zhiwenyan.cmccaifu.com.androidadvanced.navigationbar.DefaultNavigationBar;
import zhiwenyan.cmccaifu.com.androidadvanced.service.MessageService;

public class MainActivity extends BaseActivity {
    private String url1 = "http://is.snssdk.com/2/essay/discovery/v3/?&device_platform=android&device_type=Redmi+Note+3&iid=6152551759&" +
            "manifest_version_code=570&longitude=113.000366&latitude=28.171377&update_version_code=5701&aid=7&channel=360";
    private String url = "http://is.snssdk.com/2/essay/discovery/v3/?";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        startService(new Intent(this, MessageService.class));
        // startActivity(new Intent(this, GuardService.class));
        // 获取上次的崩溃信息
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        // 上传到服务器，后面再说.......
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startActivity(new Intent(this, JokeWakeUpService.class));
//        }


        HttpUtils.with(this).url(url).addParam("iid", "6152551759")
                .addParam("aid", "7").execute(new HttpCallBack<DiscoverListResult>() {
            @Override
            public void onSuccess(DiscoverListResult result) {
                Log.i("TAG", "onSuccess: " + result.getData().getCategories().getName());
            }

            @Override
            public void error(Exception e) {
            }
        });

    }

    private void fixDexBug() {

    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar.Builder(this)
                .setTitle("中间的文字")
                .setRightText("右边的文字")
                .setLeftIconListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
                    }
                }).builder();


    }

    @Override
    protected void initView() {

    }


    @OnClick({R.id.test, R.id.ali_fix})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test:
                Toast.makeText(this, 2 / 0 + "测试", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ali_fix:
                try {
                    // 测试 目前暂且放在本地
                    String patchFileString = Environment.getExternalStorageDirectory() + "/fix.apatch";
                    Log.e("TAG", patchFileString);
                    // 修复apatch，不需要重启可立即生效
                    BaseApplication.mPatchManager.addPatch(patchFileString);
                    Toast.makeText(this, "Bug修复成功", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Bug修复失败", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
