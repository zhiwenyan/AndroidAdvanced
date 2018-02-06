package zhiwenyan.cmccaifu.com.androidadvanced.ioc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import zhiwenyan.cmccaifu.com.androidadvanced.R;

public class IocActivity extends AppCompatActivity {
    @ViewById(R.id.tv)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ioc);
        ViewUtils.inject(this);
        mTextView.setText("IOC");

    }

    @onClick({R.id.tv})
    @CheckNet
    public void onClick(View view) {
        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
    }
}
