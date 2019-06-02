package test.kw.my_video.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import test.kw.my_video.activity.base.BaseActivity;

public class HomeActivity extends BaseActivity {

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setSupportActionBar();
        setActionBarIcon(R.drawable.app_inco);
        setTitle("首页");
    }

    /**
     * 显示页面
     * @return
     */
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
}
