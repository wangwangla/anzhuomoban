package test.kw.my_vedio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutId();

    /**
     * 大量的findById我们可以通过泛型来代替他们
     */
    protected <T extends View> T bindViewId(int resId){
        return (T)findViewById(resId);
    }

    /**
     * 支持ActionBar
     */
    protected  void setSupportActionBar(){
        mToolBar = bindViewId(R.id.toolbar);
        if(mToolBar!=null){
            setSupportActionBar(mToolBar);
        }
    }

    protected void setActionBarIcon(int resId){
        if(mToolBar !=null){
            mToolBar.setNavigationIcon(resId);
        }
    }
}
