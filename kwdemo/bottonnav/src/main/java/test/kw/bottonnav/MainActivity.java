package test.kw.bottonnav;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    private FrameLayout frameLayout;
    private HomeView homeView ;
    private ContentView s;
    private MyView myView;

    private TextView textView ;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    textView.setText(R.string.title_home);
                    if(homeView==null){
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
                    textView.setText(R.string.title_dashboard);
                    if(s==null){
                        s = new ContentView(MainActivity.this);
                        frameLayout.addView(s.getView());
                    }else {
                        s.getView();
                    }
                    myView.gone();
                    homeView.gone();
                    s.showView();
                    return true;
                case R.id.navigation_notifications:
                    textView.setText(R.string.title_notifications);
                    if(myView==null){
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
        s = new ContentView(MainActivity.this);
        frameLayout.addView(s.getView());
        myView = new MyView(MainActivity.this);
        frameLayout.addView(myView.getView());
        textView.setText(R.string.title_home);
        homeView = new HomeView(MainActivity.this);
        frameLayout.addView(homeView.getView());
        textView.setTextColor(Color.parseColor("#000000"));
    }
}