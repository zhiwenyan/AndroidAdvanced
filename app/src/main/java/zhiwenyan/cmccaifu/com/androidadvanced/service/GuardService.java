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
 * 守护进程  双进程通信
 */

public class GuardService extends Service {
    private static final int GuardId = 0x2;

    //提高进程的优先级
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(GuardId, new Notification());
        //绑定建立连接
        bindService(new Intent(this, MessageService.class), mServiceConnection, BIND_AUTO_CREATE);
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
            //连接上
            Log.e("TAG", "onServiceConnected: " + "守护进程建立连接");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接
            //重新启动
            startActivity(new Intent(GuardService.this, MessageService.class));
            bindService(new Intent(GuardService.this, MessageService.class), mServiceConnection, BIND_AUTO_CREATE);
            Log.e("TAG", "onServiceConnected: " + "MessageService重新启动");
        }
    };
}
