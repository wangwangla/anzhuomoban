package test.kw.vitamiodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.vov.vitamio.Vitamio;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Vitamio.isInitialized(getApplicationContext());
    }
}
