package test.kw.bottonnav;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private HomeView homeView ;
    private study s;
    private MyView myView;

    private TextView textView ;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    textView.setText("首页");
                    if(homeView==null){
                        textView.setText("首页");
                        homeView = new HomeView(MainActivity.this);
                        frameLayout.addView(homeView.getView());
                    }else {
                        homeView.getView();
                    }
                    s.gone();
                    myView.gone();
                    homeView.showView();
                    return true;
                case R.id.navigation_dashboard:
                    textView.setText("内容");
                    if(s==null){

                        s = new study(MainActivity.this);
                        frameLayout.addView(s.getView());
                    }else {
                        s.getView();
                    }
                    myView.gone();
                    homeView.gone();
                    s.showView();
                    return true;
                case R.id.navigation_notifications:
                    textView.setText("我的");
                    if(myView==null){
                        textView.setText("我的");
                        myView = new MyView(MainActivity.this);
                        frameLayout.addView(myView.getView());
                    }else {
                        myView.getView();
                    }
                    s.gone();
                    homeView.gone();
                    myView.showView();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.main_frame);
        homeView = new HomeView(MainActivity.this);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        init();
    }
    public void init(){
        textView = findViewById(R.id.main_title_bar);
        frameLayout.addView(homeView.getView());
        s = new study(MainActivity.this);
        frameLayout.addView(s.getView());
        myView = new MyView(MainActivity.this);
        frameLayout.addView(myView.getView());
        textView.setText("首页");
        homeView = new HomeView(MainActivity.this);
    }
}
