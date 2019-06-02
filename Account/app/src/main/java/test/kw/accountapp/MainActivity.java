package test.kw.accountapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

/**
 * 1.写一个消费信息bean类
 * 2.书写日期和时间工具类
 *     可以调用里面的其他参数，比如日期扥数据
 * 3.编写数据库操作类
 *
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecordBean recordBean = new RecordBean();
        //消除阴影
        getSupportActionBar().setElevation(0);
/*        final TickerView tickerView = findViewById(R.id.tickerView);
        tickerView.setCharacterList(TickerUtils.getDefaultNumberList());
        tickerView.setText("43");
        tickerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tickerView.setText("sssssssssss");
            }
        });*/
    }
}
