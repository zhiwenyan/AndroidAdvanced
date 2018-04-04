package zhiwenyan.cmccaifu.com.androidadvanced.service.simple;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * Description:
 * Data：3/28/2018-1:54 PM
 *
 * @author: yanzhiwen
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {
    private static final int JOB_ID = 3;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启一个轮询
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(this, JobWakeUpService.class));
        builder.setPeriodic(2000);
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务。定时轮询，看MessageService有没有杀死，
        //如果杀死了，就轮询启动onStartJob
        boolean isServiceWork = isServiceWork(this, MessageService.class.getName());
        if (!isServiceWork) {
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
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfoList = activityManager.getRunningServices(100);
        if (runningServiceInfoList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServiceInfoList.size(); i++) {
            String mName = runningServiceInfoList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
