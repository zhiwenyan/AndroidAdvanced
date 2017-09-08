package zhiwenyan.cmccaifu.com.androidadvanced.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by zhiwenyan on 5/23/17.
 */


public class DialogViewHelper {
    private View mContentView;
    private SparseArray<WeakReference<View>> mViews;

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public DialogViewHelper(Context context, int viewLayoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(viewLayoutResId, null);
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }


    public void setText(int viewId, CharSequence text) {
        //每次都去viewById,减少findViewById的次数
        TextView tv = getView(viewId);
        if (tv != null) {
            tv.setText(text);
        }
    }

    public void setOnClick(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);

        }
    }

    private <T extends View> T getView(int viewId) {
        WeakReference<View> viewWeakReference = mViews.get(viewId);
        View view = null;
        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }
        if (view == null) {
            view = mContentView.findViewById(viewId);
            if (view != null) {
                mViews.put(viewId, new WeakReference<>(view));
            }
        }
        return (T) view;
    }
}
