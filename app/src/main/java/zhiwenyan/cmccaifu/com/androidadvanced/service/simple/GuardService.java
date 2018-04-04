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
 * Description:守护进程 双进程通信
 * Data：3/28/2018-11:54 AM
 *
 * @author: yanzhiwen
 */
public class GuardService extends Service {
    private static final int GUARDSERVICE_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(GUARDSERVICE_ID, new Notification());
        //绑定建立连接
        bindService(new Intent(this, MessageService.class), mServiceConnection, BIND_AUTO_CREATE);
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

            Log.i("GuardService", "onServiceConnected: 建立连接");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接
            Log.i("GuardService", "onServiceConnected: 断开连接,重新连接");
            startService(new Intent(GuardService.this, MessageService.class));
            bindService(new Intent(GuardService.this, MessageService.class),
                    mServiceConnection, BIND_AUTO_CREATE);

        }
    };
}
