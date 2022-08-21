package com.prks.predict.predict.dao;

import com.prks.predict.predict.entity.TbNewbeeMallOrderQueue;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface TbNewbeeMallOrderQueueMapper {
    int insertOrder(TbNewbeeMallOrderQueue tbNewbeeMallOrderQueue);
    TbNewbeeMallOrderQueue selectLastQueNum();
    int updateFinishedTime(long orderId, Date finishedTime);
    int updateStatus(long orderId, int orderStatus);
    TbNewbeeMallOrderQueue selectQueueByOrderId(long orderId);
//    int updateOrderTime(long orderId, long formedTime);
}
