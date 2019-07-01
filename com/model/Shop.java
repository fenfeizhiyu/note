package model;

import java.text.NumberFormat;
import java.util.Random;

public class Shop {

    private static final long sleepTime=2000L;


    /**
     * 商店名称 ： A,b，C.....
     */
    private String shopName;

    public Shop(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 随机获取一个价格
     * @param product
     * @return
     */
    public double getPrice(String product){
        delay();
        Random random = new Random(product.charAt(0) * product.charAt(product.length() - 1));
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        return Double.valueOf(nf.format(shopName.charAt(0)*random.nextDouble() * 100*product.charAt(0)*product.charAt(product.length()-1)));
    }


    /**
     * 模拟延时
     */
    public static void delay(){
        try {
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
