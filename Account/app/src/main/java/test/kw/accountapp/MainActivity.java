package test.kw.accountapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import test.kw.accountapp.adapter.ViewPagerAdapter;
import test.kw.accountapp.util.GlobalUtil;

/**
 * 1.写一个消费信息bean类
 * 2.书写日期和时间工具类
 *     可以调用里面的其他参数，比如日期扥数据
 * 3.编写数据库操作类
 *
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecordBean recordBean = new RecordBean();
        //消除阴影
        getSupportActionBar().setElevation(0);
        viewPager = findViewById(R.id.view_paper);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
         //调整顺序
        viewPager.setCurrentItem(viewPagerAdapter.getCurrentItem());
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivity(intent);
             }
        });
        GlobalUtil.getInstance().setContext(getApplicationContext());

        GlobalUtil.getInstance().datebaseHelper.readRecord("209-2");
    }
}