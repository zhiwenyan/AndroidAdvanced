package zhiwenyan.cmccaifu.com.androidadvanced.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;

/**
 * Created by zhiwenyan on 5/23/17.
 */

public class AlertDialog  extends Dialog{


    public AlertDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public static class Builder{

    }
}
