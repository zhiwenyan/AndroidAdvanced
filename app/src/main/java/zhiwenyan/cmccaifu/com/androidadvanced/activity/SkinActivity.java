package zhiwenyan.cmccaifu.com.androidadvanced.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.base.BaseSkinActivity;

public abstract class SkinActivity extends BaseSkinActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
//        new AlertDialog.Builder(this).setTitle("").create().show();
//        View.inflate(this, R.layout.activity_bottom_navigation, null);
//        LayoutInflater.from(this).inflate(R.layout.activity_bottom_navigation, null);
//        LayoutInflater.from(this).inflate(R.layout.activity_bottom_navigation, null, false);
    }
}
