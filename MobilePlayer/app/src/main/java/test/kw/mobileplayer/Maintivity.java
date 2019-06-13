package test.kw.mobileplayer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;


import java.util.ArrayList;

import test.kw.mobileplayer.base.BasePager;
import test.kw.mobileplayer.frament.MyFragment;
import test.kw.mobileplayer.pager.AudioPager;
import test.kw.mobileplayer.pager.NetAudioPager;
import test.kw.mobileplayer.pager.NetVideoPager;
import test.kw.mobileplayer.pager.VideoPager;

public class Maintivity extends FragmentActivity {
    //实例化 帧布局
    private FrameLayout fl_main_content;
    //下栏
    private RadioGroup rg_buttom_tag;
    //存放页面
    private ArrayList<BasePager> basePagers;
    //选中的位置
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        basePagers = new ArrayList<>();
        fl_main_content = findViewById(R.id.fl_main_content);
        rg_buttom_tag = findViewById(R.id.rg_buttom_tag);


        basePagers.add(new VideoPager(this));
        basePagers.add(new NetVideoPager(this));
        basePagers.add(new AudioPager(this));
        basePagers.add(new NetAudioPager(this));

        //遍历哪一个被点击了
        rg_buttom_tag.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        rg_buttom_tag = findViewById(R.id.rg_buttom_tag);
        //默认选中
        rg_buttom_tag.check(R.id.rb_video);

    }
    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                default:
                    position = 0;
                    break;
                case R.id.rb_audio:
                    position = 1;
                    break;
                case R.id.rb_net_video:
                    position = 2;
                    break;
                case R.id.rb_net_audio:
                    position = 3;
                    break;
            }
            setFragment();
        }

    }

    private void setFragment() {
        //1、得到FragmentManagment
        FragmentManager manager = getSupportFragmentManager();
        //2、开始事物
        FragmentTransaction ft = manager.beginTransaction();
        //3、替换
        ft.replace(R.id.fl_main_content,new MyFragment(basePagers.get(position)));
        //4、提交
        ft.commit();
    }
    /*
    * 启动和销毁过程
    *   oncreate   onstart  onResume  onPause  onStop  onDestory
    *
    * A页面向B页面跳转
    *   onPause  onCreate   onCreate onResume  onStop
    *
    * B页面点击返回
    *   onPause  onRestart   onStart   onResume   onStop  onDestroy
    *
    *
    *
    *
    * 横竖屏切换
    *    他会先将其销毁，然后重新创建
     * */
}
