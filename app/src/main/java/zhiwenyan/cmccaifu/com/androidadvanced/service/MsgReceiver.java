package zhiwenyan.cmccaifu.com.androidadvanced.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Description:
 * Data：1/7/2019-10:33 AM
 *
 * @author yanzhiwen
 */
public class MsgReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //拿到进度，更新UI
        int progress = intent.getIntExtra("progress", 0);
        Toast.makeText(context,"progress = "+progress,Toast.LENGTH_LONG).show();
    }

}