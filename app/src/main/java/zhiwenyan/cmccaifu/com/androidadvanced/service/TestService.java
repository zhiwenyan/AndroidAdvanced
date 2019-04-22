package zhiwenyan.cmccaifu.com.androidadvanced.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Description: service与activity通信：通过广播
 * Data：1/7/2019-10:32 AM
 *
 * @author yanzhiwen
 */
public class TestService extends Service {
    /**
     * 进度条的最大值
     */
    public static final int MAX_PROGRESS = 100;
    /**
     * 进度条的进度值
     */
    private int progress = 0;

    private Intent intent = new Intent("com.steven.service.RECEIVER");


    /**
     * 模拟下载任务，每秒钟更新一次
     */
    public void startDownLoad() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (progress < MAX_PROGRESS) {
                    progress += 1;
                    //发送Action为ccom.steven.service.RECEIVER的广播
                    intent.putExtra("progress", progress);
                    sendBroadcast(intent);

                }
            }
        }).start();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startDownLoad();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
