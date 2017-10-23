package zhiwenyan.cmccaifu.com.androidadvanced.proxy.staticProxy;

/**
 * Created by zhiwenyan on 5/30/17.
 * 银行业务员
 */

public class SalesMan implements IBank {
    private IBank man;

    public SalesMan(IBank man) {
        this.man = man;
    }

    @Override
    public void applyCard() {
        System.out.println("先搞完一些流程");
        //掉用我们的方法
        man.applyCard();
        System.out.println("完毕");
    }

    @Override
    public void loseCard() {
        System.out.println("先搞完一些流程");
        //掉用我们的方法
        man.loseCard();
        System.out.println("完毕");
    }
}
