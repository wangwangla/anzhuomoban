package test.kw.bottonnav;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContentView {
    private Activity context;
    private View mCurrent;
    private LayoutInflater mLayoutInflater;
    //listView\
    private ListView listView;
    //适配器数据
    private ArrayAdapter<String> arrayAdapter;
    //构造函数
    public ContentView(Activity context){
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    public void createView(){
        init();
    }
    private void init(){
        mCurrent = mLayoutInflater.inflate(R.layout.layout,null);
        listView=mCurrent.findViewById(R.id.iv_listview);
        //设置数据
 /*       String []school = {"我是哥哥","我是爸爸","我是哥哥"};
        arrayAdapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,school);
        //加载适配器
        listView.setAdapter(arrayAdapter);*/
        //设置按下监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();
                Toast.makeText(context,"点击了"+result,Toast.LENGTH_LONG).show();
            }
        });
    }
    public View getView (){
        if(mCurrent == null){
            createView();
        }
        return mCurrent;
    }

    public void  showView(){
        if(mCurrent == null){
            createView();
        }
        mCurrent.setVisibility(View.VISIBLE);

    }
    public void gone(){
        if (mCurrent !=null)
        mCurrent.setVisibility(View.GONE);
    }
}
