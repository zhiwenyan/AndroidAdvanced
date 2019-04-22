package zhiwenyan.cmccaifu.com.androidadvanced.thread;

/**
 * Description:
 * Data：1/8/2019-9:41 AM
 *
 * @author yanzhiwen
 */
public class ThreadProduct extends Thread{
    private Product product;

    public ThreadProduct(Product product) {
        super();
        this.product = product;
    }
    @Override
    public void run() {
        //死循环，不断的生产
        while(true){
            product.setValue();
        }
    }

}
