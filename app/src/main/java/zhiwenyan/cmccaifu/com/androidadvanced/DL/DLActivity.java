package zhiwenyan.cmccaifu.com.androidadvanced.DL;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zhiwenyan.cmccaifu.com.androidadvanced.R;

public class DLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dl);
        startActivity(new Intent());
    }
}
