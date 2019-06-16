package test.kw.video_all.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;

import test.kw.video_all.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Thread(){
            @Override
            public void run() {
                super.run();
                startMainActivity();
            }
        },2000);
    }

    private void startMainActivity() {
        if (!isStartMain) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private boolean isStartMain = false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //触摸屏幕执行跳转，也可以通过点击按钮执行跳转。
        //问题：触摸的时候执行一次，在定时到在执行一次，所以可以加判断
        /**
         * 处理方法一：加入一个变量创建执行将其状态改变
         * 处理方式二:使用单例
         */
        startMainActivity();
        return super.onTouchEvent(event);
    }
}
