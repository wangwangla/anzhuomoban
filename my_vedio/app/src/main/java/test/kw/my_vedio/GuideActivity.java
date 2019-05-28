package test.kw.my_vedio;

import android.app.Activity;
import android.content.Context;
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

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener {
    private List<View> mViewList;
    private ViewPager viewPager;
    //点
    private ImageView[] mDotsList;
    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initViewPager();
        initDots();
    }

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

    private void initViewPager() {
        viewPager = findViewById(R.id.viewpager);
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(mViewList,this);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(this);
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

    class MyPagerAdapter extends PagerAdapter{
        private List<View> mImageView;
        private Context context;
        public MyPagerAdapter(List<View> viewList,Context context){
            super();
            this.mImageView = viewList;
            this.context = context;
        }
        @Override
        public int getCount() {
            if(mImageView!=null){
                return mViewList.size();
            }
            return 0;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (mImageView!=null){
                if(mViewList.size()>0){
                    container.addView(mViewList.get(position));
                    return mViewList.get(position);
                }
            }
            return null;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            if (mImageView!=null){
                if(mViewList.size()>0){
                    container.removeView(mViewList.get(position));
                }
            }

        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mViewList = new ArrayList<>();
        mViewList.add(inflater.inflate(R.layout.guide_one, null));
        mViewList.add(inflater.inflate(R.layout.guide_two, null));
        mViewList.add(inflater.inflate(R.layout.guide_three, null));
    }
}
