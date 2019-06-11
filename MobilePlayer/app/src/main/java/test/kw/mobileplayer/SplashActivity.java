package test.kw.mobileplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

public class SplashActivity extends Activity {
    private Handler handler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //两秒进入主页面
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //两秒之后执行，执行在主线程，在那么new,就在哪里执行
                startMainActivity();
            }
        },2000);
    }
    //跳转主页面，将本页面关闭
    private void startMainActivity() {
        Intent intent = new Intent(this,Maintivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //这个会有执行很多次，处理方法，让主页面是单例的，可以设置标志。
        startMainActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //null所有的消息都会被移除
        handler.removeCallbacksAndMessages(null);
    }
}
