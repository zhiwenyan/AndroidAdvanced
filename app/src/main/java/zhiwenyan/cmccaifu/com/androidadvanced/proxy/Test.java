package zhiwenyan.cmccaifu.com.androidadvanced.proxy;

/**
 * Created by zhiwenyan on 5/30/17.
 */

public class Test {
    public static void main(String[] args) {
        Man man=new Man();
        SalesMan salesMan=new SalesMan(man);
        salesMan.applyCard();
        salesMan.loseCard();
    }
}
