package zhiwenyan.cmccaifu.com.androidadvanced.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import zhiwenyan.cmccaifu.com.androidadvanced.BaseApplication;
import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.base.BaseActivity;
import zhiwenyan.cmccaifu.com.androidadvanced.db.DaoSupportFactory;
import zhiwenyan.cmccaifu.com.androidadvanced.db.IDaoSupport;
import zhiwenyan.cmccaifu.com.androidadvanced.exception.ExceptionCrashHandler;
import zhiwenyan.cmccaifu.com.androidadvanced.http.HttpCallBack;
import zhiwenyan.cmccaifu.com.androidadvanced.http.HttpUtils;
import zhiwenyan.cmccaifu.com.androidadvanced.mondel.DiscoverListResult;
import zhiwenyan.cmccaifu.com.androidadvanced.mondel.Person;
import zhiwenyan.cmccaifu.com.androidadvanced.navigationbar.DefaultNavigationBar;

public class MainActivity extends BaseActivity {
    private String url = "http://is.snssdk.com/2/essay/discovery/v3/?";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0x1);
        } else {
            initDao();
        }


        // startService(new Intent(this, MessageService.class));
        // startActivity(new Intent(this, GuardService.class));
        // 获取上次的崩溃信息
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        // 上传到服务器，后面再说.......
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startActivity(new Intent(this, JokeWakeUpService.class));
//        }
//
//
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

    private void initDao() {
        //
        //面向接口编程
        IDaoSupport<Person> dao = DaoSupportFactory.getInstance().getDao(Person.class);
        //ds.insert(new Person("steven", 23));
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            persons.add(new Person("steven", 23 + i));
        }
        long startTime = System.currentTimeMillis();
        dao.insert(persons);
        long endTime = System.currentTimeMillis();
        System.out.println("花费了:" + (endTime - startTime));
        //不开启事务花费了5141ms
        //开启事务花费了65ms
        //开始事务并缓存方法花费了55ms
    }

    @Override
    protected void initTitle() {
        new DefaultNavigationBar.Builder(this)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initDao();
            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
