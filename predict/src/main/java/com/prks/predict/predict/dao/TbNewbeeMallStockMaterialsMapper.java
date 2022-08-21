package com.prks.predict.predict.dao;

import com.prks.predict.predict.entity.TbNewbeeMallStockMaterials;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TbNewbeeMallStockMaterialsMapper {
    //查询原材料库存
    TbNewbeeMallStockMaterials getStockById(int materialId);
    //查询原材料库存
    TbNewbeeMallStockMaterials getStockByName(String materialName);

    //更新库存
    int updateStockByName(String materialsName,int newStock);

    //增加新种类的原料
    int addNewMetrial(TbNewbeeMallStockMaterials tbNewbeeMallStockMaterials);
}
