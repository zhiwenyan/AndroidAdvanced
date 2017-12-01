package zhiwenyan.cmccaifu.com.androidadvanced.utils;

/**
 * Created by yanzhiwen on 2017/10/24.
 */

public class Area {
    private int m = 30;

    /**
     * 三角形的面积=低*高/2
     */
    public double SquereArea() {
        double height = Math.sqrt(m * m - m / 2 * m / 2);
        return m * height / 2;
    }

    /**
     * 圆的面积 PI*r*r
     *
     * @return
     */
    public double CircleArea() {
        double r = m / 2;
        return Math.PI * r * r;
    }

    public static void main(String[] args) {
        Area area = new Area();
        double space = area.SquereArea() - area.CircleArea();
        System.out.println("面积==" + space);
    }
}
