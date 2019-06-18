package test.kw.androidonecode.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import test.kw.androidonecode.MainActivity;
import test.kw.androidonecode.R;

public class MeunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meun);
    }

    /**
     * 显示列表
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    /**
     * 选中
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item1:
                Toast.makeText(MeunActivity.this,"ADD_ITEM1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_item2:
                Toast.makeText(MeunActivity.this,"ADD_ITEM2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_item3:
                Toast.makeText(MeunActivity.this,"ADD_ITEM3",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
