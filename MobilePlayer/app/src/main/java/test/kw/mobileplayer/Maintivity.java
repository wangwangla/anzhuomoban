package test.kw.mobileplayer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Maintivity extends Activity {
    //实例化 帧布局
    private FrameLayout fl_main_content;
    //下栏
    private RadioGroup rg_buttom_tag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fl_main_content = findViewById(R.id.fl_main_content);
        rg_buttom_tag = findViewById(R.id.rg_buttom_tag);
        //默认选中
        rg_buttom_tag.check(R.id.rb_video);
    }
}
