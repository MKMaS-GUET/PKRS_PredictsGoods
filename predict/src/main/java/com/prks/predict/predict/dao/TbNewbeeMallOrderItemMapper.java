package com.prks.predict.predict.dao;

import com.prks.predict.predict.entity.TbNewbeeMallGoodsInfo;
import com.prks.predict.predict.entity.TbNewbeeMallOrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TbNewbeeMallOrderItemMapper {
    List<TbNewbeeMallOrderItem> selectAllGoodsByOrderId(long orderId);
}
