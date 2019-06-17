package test.kw.accountapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.LinkedList;
import test.kw.accountapp.domain.RecordBean;
public abstract class RecordDatabaseHelper extends SQLiteOpenHelper {

    public RecordDatabaseHelper(Context context,  String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public abstract void addRecord(RecordBean recordBean);

    public abstract void removeRecord(String uuid);

    public abstract void editRecord(String uuid,RecordBean bean);

    public abstract LinkedList<RecordBean> readRecord(String dataStr);

    public abstract LinkedList<String> getAvaliableDate();
}
