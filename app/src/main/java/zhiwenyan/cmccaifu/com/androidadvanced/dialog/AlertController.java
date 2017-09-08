package zhiwenyan.cmccaifu.com.androidadvanced.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;

/**
 * Created by zhiwenyan on 5/23/17.
 * <p>
 * 具体的构造器
 */

class AlertController {
    private AlertDialog mAlertDialog;
    private Window mWindow;

    public AlertController(AlertDialog alertDialog, Window window) {
        this.mAlertDialog = alertDialog;
        this.mWindow = window;
    }

    public AlertDialog getAlertDialog() {
        return mAlertDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    /**
     * 存放参数，一部分设置参数的功能
     */
    public static class AlertParams {
        public Context mContext;
        public int mThemeId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public View mView;
        public int mViewLayoutResId;
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();

        public AlertParams(Context context, int themeId) {
            this.mContext = context;
            this.mThemeId = themeId;
        }

        /**
         * 绑定和设置参数
         *
         * @param alert
         */

        public void apply(AlertController alert) {
            DialogViewHelper viewHelper = null;

            if (mViewLayoutResId != 0) {
                viewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }
            if (mView != null) {
                viewHelper = new DialogViewHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局");
            }
            alert.getWindow().setContentView(mViewLayoutResId);

            for (int i = 0; i < mTextArray.size(); i++) {
                viewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }
            for (int i = 0; i < mClickArray.size(); i++) {
                viewHelper.setOnClick(mClickArray.keyAt(i), mClickArray.valueAt(i));

            }

        }
    }

}
