package zhiwenyan.cmccaifu.com.androidadvanced.navigationbar;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zhiwenyan.cmccaifu.com.androidadvanced.R;

/**
 * Created by zhiwenyan on 5/23/17.
 */

public class DefaultNavigationBar<D extends DefaultNavigationBar.Builder.DefaultNavigationBarParams> extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationBarParams> {

    public DefaultNavigationBar(D params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.title, getParams().mTitle);
        setText(R.id.right_title, getParams().mRightText);
        setLeftImageResource(R.id.left_icon, getParams().mLeftIcon);
        setOnClickListener(R.id.left_icon, getParams().mOnClickListener);

    }

    private void setLeftImageResource(int imgId, int leftIcon) {
        ImageView img = findByViewId(imgId);
        if (img != null) {
            img.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void setText(int viewId, String text) {
        TextView tv = findByViewId(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    private void setOnClickListener(int viewId, View.OnClickListener listener) {
        if (listener != null) {
            findByViewId(viewId).setOnClickListener(listener);
        }
    }

    public <T extends View> T findByViewId(int viewId) {
        return (T) navigationView.findViewById(viewId);
    }
    //ConcreteBuilder : 具体的构建器；
    public static class Builder extends AbsNavigationBar.Builder {
        public DefaultNavigationBarParams p;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            p = new DefaultNavigationBarParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            p = new DefaultNavigationBarParams(context, null);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar(p);
            return defaultNavigationBar;
        }

        //设置所有效果
        public DefaultNavigationBar.Builder setTitle(String title) {
            p.mTitle = title;
            return this;
        }

        public DefaultNavigationBar.Builder setRightText(String rightText) {
            p.mRightText = rightText;
            return this;
        }

        public DefaultNavigationBar.Builder setLeftIcon(int icon) {
            p.mLeftIcon = icon;
            return this;
        }

        public DefaultNavigationBar.Builder setLeftIconListener(View.OnClickListener onClickListener) {
            p.mOnClickListener = onClickListener;
            return this;
        }

        public static class DefaultNavigationBarParams extends AbsNavigationBarParams {
            public String mTitle;
            public String mRightText;
            public int mLeftIcon;
            public View.OnClickListener mOnClickListener;

            public DefaultNavigationBarParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }

}
