package test.kw.androidonecode.activity.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import test.kw.androidonecode.R;

public class ReciverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciver);
        String data = getIntent().getStringExtra("data");
        Toast.makeText(ReciverActivity.this,data,Toast.LENGTH_SHORT).show();
    }
}
