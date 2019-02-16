package test.kw.com.test;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init()  {
        TextView textView = findViewById(R.id.tv_version);
        int tv_version = 0;
        try {
            tv_version = getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
            textView.setText("V   "+tv_version);
        } catch (PackageManager.NameNotFoundException e) {
            textView.setText("V");
            e.printStackTrace();
        }
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(timerTask,30);
    }
}
