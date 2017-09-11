package zhiwenyan.cmccaifu.com.androidadvanced.ToolBar;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zhiwenyan.cmccaifu.com.androidadvanced.R;

/**
 * Created by zhiwenyan on 10/09/2017.
 */

public abstract class AbsNavigationBar<P extends
        AbsNavigationBar.Builder.AbsNavigationBarParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndView();
    }

    public P getParams() {
        return mParams;
    }

    /**
     * 设置文本
     *
     * @param viewId
     * @param title
     */
    public void setText(int viewId, String title) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(title)) {
            tv.setText(title);
        }
    }

    /**
     * 设置图片
     *
     * @param imgId
     * @param leftIcon
     */
    private void setLeftImageResource(int imgId, int leftIcon) {
        ImageView img = findViewById(imgId);
        if (img != null) {
            img.setImageResource(R.mipmap.ic_launcher);
        }
    }

    /**
     * 设置事件
     *
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }

    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }

    /**
     * 创建和绑定View
     */
    private void createAndView() {
        mNavigationView = LayoutInflater.from(mParams.mContext).
                inflate(bindLayoutId(), mParams.mParent, false);
        mParams.mParent.addView(mNavigationView, 0);
        applyView();
    }

    @Override
    public abstract int bindLayoutId();

    @Override
    public void applyView() {
    }

    public abstract static class Builder {
        Context mContext;
        AbsNavigationBarParams P;

        public Builder(Context context, ViewGroup parent) {
            mContext = context;
            P = new AbsNavigationBarParams(context, parent);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationBarParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationBarParams(Context context, ViewGroup parent) {
                mContext = context;
                mParent = parent;
            }
        }
    }
}
