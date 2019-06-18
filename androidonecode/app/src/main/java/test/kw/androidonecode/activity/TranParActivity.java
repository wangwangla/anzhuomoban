package test.kw.androidonecode.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import test.kw.androidonecode.R;
import test.kw.androidonecode.activity.test.ReciverActivity;

public class TranParActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_par);
        findViewById(R.id.btn_par).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TranParActivity.this,ReciverActivity.class);
                intent.putExtra("data","data--------------");
                startActivity(intent);
            }
        });
    }
}
