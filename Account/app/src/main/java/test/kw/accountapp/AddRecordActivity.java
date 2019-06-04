package test.kw.accountapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import test.kw.accountapp.adapter.CategoryAdapter;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener {
    //对于数组的操作
    private String userInput = "";
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    //更新顶部钱数
    private TextView amountText;
    //
    private String category = "General";
    //消费类型  花费
    private RecordBean.RecordType type = RecordBean.RecordType.RECODE_TYPR_EXPENSE;
    private String remark = category;

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        amountText = findViewById(R.id.textView_amount);
        editText = findViewById(R.id.editText);
        editText.setText(remark);
        initKeyBoard();
        recyclerView = findViewById(R.id.recyclerView);
        categoryAdapter = new CategoryAdapter(getApplicationContext());
        recyclerView.setAdapter(categoryAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(gridLayoutManager);
        categoryAdapter.notifyDataSetChanged();
    }

    public void getKeyboardType(){
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KW_KWY_TEST","TYPE=======================");
            }
        });
    }

    public void getKeyboardBack(){
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退格实现
                if (userInput.length() > 0) {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                //为什么要处理点  判断最后以为是否为.如果是就将其删除
                if (userInput.length() > 0 && userInput.charAt(userInput.length() - 1) == '.') {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                updateAmountText();
            }
        });
    }

    public void getKeyboardDone(){
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInput.equals("")) {
                    double amount = Double.valueOf(userInput);
                    Log.d("KW_KWY_TEST",amount+"KEYBOR____DONE=======================");
                }
            }
        });
    }
    public void getKeyboardDot(){
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("KW_KWY_TEST","KEYBOR____DOT=======================");
                /**
                 * 点的处理
                 *    不包含的是时候加入，如果有啥也不做
                 */
                if(!userInput.contains(".")){
                    userInput+=".";
                }

            }
        });
    }
    @Override
    public void onClick(View v) {
        //按钮的值为text上的数据，将View墙砖为
        Button button = (Button)v;
        String input = button.getText().toString();
        //按钮按下将数据加进去,但是小数点之后只能是两位，如果是三位就啥也不做
        /**
         * 如果包含了点在进行这一部分的书写
         */
        Log.d("String.length",""+userInput.length());
        if(userInput.contains(".")){
            //会出现 33.  33.2 33.33  如果是33.那么还可以在输入两位数  33.3还可以输入以为，如果是33.33那么就啥也不做
            if(userInput.split("\\.").length==1||userInput.split("\\.")[1].length()<2){
                userInput+=input;
            }
        }else {
            userInput += input;
        }
        updateAmountText();
        Log.d("numTest",userInput);
    }
    public void updateAmountText(){
        Log.d("String.length",""+userInput.length());
        if(userInput.contains(".")){
            if (userInput.split("\\.").length==1){
                amountText.setText(userInput+"00");
            }else if (userInput.split("\\.")[1].length()==1){
                amountText.setText(userInput+"0");
            }else if(userInput.split("\\.")[1].length()==2){
                amountText.setText(userInput);
            }
        }else {
            if(userInput.equals("")){
                amountText.setText("0.00");
            }else {
                amountText.setText(userInput + ".00");
            }
        }
    }
    public void initKeyBoard(){
        //按钮设置监听
        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_seven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);
        getKeyboardDot();
        getKeyboardBack();
        getKeyboardDone();
        getKeyboardType();
    }
}
