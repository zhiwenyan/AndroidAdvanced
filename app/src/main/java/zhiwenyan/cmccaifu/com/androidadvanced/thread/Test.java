package zhiwenyan.cmccaifu.com.androidadvanced.thread;

/**
 * Description:
 * Data：1/8/2019-9:41 AM
 *
 * @author yanzhiwen
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        String lock = new String("");
        Product product = new Product(lock);
        Consumer consumer = new Consumer(lock);
        ThreadProduct pThread = new ThreadProduct(product);
        ThreadConsumer cThread = new ThreadConsumer(consumer);
        pThread.start();
        cThread.start();
    }
}
