package zhiwenyan.cmccaifu.com.androidadvanced.ioc;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import zhiwenyan.cmccaifu.com.androidadvanced.utils.NetworkUtils;


/**
 * Description:
 * Data：2/6/2018-2:06 PM
 *
 * @author: yanzhiwen
 */
public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);

    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);

    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    /**
     * 兼容上面三个类  反射执行的类
     *
     * @param viewFinder
     * @param object
     */
    private static void inject(ViewFinder viewFinder, Object object) {
        injectFiled(viewFinder, object);
        injectEvent(viewFinder, object);
    }

    /**
     * 注入属性
     *
     * @param viewFinder
     * @param object
     */
    private static void injectFiled(ViewFinder viewFinder, Object object) {
        //获取类里面所有的属性
        Class<?> clz = object.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取ViewById的Value的值
                int viewId = viewById.value();
                //findViewById找到View
                View view = viewFinder.findViewById(viewId);
                //动态注入找到的View
                if (view != null) {
                    field.setAccessible(true);
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    /**
     * 注入事件
     *
     * @param viewFinder
     * @param object
     */
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        //获取类里面所有的方法
        Class<?> clz = object.getClass();
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
            onClick onClick = method.getAnnotation(onClick.class);
            if (onClick != null) {
                //获取ViewById的Value的值
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //findViewById找到View
                    View view = viewFinder.findViewById(viewId);

                    //扩展功能 检查网络
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    if (view != null)
                        view.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet));
                }
            }
        }
    }


    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Method mMethod;
        private Object mObject;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            if (mIsCheckNet) {
                if (!NetworkUtils.isNetAvailable(v.getContext())) {
                    Toast.makeText(v.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            try {
                mMethod.setAccessible(true);
                //反射执行方法
                mMethod.invoke(mObject, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}
