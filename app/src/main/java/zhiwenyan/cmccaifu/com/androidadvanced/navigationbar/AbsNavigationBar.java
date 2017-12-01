package zhiwenyan.cmccaifu.com.androidadvanced.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhiwenyan on 5/23/17.
 */

public class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationBarParams> implements INavigationBar {
    private P mParams;
    View navigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    private void createAndBindView() {
        if (mParams.mViewGroup == null) {
            //获取Activity的根布局
            ViewGroup activityRoot = (ViewGroup) ((Activity) mParams.mContext)
                    .getWindow().getDecorView();
            //.findViewById(android.R.id.content);
            mParams.mViewGroup = (ViewGroup) activityRoot.getChildAt(0);
        }
        //创建View
        navigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(),
                mParams.mViewGroup, false);
        //添加
        mParams.mViewGroup.addView(navigationView, 0);
        applyView();
    }

    public P getParams() {
        return mParams;
    }

    @Override
    public int bindLayoutId() {
        return 0;
    }

    @Override
    public void applyView() {

    }
    //Builder : 抽象类， 规范产品的组建，一般是由子类实现具体的组件过程；
    public abstract static class Builder {
        Context mContext;
        AbsNavigationBarParams p;

        public Builder(Context context, ViewGroup parent) {
            this.mContext = context;
            p = new AbsNavigationBarParams(context, parent);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationBarParams {
            public Context mContext;
            public ViewGroup mViewGroup;

            AbsNavigationBarParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mViewGroup = parent;
            }
        }
    }
}
