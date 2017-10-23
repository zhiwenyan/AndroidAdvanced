package zhiwenyan.cmccaifu.com.androidadvanced.proxy.staticProxy;

/**
 * Created by zhiwenyan on 5/30/17.
 * <p>
 * 办卡的人
 */

public class Man implements IBank {
    @Override
    public void applyCard() {
        System.out.println("申请办卡");
    }

    @Override
    public void loseCard() {
        System.out.println("挂失");
    }
}
