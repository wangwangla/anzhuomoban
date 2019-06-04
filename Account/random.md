# 笔记

### 书写步骤

```
* 1.写一个消费信息bean类
* 2.书写日期和时间工具类
*     可以调用里面的其他参数，比如日期扥数据
* 3.编写数据库操作类
```

#### bean

```java
import test.kw.accountapp.util.DateUtil;
/**
 * 账目类
 */
public class RecordBean {
    //消费类别
    public enum RecordType {
        //输入  支出
        RECODE_TYPR_EXPENSE, RECODE_TYPR_INCOME
    }
    //消费金额
    private double amount;
    //消费类别
    private RecordType recordType;
    //分类
    private String category;
    //备注
    private String remark;
    //日期
    private String date;
    public int getRecordType() {
        if(this.recordType == RecordType.RECODE_TYPR_EXPENSE){
            return 1;
        }else{
            return 2;
        }
    }

    public void setRecordType(int recordType) {
        if(recordType==1){
            this.recordType= RecordType.RECODE_TYPR_EXPENSE;
        }else {
            this.recordType = RecordType.RECODE_TYPR_INCOME;
        }
    }
    //时间   存储为一个时间戳
    private long timeStamp;
    //主键
    private String uuid;

    //构造方法
    public RecordBean() {
        //主键UUID
        this.uuid = UUID.randomUUID().toString();
        this.timeStamp = System.currentTimeMillis();
        date = DateUtil.getFormattedDate();
    }
}
```

#### 时间工具类

```java
public class DateUtil {
    /**
     *  输出  09:08
     * @param timeStamp
     * @return string
     */
    public static String getFormattedTime(long timeStamp){
        //使用Format进行格式化  yyyy-MM-dd  HH:mm:ss
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date(timeStamp));
    }

    /**
     * yyyy-MM-dd
     * @return
     */
    public static String getFormattedDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }
}

```

#### 数据库操作类

```java
public class RecordDatebaseHelper extends SQLiteOpenHelper {
    private static String TAG = "RecordDatebaseHelper";
    //数据库名称
    public static String DB_NAME  = "Record";
    //SQL语句
    private static final String CREATE_DB = "create table Record(" +
            "id integer primary key autoincrement," +
            "uuid text ," +
            "type integer," +
            "amount double," +
            "category text," +
            "remark text," +
            "time integer," +
            "date date)";
    //上下文   数据库名称   null  版本
    public RecordDatebaseHelper(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);//创建语句
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新的时候执行  版本号的更新
    }

    /**
     * 插入数据
     * @param recordBean
     */
    public void addRecord(RecordBean recordBean){
        //获取到数据库   向数据中写入数据
        SQLiteDatabase database = this.getWritableDatabase();
        //传人数据
        ContentValues values = new ContentValues();
        values.put("uuid",recordBean.getUuid());
        values.put("type",recordBean.getRecordType());
        values.put("category",recordBean.getCategory());
        values.put("remark",recordBean.getRemark());
        values.put("amount",recordBean.getAmount());
        values.put("date",recordBean.getDate());
        values.put("time",recordBean.getTimeStamp());
        database.insert(DB_NAME,null,values);
    }

    /**
     * 删除
     * @param uuid
     */
    private void removeRecord(String uuid){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DB_NAME,"uuid=?",new String[]{uuid});
    }

    /**
     * 首先我们先将旧的删除，在将新的数据加入进去
     * @param uuid
     * @param bean
     */
    public void editRecord(String uuid,RecordBean bean){
        removeRecord(uuid);
        bean.setUuid(uuid);
        addRecord(bean);
    }

    /**
     * 查找出某一天的消费信息
     * @param dataStr
     * @return
     */
    public LinkedList<RecordBean> readRecord(String dataStr){
        LinkedList<RecordBean> linkedList = new LinkedList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        //查询表   条件是日期   排序方式是时间
        String sql = "select * from Record where date = ? order by time asc";
        Cursor cursor = database.rawQuery(sql,new String[]{dataStr});
        if (cursor.moveToFirst()){
            do{
                String uuid = cursor.getString(cursor.getColumnIndex("uuid"));
                int type = cursor.getInt(cursor.getColumnIndex("type"));
                String category = cursor.getString(cursor.getColumnIndex("category"));
                String remark = cursor.getString(cursor.getColumnIndex("remark"));
                double amount = cursor.getInt(cursor.getColumnIndex("amount"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                long timestamp = cursor.getLong(cursor.getColumnIndex("time"));
                RecordBean recordBean = new RecordBean();
                recordBean.setUuid(uuid);
                recordBean.setRecordType(type);
                recordBean.setCategory(category);
                recordBean.setRemark(remark);
                recordBean.setAmount(amount);
                recordBean.setDate(date);
                linkedList.add(recordBean);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return linkedList;
    }

    /**
     * 有消费的列表
     *
     * 实现思路：
     *      将所有的消费信息都加载出来,然后将数据加载出来
     * @return
     */
    public LinkedList<String> getAvaliableDate(){
        LinkedList<String> linkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCE * FROM Record ORDER BY DATE ASC",new String[]{});
        if (cursor.moveToFirst()){
            do{
                String date = cursor.getString(cursor.getColumnIndex("date"));
                if (!linkedList.contains(date)){
                    linkedList.add(date);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return linkedList;
    }
}

```

### 页面布局

![1559625486017](img\\1559625486017.png)

#### 书写上面布局

