package zhiwenyan.cmccaifu.com.androidadvanced.ToolBar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import zhiwenyan.cmccaifu.com.androidadvanced.R;

/**
 * Created by zhiwenyan on 10/09/2017.
 */

public class DefaultNavigationBar<D extends DefaultNavigationBar.Builder.DefaultNavigationBarParams> extends AbsNavigationBar<DefaultNavigationBar.
        Builder.DefaultNavigationBarParams> {
    public DefaultNavigationBar(D params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        setText(R.id.title, getParams().mTitle);


    }


    public static class Builder extends AbsNavigationBar.Builder {
        DefaultNavigationBarParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationBarParams(context, parent);
        }

        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar defaultNavigationBar = new DefaultNavigationBar(P);
            return defaultNavigationBar;
        }

        //设置所有的效果
        public DefaultNavigationBar.Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        public DefaultNavigationBar.Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        public DefaultNavigationBar.Builder setRightIcon(int icon) {
            P.mRightIcon = icon;
            return this;
        }

        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener listener) {
            P.mRightListener = listener;
            return this;
        }

        public DefaultNavigationBar.Builder setLeftIcon(int icon) {
            P.mLeftIcon = icon;
            return this;
        }

        public DefaultNavigationBar.Builder setLeftClickListener(View.OnClickListener listener) {
            P.mLeftListener = listener;
            return this;
        }


        public static class DefaultNavigationBarParams extends
                AbsNavigationBar.Builder.AbsNavigationBarParams {
            public String mTitle;
            public String mRightText;
            public int mRightIcon;
            public View.OnClickListener mRightListener;
            public int mLeftIcon;
            public View.OnClickListener mLeftListener;


            //所有的效果
            public DefaultNavigationBarParams(Context context, ViewGroup parent) {
                super(context, parent);
            }

        }
    }
}
