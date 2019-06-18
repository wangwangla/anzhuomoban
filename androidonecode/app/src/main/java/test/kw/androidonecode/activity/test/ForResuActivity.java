package test.kw.androidonecode.activity.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import test.kw.androidonecode.R;

public class ForResuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_resu);
        findViewById(R.id.for_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               returnData();
            }
        });
    }

    private void returnData() {
        Intent intent = new Intent();
        intent.putExtra("data","FOR_RETURN");
        setResult(1,intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnData();
    }

}
