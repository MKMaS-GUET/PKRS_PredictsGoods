package com.prks.predict.predict.entity;

public class TbNewbeeMallGoodsComposition {
    //商品表主键id
    private int goodsId;
    //商品名称
    private String goodsName;
    //是否可以被组装
    private boolean isFormed;
    //商品组装时间，【以秒为单位】
    private int formedTime;
    //所需原料id列表,用;分隔
    private String components;

    //商品图片Url
    private String goodsImg;

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
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

    public boolean isFormed() {
        return isFormed;
    }

    public void setFormed(boolean formed) {
        isFormed = formed;
    }

    public int getFormedTime() {
        return formedTime;
    }

    public void setFormedTime(int formedTime) {
        this.formedTime = formedTime;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    @Override
    public String toString() {
        return "TbNewbeeMallGoodsComposition{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", isFormed=" + isFormed +
                ", formedTime=" + formedTime +
                ", components='" + components + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                '}';
    }
}
