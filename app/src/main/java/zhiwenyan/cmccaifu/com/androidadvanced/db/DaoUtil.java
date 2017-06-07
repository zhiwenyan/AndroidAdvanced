package zhiwenyan.cmccaifu.com.androidadvanced.db;

/**
 * Created by zhiwenyan on 6/6/17.
 */

public class DaoUtil {

    public static String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
    }

    public static String getColumnType(String type) {
        String value = null;
        if (type.contains("String")) {
            value = "text";
        } else if (type.contains("int")) {
            value = "integer";
        } else if (type.contains("boolean")) {
            value = "boolean";
        } else if (type.contains("float")) {
            value = "float";
        } else if (type.contains("double")) {
            value = "double";
        } else if (type.contains("char")) {
            value = "varchar";
        } else if (type.contains("long")) {
            value = "long";
        }
        return value;
    }

}
