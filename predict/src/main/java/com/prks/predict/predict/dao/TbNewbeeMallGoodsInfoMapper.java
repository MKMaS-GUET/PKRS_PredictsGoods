package com.prks.predict.predict.dao;

import com.prks.predict.predict.entity.TbNewbeeMallGoodsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbNewbeeMallGoodsInfoMapper {

    List<TbNewbeeMallGoodsInfo>  selectAllGoods();
    TbNewbeeMallGoodsInfo queryGoodsById(int goodsId);
}
