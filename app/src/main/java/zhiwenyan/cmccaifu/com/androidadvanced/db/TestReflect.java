package zhiwenyan.cmccaifu.com.androidadvanced.db;

import java.lang.reflect.Field;

/**
 * Created by zhiwenyan on 6/7/17.
 * <p>
 * 练习下反射
 */
class User {
    private String name;
    private int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class TestReflect {
    public static void main(String[] args) throws IllegalAccessException {
        User user = new User();
        user.setAge(22);
        user.setName("Steven");
        Class<?> clazz = User.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            String name = field.getName();
            //String value = field.get(User.class).toString();

            String type = field.getType().getName();
            System.out.println(name + "," + type + ",");
        }
    }
}
