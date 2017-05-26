package zhiwenyan.cmccaifu.com.androidadvanced.service;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.List;

/**
 * Created by zhiwenyan on 5/24/17.
 * JokeWakeUpService
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JokeWakeUpService extends JobService {
    private static final int jobId = 0x3;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启轮询
        JobInfo.Builder jokeInfo = new JobInfo.Builder(jobId, new ComponentName(this, JokeWakeUpService.class));
        jokeInfo.setPeriodic(2000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jokeInfo.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("TAG", "onStartJob");
        // boolean isGuardAlive = isServiceWork(this,GuardService.class.getName());
        boolean isMessageAlive = isServiceWork(this, MessageService.class.getName());
        if (!isMessageAlive) {
            // startService(new Intent(this,GuardService.class));
            startService(new Intent(this, MessageService.class));
        }

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
