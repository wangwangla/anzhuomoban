package test.kw.my_video;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        init();
    }

    private void init() {
        //获取数据,没有就设置true
        boolean isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);
        if (isFirstIn){
            handler.sendEmptyMessageDelayed(Constant.GO_GUIDE,Constant.DELAY_TIME);
        }else {
            handler.sendEmptyMessageDelayed(Constant.GO_HOME,Constant.DELAY_TIME);
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void startGuideActivity() {
        Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message) {
            switch (message.what){
                case Constant.GO_GUIDE:
                    startGuideActivity();
                    break;
                case Constant.GO_HOME:
                    startHomeActivity();
                    break;
                default:
                    break;
            }
        }
    };
}