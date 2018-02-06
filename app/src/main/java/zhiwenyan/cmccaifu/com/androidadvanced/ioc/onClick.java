package zhiwenyan.cmccaifu.com.androidadvanced.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:View 事件注解的Annotation
 * Data：2/6/2018-1:59 PM
 *
 * @author: yanzhiwen
 */
//FILED 属性 TYPE 类上 CONSTRUCTOR构造函数上
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface onClick {
    int[] value();
}
