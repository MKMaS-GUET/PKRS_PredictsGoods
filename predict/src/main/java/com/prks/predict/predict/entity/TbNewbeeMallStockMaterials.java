package com.prks.predict.predict.entity;

public class TbNewbeeMallStockMaterials {
    private int materialsId;
    private String materialsName;
    private int stockNum;

    public int getMaterialsId() {
        return materialsId;
    }

    public void setMaterialsId(int materialsId) {
        this.materialsId = materialsId;
    }

    public String getMaterialsName() {
        return materialsName;
    }

    public void setMaterialsName(String materialsName) {
        this.materialsName = materialsName;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    @Override
    public String toString() {
        return "TbNewbeeMallStockMaterials{" +
                "materialsId=" + materialsId +
                ", materialsName='" + materialsName + '\'' +
                ", stockNum=" + stockNum +
                '}';
    }
}
