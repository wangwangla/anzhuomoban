package test.kw.my_vedio;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

public class HomeActivity extends BaseActivity  {
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private FragmentManager fragmentManager;
    private Fragment mCurrentFragment;

    private MenuItem mPreItem ;
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setSupportActionBar();
        setActionBarIcon(R.drawable.ic_launcher_background);
        setTitle("首页");
        mDrawerLayout = bindViewId(R.id.drawer_layout);
        mNavigationView = bindViewId(R.id.navigation_view);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolBar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        mPreItem = mNavigationView.getMenu().getItem(0);
        mPreItem.setCheckable(true);
        initFragment();
        handleNatigationView();

    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        mCurrentFragment = FragmentMangerWrapper.getFragmentManger().createFragment(HomeFragment.class);
        fragmentManager.beginTransaction().add(R.id.fl_main,mCurrentFragment).commit();
    }

    private void handleNatigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if(mPreItem !=null){
                    mPreItem.setCheckable(false);
                }
                switch (menuItem.getItemId()){
                    case R.id.naigation_item_video:
                        switchFragment(HomeFragment.class);
                        mToolBar.setTitle("标题");
                        break;
                    case R.id.naigation_item_blog:
                        switchFragment(BlogFragment.class);
                        mToolBar.setTitle("博客");
                        break;
                    case R.id.naigation_item_about:
                        switchFragment(AboutFragment.class);
                        mToolBar.setTitle("关于");
                        break;
                }
                mPreItem = menuItem;
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                menuItem.setCheckable(true);
                return false;
            }
        });
    }

    private void switchFragment(Class<?> clazz) {
        Fragment fragment = FragmentMangerWrapper.getFragmentManger().createFragment(clazz);
        if(fragment.isAdded()){
            fragmentManager.beginTransaction().hide(mCurrentFragment).show(fragment).commitAllowingStateLoss();
        }else {
            fragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.fl_main,fragment).commitAllowingStateLoss();
        }
        mCurrentFragment = fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }
}
