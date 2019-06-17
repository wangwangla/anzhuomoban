package test.kw.androidonecode;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import test.kw.androidonecode.activity.MeunActivity;
import test.kw.androidonecode.activity.ToastActivity;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.toast).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startToastActivity();
            }
        });
        findViewById(R.id.meun).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startMenuActivity();
            }
        });
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this,MeunActivity.class);
        startActivity(intent);
    }


    private void startToastActivity() {
        Intent intent = new Intent(this,ToastActivity.class);
        startActivity(intent);
    }
}
