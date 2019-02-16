package test.kw.com.test;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    private View mCurrentView;
    private boolean flag =false;
    private Activity mContext;
    private LayoutInflater layoutInflater ;
    public HomeActivity(Activity context){
        mContext = context;
        //从父类得来一个context
        layoutInflater = LayoutInflater.from(mContext);
    }

    protected void onCreateView() {
        initView();
    }

    private void initView() {
        //将当前的页面设置为一个可显示    给父类的页面设置一个页面
        mCurrentView=layoutInflater.inflate(R.layout.layout,null);
        mCurrentView.setVisibility(View.VISIBLE);
    }
    public View getView(){

        if (mCurrentView==null)
        {
            onCreateView();
        }
        TextView textView = mCurrentView.findViewById(R.id.lay);
        if(flag==false){
            flag = !flag;
            textView.setText("" +flag);
        }else {
            flag=!flag;
            textView.setText("XXXXX");
        }
        return mCurrentView;
    }
    public void showView(){
        if(mCurrentView==null){
            onCreateView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}
