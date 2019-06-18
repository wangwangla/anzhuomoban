package test.kw.androidonecode.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import test.kw.androidonecode.R;

/**
 * Toast的使用
 */
public class ToastActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }
    public void toastclick(View v){
        Toast.makeText(ToastActivity.this,"toast-------",Toast.LENGTH_SHORT).show();
    }
}
