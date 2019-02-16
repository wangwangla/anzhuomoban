package test.kw.com.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class OtherActivivty extends AppCompatActivity {
    private Activity mContext;
    private LayoutInflater layoutInflater ;
    private View mCurrentView;
    public OtherActivivty(Activity context){
        mContext=context;
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
        if (mCurrentView==null) {
            onCreateView();
        }
        TextView textView = mCurrentView.findViewById(R.id.lay);
        textView.setText("ZZZZZZZZZZZZZZZ");
        return mCurrentView;
    }
    public void showView(){
        if(mCurrentView==null){
            onCreateView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
}