package test.kw.bottonnav;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class HomeView {
    //页面
    private Activity context;
    private View mCurrent;
    private LayoutInflater mLayoutInflater;
    //页面资源

    //构造函数
    public HomeView(Activity context){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    public void createView(){
        init();
    }
    private void init(){
        mCurrent = mLayoutInflater.inflate(R.layout.activity_home,null);
    }
    public View getView (){
        if(mCurrent == null){
            createView();
        }
        return mCurrent;
    }

    public void  showView(){
        if(mCurrent == null){
            createView();
        }
        mCurrent.setVisibility(View.VISIBLE);

    }

    public void gone(){
        if(mCurrent!=null) {
            mCurrent.setVisibility(View.GONE);
        }
    }
}
