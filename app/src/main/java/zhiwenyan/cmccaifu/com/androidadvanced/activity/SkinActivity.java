package zhiwenyan.cmccaifu.com.androidadvanced.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.base.BaseSkinActivity;

public  abstract class SkinActivity extends BaseSkinActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        new AlertDialog.Builder(this).setTitle("").create().show();
    }
}
