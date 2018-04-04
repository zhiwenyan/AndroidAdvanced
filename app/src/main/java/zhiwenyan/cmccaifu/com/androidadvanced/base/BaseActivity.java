package zhiwenyan.cmccaifu.com.androidadvanced.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by zhiwenyan on 5/22/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        initData();
        initTitle();
        initView();
    }

    protected abstract int getLayoutId();

    protected abstract void initTitle();

    protected abstract void initData();

    protected abstract void initView();


    protected void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    /**
     * findViewById 不需要再去强转
     */
    public <T extends View> T viewById(int resId) {
        return (T) super.findViewById(resId);
    }
}
