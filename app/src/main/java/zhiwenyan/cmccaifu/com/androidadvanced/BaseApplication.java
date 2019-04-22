package zhiwenyan.cmccaifu.com.androidadvanced;

import android.app.Application;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;

import zhiwenyan.cmccaifu.com.androidadvanced.exception.ExceptionCrashHandler;

/**
 * Created by zhiwenyan on 5/23/17.
 */

public class BaseApplication extends Application {
    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
//      HttpUtils.init(new OkHttpEngine());
        ExceptionCrashHandler.getInstance().init(this);
        // Ali热修复
        try {
            mPatchManager = new PatchManager(this);
            // 初始化patch版本
            String pkName = this.getPackageName();
            String versionName = getPackageManager().getPackageInfo(pkName, 0).versionName;
            // 初始化版本名称,当前的版本号
            mPatchManager.init(versionName);
            // 加载之前的patch
            mPatchManager.loadPatch();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

//        HookStartActivityUtil hookStartActivityUtil=new HookStartActivityUtil();
//        try {
//            hookStartActivityUtil.hookStartActivity();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }


    public void setDownload(boolean b) {
    }
}
