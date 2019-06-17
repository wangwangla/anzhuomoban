package test.kw.video_all.activity;

import android.app.Activity;
import android.widget.ListView;
import android.widget.TextView;

import test.kw.video_all.R;

public class MainActivity extends Activity {
    private TextView textView;
    private ListView listView;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        TextView textView = findViewById(R.id.title_bar);
        listView = findViewById(R.id.list_view);
    }
}
