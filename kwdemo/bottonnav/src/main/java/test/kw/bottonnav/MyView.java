package test.kw.bottonnav;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MyView {
    private Activity context;
    private View mCurrent;
    private LayoutInflater mLayoutInflater;


    //构造函数
    public MyView(Activity context){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    public void createView(){
        init();
    }
    private void init(){
        mCurrent = mLayoutInflater.inflate(R.layout.actvity_my,null);
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
