package zhiwenyan.cmccaifu.com.androidadvanced.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import zhiwenyan.cmccaifu.com.androidadvanced.BaseApplication;
import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.UserAidl;
import zhiwenyan.cmccaifu.com.androidadvanced.base.BaseActivity;
import zhiwenyan.cmccaifu.com.androidadvanced.db.DaoSupportFactory;
import zhiwenyan.cmccaifu.com.androidadvanced.db.IDaoSupport;
import zhiwenyan.cmccaifu.com.androidadvanced.dialog.AlertDialog;
import zhiwenyan.cmccaifu.com.androidadvanced.exception.ExceptionCrashHandler;
import zhiwenyan.cmccaifu.com.androidadvanced.ipc.MessageService;
import zhiwenyan.cmccaifu.com.androidadvanced.mondel.Person;
import zhiwenyan.cmccaifu.com.androidadvanced.navigationbar.DefaultNavigationBar;

public class MainActivity extends BaseActivity {
    private String url = "http://is.snssdk.com/2/essay/discovery/v3/?";
    private Button mSkinBtn;
    private ImageView mSkinImg;
    //客户端一定要获取Aidl的实例
    private UserAidl mUserAidl;
    private Button mUserNameBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        if (crashFile.exists()) {
            //将崩溃日志上传至服务器

        }

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setContentView(R.layout.dialog_layout)
                .setText(R.id.labelShare, "分享")
                .fromBottom(true)
                .fullWith()
                .setOnClickListener(R.id.send, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "send", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
//        final EditText editText = dialog.getView(R.id.edit);
//
        mUserNameBtn = (Button) findViewById(R.id.nameBtn);
//        dialog.setOnClick(R.id.send, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("TAG", "onClick: " + dialog.toString());
//                Toast.makeText(MainActivity.this,
//                        editText.getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
        //弹出软件盘的问题
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    0x1);
//        } else {
//            initDao();
//        }
        // View layoutView = View.inflate(this, R.layout.activity_main, null);
        //LayoutInflater主要用来inflater的layout的布局
        //  LayoutInflater.from(this).inflate(R.layout.activity_main, null);

        LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);

        // startService(new Intent(this, MessageService.class));
        // startActivity(new Intent(this, GuardService.class));
        // 获取上次的崩溃信息
        //    File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        // 上传到服务器，后面再说.......
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            startActivity(new Intent(this, JokeWakeUpService.class));
//        }
//
//
//        HttpUtils.with(this).url(url).addParam("iid", "6152551759")
//                .cache(true)
//                .addParam("aid", "7").execute(new HttpCallBack<DiscoverListResult>() {
//            @Override
//            public void onSuccess(DiscoverListResult result) {
//                System.out.println("-------" + result);
//            }
//
//            @Override
//            public void error(Exception e) {
//            }
//        });


        startService(new Intent(this, MessageService.class));

        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

    }

    private void fixDexBug() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i("TAG", "onClick: " + mUserAidl.getUserName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        startService(new Intent(this, MessageService.class));
        Intent intent = new Intent(this, MessageService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //service 是服务端给我们的
            mUserAidl = UserAidl.Stub.asInterface(service);
            try {
                String userName = mUserAidl.getUserName();
                Log.i("MainActivity", "onServiceConnected: " + userName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //
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

    //
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

    //
    @Override
    protected void initView() {
        mSkinBtn = (Button) findViewById(R.id.skinBtn);
        mSkinImg = (ImageView) findViewById(R.id.img);
        mSkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources superResources = getResources();
                try {
                    //创建AssertManager
                    AssetManager assetManager = AssetManager.class.newInstance();
                    @SuppressLint("PrivateApi")
                    Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
                    //如果是私有的
                    //method.setAccessible(true);
                    //反射执行方法
                    method.invoke(assetManager, Environment.getExternalStorageDirectory().getAbsolutePath()
                            + File.separator + "red.skin");
                    Resources resources = new Resources(assetManager,
                            superResources.getDisplayMetrics(), superResources.getConfiguration());
                    int drawableId = resources.getIdentifier("fengjingxiaotu", "mipmap", "com.fumi.zhiwenyan.skinplugin");
                    Drawable drawable = resources.getDrawable(drawableId);
                    mSkinImg.setImageDrawable(drawable);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    @OnClick({R.id.test, R.id.ali_fix})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test:
//                Toast.makeText(this, 2 / 0 + "测试", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ali_fix:
                try {
                    // 测试 目前暂且放在本地
                    File patchFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
                    if (patchFile.exists()) {
                        // 修复apatch，不需要重启可立即生效
                        BaseApplication.mPatchManager.addPatch(patchFile.getAbsolutePath());
                        Toast.makeText(this, "Bug修复成功", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Bug修复失败", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    //
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  initDao();
            } else {
                // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
