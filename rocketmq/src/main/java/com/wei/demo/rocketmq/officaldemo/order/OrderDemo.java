package com.wei.demo.rocketmq.officaldemo.order;

/**
 * @Author: weiwenfeng
 * @Date: 2018/11/1
 */
public class OrderDemo {
    private long orderId;

    private String desc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderDemo{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
