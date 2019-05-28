package test.kw.my_vedio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

/**
 * 引导页
 */
public class SplashActivity extends Activity {
    private SharedPreferences sharedPreferences;
    private static final int GO_HOME = 1;
    private static final int GO_GUIDE = 2;
    private static final int ENTER_DURATION = 2000;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_GUIDE:
                    startGuideActivity();
                    break;
                case GO_HOME:
                    startHomeActivity();
                    break;
                default:
                    break;
            }
        }
    } ;

    private void startHomeActivity() {
        Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * 创建一个文件，这个文件只能本APP可以操作
         */
        sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        init();
    }

    private void init() {
        boolean isFirstIn = sharedPreferences.getBoolean("mIsFirstIn",true);
        /**
         * 首次进来
         */
        if(isFirstIn){
            handler.sendEmptyMessageDelayed(GO_GUIDE,ENTER_DURATION);
        }else {
            handler.sendEmptyMessageDelayed(GO_HOME,ENTER_DURATION);
        }
    }
    private void startGuideActivity() {
        Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
        startActivity(intent);
        finish();
    }
}
