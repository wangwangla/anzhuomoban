package test.kw.com.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import static test.kw.com.test.R.layout.activity_main;

public class MainActivity extends AppCompatActivity {
    private OtherActivivty otherActivivty;
    private HomeActivity homeActivity;
    private FrameLayout mTextMessage;
    private MyActivity myActivity;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    removeAllView();
                    if(homeActivity==null){
                        homeActivity=new HomeActivity(MainActivity.this);
                        mTextMessage.addView(homeActivity.getView());
                    }else {
                        homeActivity.getView();
                    }
                    homeActivity.showView();
                    return true;
                case R.id.navigation_dashboard:
                    removeAllView();
                    if(myActivity==null){
                        myActivity=new MyActivity(MainActivity.this);
                        mTextMessage.addView(myActivity.getView());
                    }else {
                        myActivity.getView();
                    }
                    myActivity.showView();
                    return true;
                case R.id.navigation_notifications:
                    removeAllView();
                    if(otherActivivty==null){
                        otherActivivty=new OtherActivivty(MainActivity.this);
                        mTextMessage.addView(otherActivivty.getView());
                    }else {
                        otherActivivty.getView();
                    }
                    otherActivivty.showView();
                    return true;
                default:

            }
            return false;
        }
    };

    public void removeAllView(){
        for(int i=0;i<mTextMessage.getChildCount();i++){
            mTextMessage.getChildAt(i).setVisibility(View.GONE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);
        mTextMessage = findViewById(R.id.main_body);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        removeAllView();
        if(homeActivity==null){
            homeActivity=new HomeActivity(MainActivity.this);
            mTextMessage.addView(homeActivity.getView());
        }else {
            homeActivity.getView();
        }
        homeActivity.showView();
    }
}
