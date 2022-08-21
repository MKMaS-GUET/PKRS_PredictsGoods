package com.prks.predict.predict.entity;

import java.util.Date;

public class TbNewbeeMallOrderQueue {
    private long orderId;
    private long orderFormedTime;
    private int queNum;
    private Date finishedTime;
    private int orderStatus;

    @Override
    public String toString() {
        return "TbNewbeeMallOrderQueue{" +
                "orderId=" + orderId +
                ", orderFormedTime=" + orderFormedTime +
                ", queNum=" + queNum +
                ", finishedTime=" + finishedTime +
                ", orderStatus=" + orderStatus +
                '}';
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public int getQueNum() {
        return queNum;
    }

    public void setQueNum(int queNum) {
        this.queNum = queNum;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderFormedTime() {
        return orderFormedTime;
    }

    public void setOrderFormedTime(long orderFormedTime) {
        this.orderFormedTime = orderFormedTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }
}
