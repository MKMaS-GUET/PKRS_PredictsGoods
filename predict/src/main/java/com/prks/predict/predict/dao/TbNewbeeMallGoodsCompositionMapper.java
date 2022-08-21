package com.prks.predict.predict.dao;

import com.prks.predict.predict.entity.TbNewbeeMallGoodsComposition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbNewbeeMallGoodsCompositionMapper {
    //更具商品id查询商品组成表
    TbNewbeeMallGoodsComposition selectById(int goodsId);

    //根据商品名称查询商品组成表
    TbNewbeeMallGoodsComposition selectByName(String goodsName);

    //向商品组成中插入新商品的组成
    int insertTbNewbeeMallGoodsComposition(TbNewbeeMallGoodsComposition tbNewbeeMallGoodsComposition);

    //更新商品组装时间
    int updateFormedTime(int goodsId, int formedTime);

    //更具商品id查询商品组成表
    List<TbNewbeeMallGoodsComposition> selectAllComp();
}
