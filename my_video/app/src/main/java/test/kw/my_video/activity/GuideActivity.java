package test.kw.my_video.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{
    //将页面的布局放入一个list中
    private List<View> mViewList;
    //ViewPaper
    private ViewPager viewPager;
    //点放入数组
    private ImageView[] mDotsList;
    //当前位置
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化页面
        initView();
        //初始化Viewpaper
        initViewPager();
        //初始点
        initDots();
    }

    private void initView() {
        /*将三者加入到页面布局上
          （1）获取到LayoutInflater
          （2）将页面放入到一个list中
         */
        LayoutInflater inflater = LayoutInflater.from(this);
        mViewList = new ArrayList<View>();
        mViewList.add(inflater.inflate(R.layout.guide_one,null));
        mViewList.add(inflater.inflate(R.layout.guide_two,null));
        mViewList.add(inflater.inflate(R.layout.guide_three,null));
    }

    private void initViewPager() {
        //获取到ViewPaper
        viewPager = findViewById(R.id.viewpager);
        //在Viewpaper中设置页面
        viewPager.setAdapter(new MyPaperAdapter(mViewList,this));
        //在页面上设置监听
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 初始化点
     *  将所有的点都得到，然后将他们放入到数组中去，并且都设置为不选中
     */
    private void initDots() {
        LinearLayout dotsLayout = findViewById(R.id.dots_layout);
        mDotsList = new ImageView[mViewList.size()];
        for(int i=0;i<mViewList.size();i++){
            mDotsList[i] = (ImageView) dotsLayout.getChildAt(i);
            mDotsList[i].setEnabled(false);
        }
        pos = 0;
        mDotsList[0].setEnabled(true);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    /**
     * 演示哪一个被选中
     * 我们确定出当前的，然后将当前的设置为选中，将上一个设置为未选中
     * 重新的记录被选中的点
     * @param i
     */
    @Override
    public void onPageSelected(int i) {
        setCurremtDotPosition(i);
    }

    private void setCurremtDotPosition(int i) {
        mDotsList[i].setEnabled(true);
        mDotsList[pos].setEnabled(false);
        pos=i;
    }
    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * 适配器实现几个方法
     */
    class MyPaperAdapter extends PagerAdapter {
        private List<View> mViewList;
        private Context context;

        public MyPaperAdapter(List<View> viewList, Context context) {
            this.mViewList = viewList;
            this.context = context;
        }

        //布局的个数
        @Override
        public int getCount() {
            if (mViewList != null) {
                return mViewList.size();
            } else {
                return 0;
            }
        }

        //页面是否相同
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        /**
         * 设置显示的页面
         * @param container
         * @param position
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (mViewList != null) {
                if (mViewList.size() > 0) {
                    //container显示的页面，将需要显示的页面加入到当前的显示列表中
                    container.addView(mViewList.get(position));
                    if (position == mViewList.size() - 1) {
                        //最后一个页面他有一个点击按钮的处理
                        ImageView imageView = mViewList.get(position).findViewById(R.id.iv_start);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setGuid();
                                startHomeActivity();
                            }
                        });
                    }
                    return mViewList.get(position);
                }
            }
            return null;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (mViewList != null) {
                if (mViewList.size() > 0) {
                    container.removeView(mViewList.get(position));
                }
            }
        }
    }

    private void setGuid() {
        SharedPreferences sharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("isFirstIn",false).commit();

    }

    private void startHomeActivity() {
        Intent intent = new Intent(GuideActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

}
