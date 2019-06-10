package test.kw.accountapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

import test.kw.accountapp.database.RecordDatebaseHelper;
import test.kw.accountapp.fragment.MainFragment;
import test.kw.accountapp.util.GlobalUtil;

/**
 * ViewPaper适配器
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    //ViewPaper维护fragment
    private LinkedList<MainFragment> fragments = new LinkedList<>();
    //日期
    private LinkedList<String> datas = new LinkedList<>();
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
        //多少个侧滑是通过data的个数决定的
        GlobalUtil globalUtil = GlobalUtil.getInstance();
        RecordDatebaseHelper r = globalUtil.datebaseHelper;
        datas = r.getAvaliableDate();
        for(String date : datas){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
    }
    public int getCurrentItem(){
        return datas.size()-1;
    }
}
