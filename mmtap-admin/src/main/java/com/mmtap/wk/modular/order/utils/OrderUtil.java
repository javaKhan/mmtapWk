package com.mmtap.wk.modular.order.utils;


import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.UUID;

/**
 * 订单工具类
 */
public class OrderUtil {
    /**
     * 订单ID生成策略
     *  时间-xxxx(随机数)
     * @return
     */
    public static String createOrderID(){
        StringBuilder sb = new StringBuilder();
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
        Random d = new Random(UUID.randomUUID().hashCode());
        int str2 = 1000+d.nextInt(9000);
        return sb.append(time).append("-").append(str2).toString();
    }

    /**
     * 客户ID生成器
     * @return
     */
    public static String createCustomID() {
        return UUID.randomUUID().toString();
    }
}
