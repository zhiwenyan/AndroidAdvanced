package zhiwenyan.cmccaifu.com.androidadvanced.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by zhiwenyan on 6/6/17。
 *
 */

public class DaoSupport<T> implements IDaoSupport<T> {
    private SQLiteDatabase mSQLiteDatabase;
    private Class<T> mClazz;
    private static final Object[] mPutMethods = new Object[2];
    private static final Map<String, Method> mMethods = new ArrayMap<>();

    @Override
    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSQLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
        //创建表
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("create table if not exists ")
                .append(DaoUtil.getTableName(mClazz))
                .append("(id integer primary key autoincrement, ");
        Field[] fields = mClazz.getDeclaredFields();

        for (int i = 0; i < fields.length - 2; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            String name = field.getName();  //属性的名称
            String type = field.getType().getSimpleName(); //int string  boolean 属性的修饰符的名称
            //type需要进行转换
            stringBuffer.append(name).append(" ").append(DaoUtil.getColumnType(type)).append(", ");
        }
        stringBuffer.replace(stringBuffer.length() - 2, stringBuffer.length(), ")");
        String sql = stringBuffer.toString();

        Log.i("TAG", "创建表的语句：" + sql);
        mSQLiteDatabase.execSQL(sql);
    }

    /**
     * 插入
     *
     * @param t
     * @return
     */
    @Override
    public long insert(T t) {
        ContentValues values = contentValuesObj(t);

        return mSQLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    /**
     * 泛型转object
     *
     * @param obj
     * @return
     */
    private ContentValues contentValuesObj(T obj) {
        ContentValues contentValues = new ContentValues();
        //封装values
        Field[] fields = mClazz.getDeclaredFields();  //取得本类的全部属性
        try {
            for (int i = 0; i < fields.length - 2; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                String key = field.getName(); //获取属性名
                Object value = field.get(obj); //获取值
                mPutMethods[0] = key;
                mPutMethods[1] = value;
                //获取put方法 缓存方法
                String filedTypeName = field.getType().getName();
                Method putMethod = mMethods.get(filedTypeName); //null put
                if (putMethod == null) {
                    //获取put方法
                    putMethod = ContentValues.class.getDeclaredMethod("put",
                            String.class, value.getClass());
                    mMethods.put(filedTypeName, putMethod);
                }
                //反射执行方法
                putMethod.invoke(contentValues, mPutMethods);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mPutMethods[0] = null;
            mPutMethods[1] = null;
        }
        return contentValues;
    }

    //批量插入
    @Override
    public void insert(List<T> datas) {
        //批量插入采用事物
        mSQLiteDatabase.beginTransaction();
        for (T data : datas) {
            insert(data);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();

    }

    //查询所有
    @Override
    public List<T> query() {
        Cursor cursor = mSQLiteDatabase.query(DaoUtil.getTableName(mClazz),
                null, null, null, null, null, null);
        return cursorToList(cursor);
    }

    private List<T> cursorToList(Cursor cursor) {
//        List<T> list = new ArrayList<>();
//        if (cursor != null && cursor.moveToFirst()) {
//            do{
//                try{
//
//                }catch (){
//
//                }
//            }while ()
//        }
        return null;
    }
}
