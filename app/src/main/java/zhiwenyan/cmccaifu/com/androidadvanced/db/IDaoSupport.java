package zhiwenyan.cmccaifu.com.androidadvanced.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by zhiwenyan on 6/6/17.
 */

public interface IDaoSupport<T> {
    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    long insert(T t);

    void insert(List<T> datas);

    //查询所有
    List<T> query();

    //按照语句查询

}
