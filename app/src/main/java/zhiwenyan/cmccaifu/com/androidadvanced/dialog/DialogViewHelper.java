package zhiwenyan.cmccaifu.com.androidadvanced.dialog;

import android.content.Context;
import android.util.Log;
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
    private SparseArray<View> mViewSparseArray;

    public DialogViewHelper() {
        mViews = new SparseArray<>();
        mViewSparseArray = new SparseArray<>();
    }

    public DialogViewHelper(Context context, int viewLayoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(viewLayoutResId, null);
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        //每次都去viewById,减少findViewById的次数
        TextView tv = getView(viewId);
        if (tv != null) {
            Log.i("TAG", "setText: " + text);
            tv.setText(text);
        }
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnClick(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            Log.i("TAG", "setOnClick: ");
            view.setOnClickListener(listener);
        }
    }

    public <T extends View> T getView(int viewId) {
//        WeakReference<View> viewWeakReference = mViews.get(viewId);
//        View view = null;
//        if (viewWeakReference != null) {
//            view = viewWeakReference.get().findViewById(viewId);
//        }
//        if (view == null) {
//            view = mContentView.findViewById(viewId);
//            if (view != null) {
//                mViews.put(viewId, new WeakReference<>(view));
//            }
//        }
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;
    }
}
