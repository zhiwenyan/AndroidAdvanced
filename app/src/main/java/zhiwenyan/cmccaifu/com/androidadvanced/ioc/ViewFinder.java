package zhiwenyan.cmccaifu.com.androidadvanced.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Description:ViewFinderById的辅助类
 * Data：2/6/2018-2:09 PM
 *
 * @author: yanzhiwen
 */
public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
