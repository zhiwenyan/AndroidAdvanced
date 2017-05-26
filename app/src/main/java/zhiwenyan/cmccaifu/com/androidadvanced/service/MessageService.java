package zhiwenyan.cmccaifu.com.androidadvanced.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import zhiwenyan.cmccaifu.com.androidadvanced.ProcessConnection;

/**
 * Created by zhiwenyan on 5/24/17.
 * <p>
 * QQ通讯
 * 耍流氓的方式保活Service
 */

public class MessageService extends Service {
    private static final int MessageId = 0x1;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e("TAG", "run: " + "等待接收消息");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    //提高进程的优先级
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(MessageId, new Notification());
        bindService(new Intent(this, GuardService.class), mServiceConnection, BIND_AUTO_CREATE);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {

        };
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG", "onServiceConnected: " + "建立连接GuardService");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //重新启动
            startActivity(new Intent(MessageService.this, GuardService.class));
            bindService(new Intent(MessageService.this, GuardService.class), mServiceConnection, BIND_AUTO_CREATE);

            Log.e("TAG", "onServiceConnected: " + "GuardService重新启动");
        }
    };
}
