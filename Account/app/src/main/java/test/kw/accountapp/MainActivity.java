package test.kw.accountapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import test.kw.accountapp.adapter.ViewPagerAdapter;
import test.kw.accountapp.util.DateUtil;
import test.kw.accountapp.util.GlobalUtil;

/**
 * 1.写一个消费信息bean类
 * 2.书写日期和时间工具类
 *     可以调用里面的其他参数，比如日期扥数据
 * 3.编写数据库操作类
 *
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter ;
    private TickerView amountText;
    private TextView dateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        amountText = findViewById(R.id.amount_text);
        dateText = findViewById(R.id.date_text);
        amountText.setCharacterList(TickerUtils.getDefaultNumberList());
        GlobalUtil.getInstance().setContext(getApplicationContext());
        RecordBean recordBean = new RecordBean();
        //消除阴影
        getSupportActionBar().setElevation(0);
        viewPager = findViewById(R.id.view_paper);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(this);
         //调整顺序
        viewPager.setCurrentItem(viewPagerAdapter.getCurrentItem());
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddRecordActivity.class);
                startActivityForResult(intent,1);
             }
        });

        GlobalUtil.getInstance().datebaseHelper.readRecord("209-2");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewPagerAdapter.reLoad();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /**
     * pager滑动，并且完成的时候
     * @param i
     */
    @Override
    public void onPageSelected(int i) {
        String amount = String.valueOf(viewPagerAdapter.getTotalCoast(i));
        amountText.setText(amount);
        String date = viewPagerAdapter.getDateStr(i);
        dateText.setText(DateUtil.getWeekDay(date));
        Log.d("XXXXXXXXXXX",viewPagerAdapter.getTotalCoast(i)+"=====================");
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}