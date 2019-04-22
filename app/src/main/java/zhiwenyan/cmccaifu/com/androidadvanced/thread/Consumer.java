package zhiwenyan.cmccaifu.com.androidadvanced.thread;

/**
 * Description:
 * Data：1/8/2019-9:40 AM
 *
 * @author yanzhiwen
 */
public class Consumer {
    private final String lock;

    public Consumer(String lock) {
        super();
        this.lock = lock;
    }

    public void getValue() {
        try {
            synchronized (lock) {
                if (StringObject.value.equals("")) {
                    //没值，不进行消费
                    lock.wait();
                }
                System.out.println("get的值是：" + StringObject.value);
                StringObject.value = "";
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
