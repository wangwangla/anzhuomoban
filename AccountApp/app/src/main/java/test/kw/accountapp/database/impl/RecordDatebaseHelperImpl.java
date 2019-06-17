package test.kw.accountapp.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

import test.kw.accountapp.database.RecordDatabaseHelper;
import test.kw.accountapp.domain.RecordBean;
import test.kw.accountapp.utils.Contant;

import static test.kw.accountapp.utils.Contant.DB_NAME;

public class RecordDatebaseHelperImpl extends RecordDatabaseHelper {
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
    public RecordDatebaseHelperImpl(Context context, String name,SQLiteDatabase.CursorFactory factory, int version) {
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
        values.clear();
        Log.d("Insert",recordBean.toString());
    }

    /**
     * 删除
     * @param uuid
     */
    public void removeRecord(String uuid){
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
        Cursor cursor = db.rawQuery("select * FROM Record ORDER BY DATE ASC",new String[]{});
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
