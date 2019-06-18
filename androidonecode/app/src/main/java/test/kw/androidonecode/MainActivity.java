package test.kw.androidonecode;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import test.kw.androidonecode.activity.ForResultActivity;
import test.kw.androidonecode.activity.MeunActivity;
import test.kw.androidonecode.activity.ToastActivity;
import test.kw.androidonecode.activity.TranParActivity;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.toast).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(ToastActivity.class);
            }
        });
        findViewById(R.id.meun).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(MeunActivity.class);
            }
        });
        findViewById(R.id.trn_par).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(TranParActivity.class);
            }
        });
        findViewById(R.id.for_back_data).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(ForResultActivity.class);
            }
        });
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
