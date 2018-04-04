package zhiwenyan.cmccaifu.com.androidadvanced.ipc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.UserAidl;
import zhiwenyan.cmccaifu.com.androidadvanced.base.BaseActivity;

/**
 * Created by yanzhiwen on 2017/10/24.
 */

public class IPCActivity extends BaseActivity {
    private UserAidl mUserAidl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ipc;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initData() {
        //启动一个服务，等待A应用过来连接
        startService(new Intent(this, MessageService.class));
        //客户端代码
        bindService(new Intent(this, MessageService.class), mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void initView() {

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mUserAidl = UserAidl.Stub.asInterface(service);
            try {
                String userName = mUserAidl.getUserName();
                Log.i("TAG", "onServiceConnected: " + userName);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
