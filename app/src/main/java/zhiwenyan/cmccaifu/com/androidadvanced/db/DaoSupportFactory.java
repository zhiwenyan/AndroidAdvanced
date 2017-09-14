package zhiwenyan.cmccaifu.com.androidadvanced.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by zhiwenyan on 6/6/17.
 */

public class DaoSupportFactory {

    private SQLiteDatabase mSQLiteDatabase;

    //持有外部数据库的引用  判断是否有内存卡，6.0需要动态申请权限；
    private DaoSupportFactory() {
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "nhdz" + File.separator
                + "database");
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot, "nhdz.db");

        //打开或者创建一个数据库
        mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public static DaoSupportFactory getInstance() {
        return SingleTon.INSTANCE;
    }

    private static class SingleTon {
        private final static DaoSupportFactory INSTANCE = new DaoSupportFactory();
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        IDaoSupport<T> daoSupport = new DaoSupport();
        daoSupport.init(mSQLiteDatabase, clazz);
        return daoSupport;

    }
}
