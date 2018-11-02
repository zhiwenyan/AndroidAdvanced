package zhiwenyan.cmccaifu.com.androidadvanced.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description:
 * Data：22/03/2018
 * Author: Steven
 */
public class SqliteHelper extends SQLiteOpenHelper {
    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person (personid integer primary key autoincrement, name VARCHAR(20), age INTEGER)");
//        db.execSQL("insert into person(name, age) values('steven', 4)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