- 布局分析
  - 左边显示一个金钱和符号，下方显示一个floating符号，金钱的下部显示一个日期。
  - 使用Relative布局
  - 下方加好，这个吸附需要使用一个CoordinatorLayout布局。

- 金额显示部分，我们使用第三方技术来实现

  - 引入第三方框架

    ```xml
    implementation 'com.robinhood.ticker:ticker:1.2.2'
    ```

  - 页面中显示

    ```java
     <com.robinhood.ticker.TickerView
             android:textColor="@android:color/white"
             android:textSize="60sp"
             android:paddingTop="10dp"
             android:id="@+id/amount_text"
             android:layout_centerHorizontal="true"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content" />
    ```

  - 吸附

    ```
        <android.support.design.widget.AppBarLayout
            android:elevation="0dp"
            android:orientation="vertical"
            android:id="@+id/app_bar_layout"
            android:layout_width="match_parent"
            android:layout_height="120dp">
            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent">
                <RelativeLayout
                    android:id="@+id/top_re"
                    android:layout_width="match_parent"
                    android:layout_height="80dp">
                    <com.robinhood.ticker.TickerView
                        android:textColor="@android:color/white"
                        android:textSize="60sp"
                        android:paddingTop="10dp"
                        android:id="@+id/amount_text"
                        android:layout_centerHorizontal="true"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content" />
                    <TextView
                        android:textColor="@android:color/white"
                        android:gravity="center"
                        android:text="￥"
                        android:textSize="60dp"
                        android:layout_toRightOf="@id/amount_text"
                        android:id="@+id/currency_text"
                        android:layout_alignTop="@id/amount_text"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content" />
                </RelativeLayout>
    
                <TextView
                    android:id="@+id/date_text"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_below="@id/top_re"
                    android:layout_centerHorizontal="true"
                    android:text="JUN 22"
                    android:textStyle="bold"
                    android:textColor="@color/colorPrimaryDark" />
            </RelativeLayout>
        </android.support.design.widget.AppBarLayout>
    ```

#### 下方布局

下方布局可以进行左右滑动，所以使用一个viewpaper布局实现

```
<android.support.v4.view.ViewPager
        android:id="@+id/view_paper"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

    </android.support.v4.view.ViewPager>
```

下来我们的做法就是向里面加入数据

- 定位出资源

  ```JAVA
  viewPager = findViewById(R.id.view_paper);
  ```

  

- 对其设置Adapter，我们使用Fragment布局进行显示，所以继承一个FragmentPagerAdapter，实现一个方法，在这个里面一个有以下几个方法。

  ```java
  //获取一个Fragment
  public Fragment getItem(int i);
  //返回页面的个数
  public int getCount()；
  //初始化Fragment
  private void initFragment();
  //获取当前的
  public int getCurrentItem();
      
      
  @Override
  public Fragment getItem(int i) {
     return fragments.get(i);
  }
  @Override
  public int getCount() {
      return fragments.size();
  }
  private void initFragment(){
      dates.add("XXX1");
      dates.add("XXX2");
      dates.add("XXX3");
      for(String date : dates){
           MainFragment fragment = new MainFragment(date);
           fragments.add(fragment);
       }
  }
  public int getCurrentItem(){
     return dates.size()-1;
  }
  ```

- 上面使用的MainFragment布局

  ![1559627149289](img\\1559627149289.png)

  - xml书写

    ```java
    <?xml version="1.0" encoding="utf-8"?>
    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <TextView
                android:layout_width="match_parent"
                android:layout_height="35dp"
                android:text="JJ  22"
                android:id="@+id/day_text"
                android:paddingLeft="15dp"
                android:textColor="@android:color/white"
                android:gravity="center_vertical"
                android:background="#5e5e5e"/>
            <ListView
                android:layout_below="@id/day_text"
                android:id="@+id/list_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"></ListView>
        </RelativeLayout>
        <RelativeLayout
            android:id="@+id/no_record"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <TextView
                android:layout_width="wrap_content"
                android:text="NO RECORD"
                android:textSize="20dp"
                android:layout_centerInParent="true"
                android:layout_height="wrap_content" />
        </RelativeLayout>
    </FrameLayout>
    ```

    

  - java逻辑书写,我们创建MainFragment，

    ```java
    @SuppressLint("ValidFragment")
    public class MainFragment extends Fragment {
        private TextView textView ;
        private ListView listView;
        private String data="";
        private View rootView;
    
        private LinkedList<RecordBean> linkedList = null;
    
        private ListViewAdapter listViewAdapter;
        @SuppressLint("ValidFragment")
        public MainFragment(String date){
            linkedList = new LinkedList<>();
            this.data = date;
            linkedList.add(new RecordBean());
            linkedList.add(new RecordBean());
            linkedList.add(new RecordBean());
            linkedList.add(new RecordBean());
            linkedList.add(new RecordBean());
        }
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment,container,false);
            initView();
            return rootView;
        }
    
        public void initView(){
            textView = rootView.findViewById(R.id.day_text);
            listView = rootView.findViewById(R.id.list_view);
            textView.setText(data);
            listViewAdapter = new ListViewAdapter(getContext());
            listView.setAdapter(listViewAdapter);
            listViewAdapter.setData(linkedList);
            if(listViewAdapter.getCount()>0){
                rootView.findViewById(R.id.no_record).setVisibility(View.GONE);
            }
        }
    }
    
    ```

    

- 





[TOC]

