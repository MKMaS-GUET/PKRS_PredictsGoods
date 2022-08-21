package com.prks.predict.predict.entity;


import java.util.Date;


public class TbNewbeeMallOrderItem {
    private long orderItemid;
    private long orderId;
    private int goodsId;
    private String goodsName;
    private String goodsCoverImg;
    private int sellingPrice;
    private int goodsCount;
    private Date createTime;

    @Override
    public String toString() {
        return "TbNewbeeMallOrderItem{" +
                "orderItemid=" + orderItemid +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsCoverImg='" + goodsCoverImg + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", goodsCount=" + goodsCount +
                ", createTime=" + createTime +
                '}';
    }

    public long getOrderItemid() {
        return orderItemid;
    }

    public void setOrderItemid(long orderItemid) {
        this.orderItemid = orderItemid;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCoverImg() {
        return goodsCoverImg;
    }

    public void setGoodsCoverImg(String goodsCoverImg) {
        this.goodsCoverImg = goodsCoverImg;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
