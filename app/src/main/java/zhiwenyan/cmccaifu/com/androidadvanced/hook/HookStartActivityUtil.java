package zhiwenyan.cmccaifu.com.androidadvanced.hook;

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:
 * Data：8/15/2018-9:50 AM
 *
 * @author yanzhiwen
 */
public class HookStartActivityUtil {

    public void hookStartActivity() throws Exception {

        Class<?> amnClass = Class.forName("android.app.ActivityManager");
        Field gDefaultField = amnClass.getDeclaredField("IActivityManagerSingleton");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);
        //反射SingleTon
        Class<?> singleTonClass = Class.forName("android.util.Singleton");
        //获取gDefault的instance属性
        Field mInstanceFiled = singleTonClass.getDeclaredField("mInstance");
        mInstanceFiled.setAccessible(true);
        Object iamInstance = mInstanceFiled.get(gDefault);
        //开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
        Class<?> iamClass = Class.forName("android.app.IActivityManager");
        Proxy.newProxyInstance(HookStartActivityUtil.this.getClass().getClassLoader(), new Class[]{iamClass}, new StartActivityInvocationHandler(iamInstance));
        mInstanceFiled.set(gDefault, mInstanceFiled);
    }

    private class StartActivityInvocationHandler implements InvocationHandler {
        private Object mObject;

        public StartActivityInvocationHandler(Object object) {
            this.mObject = object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Log.i("TAG", "invoke: " + method.getName());
            return method.invoke(mObject, args);
        }
    }
}
