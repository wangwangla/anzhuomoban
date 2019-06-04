package test.kw.accountapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

import test.kw.accountapp.fragment.MainFragment;

/**
 * ViewPaper适配器
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    //ViewPaper维护fragment
    private LinkedList<MainFragment> fragments = new LinkedList<>();
    //日期
    private LinkedList<String> dates = new LinkedList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void initFragment(){
        dates.add("XXX1");
        dates.add("XXX2");
        dates.add("XXX3");

        for(String date : dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }
    public int getCurrentItem(){
        return dates.size()-1;
    }
}
