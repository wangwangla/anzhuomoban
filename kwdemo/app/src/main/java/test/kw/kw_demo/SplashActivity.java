package test.kw.kw_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        timer.schedule(timerTask,3000);
    }
}
