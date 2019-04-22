package zhiwenyan.cmccaifu.com.androidadvanced.thread;

/**
 * Description:
 * Data：1/8/2019-9:40 AM
 *
 * @author yanzhiwen
 */
public class Product {
    private final String lock;

    public Product(String lock) {
        super();
        this.lock = lock;
    }
    public void setValue(){
        try {
            synchronized (lock) {
                if(!StringObject.value.equals("")){
                    //有值，不生产
                    lock.wait();
                }
                String  value = System.currentTimeMillis()+""+System.nanoTime();
                System.out.println("set的值是："+value);
                StringObject.value = value;
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
