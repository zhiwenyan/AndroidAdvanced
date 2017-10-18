package zhiwenyan.cmccaifu.com.androidadvanced.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by yanzhiwen on 2017/10/17.
 */

public abstract class BaseSkinActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                //拦截View的创建
                Log.e("TAG", "拦截到View的创建");
                if (name.equals("Button")) {
                    TextView tv = new TextView(BaseSkinActivity.this);
                    tv.setText("拦截");
                    return tv;
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
    }
}
