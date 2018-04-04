package zhiwenyan.cmccaifu.com.androidadvanced.service.simple;

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
 * Description:QQ聊天通讯
 * Data：3/28/2018-11:37 AM
 *
 * @author: yanzhiwen
 */
public class MessageService extends Service {
    private static final int MESSAGE_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Log.i("MessageService", "run: 等待接收消息");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //提高进程的优先级
        startForeground(MESSAGE_ID, new Notification());
        bindService(new Intent(this, GuardService.class), mServiceConnection, BIND_AUTO_CREATE);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private ProcessConnection.Stub mBinder = new ProcessConnection.Stub() {

    };


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接上
            Log.i("MessageService", "onServiceConnected: 建立连接");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接，需要重新绑定
            Log.i("MessageService", "onServiceConnected: 断开连接,重新连接");
            startService(new Intent(MessageService.this, GuardService.class));
            bindService(new Intent(MessageService.this, GuardService.class),
                    mServiceConnection, BIND_AUTO_CREATE);


        }
    };

}
