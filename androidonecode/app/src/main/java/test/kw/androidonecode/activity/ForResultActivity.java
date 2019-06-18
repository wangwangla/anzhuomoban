package test.kw.androidonecode.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import test.kw.androidonecode.R;
import test.kw.androidonecode.activity.test.ForResuActivity;

public class ForResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_result);
        findViewById(R.id.for_result).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForResultActivity.this,ForResuActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==1){
                    String returnData = data.getStringExtra("data");
                    Toast.makeText(ForResultActivity.this,returnData,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
