package zhiwenyan.cmccaifu.com.androidadvanced.thread;

/**
 * Description:
 * Data：1/8/2019-9:41 AM
 *
 * @author yanzhiwen
 */
public class ThreadConsumer extends Thread{
    private Consumer consumer;

    public ThreadConsumer(Consumer consumer) {
        super();
        this.consumer = consumer;
    }
    @Override
    public void run() {
        //死循环，不断的消费
        while(true){
            consumer.getValue();
        }
    }

}