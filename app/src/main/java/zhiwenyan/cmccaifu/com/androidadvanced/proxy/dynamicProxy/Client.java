package zhiwenyan.cmccaifu.com.androidadvanced.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import zhiwenyan.cmccaifu.com.androidadvanced.proxy.staticProxy.IBank;
import zhiwenyan.cmccaifu.com.androidadvanced.proxy.staticProxy.Man;

/**
 * Created by zhiwenyan on 5/30/17.
 */

public class Client {
    public static void main(String[] args) {
        Man man = new Man();
        //JDK动态代理:代理了所有的方法
        IBank proxy = (IBank) Proxy.newProxyInstance(IBank.class.getClassLoader(),
                man.getClass().getInterfaces(),  //接口类数组  //man.getClass.
                new BankInvoketionHandler(man)); //方法回调
        //动态代理：解析interface所有方法 新建一个class
        proxy.applyCard();
        System.out.println("--------------");
        proxy.loseCard();

    }

    private static class BankInvoketionHandler implements InvocationHandler {
        private IBank man;

        public BankInvoketionHandler(IBank man) {
            this.man = man;
        }

        /**
         * @param proxy  代理对象
         * @param method 代理的方法
         * @param args
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            //方法回调  方法执行还是要Man执行
            System.out.println("做些流程");
            System.out.println("方法执行：" + method.getName());
            //man 方法的执行对象
            Object object = method.invoke(man, args);
            System.out.println("完毕");
            return object;
        }
    }
}
