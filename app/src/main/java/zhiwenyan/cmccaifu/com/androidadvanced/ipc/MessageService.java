package zhiwenyan.cmccaifu.com.androidadvanced.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import zhiwenyan.cmccaifu.com.androidadvanced.UserAidl;

/**
 * Created by yanzhiwen on 2017/10/24.
 */

public class MessageService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //绑定
        return mBinder;
    }

    private final UserAidl.Stub mBinder = new UserAidl.Stub() {
        @Override
        public String getUserName() throws RemoteException {
            return "Steven";
        }

        @Override
        public String getUserPwd() throws RemoteException {
            return "123456";
        }
    };
}
