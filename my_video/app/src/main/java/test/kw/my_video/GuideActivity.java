package test.kw.my_video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (mViewList != null) {
                if (mViewList.size() > 0) {
                    container.addView(mViewList.get(position));
                    if (position == mViewList.size() - 1) {
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
