package com.prks.predict.predict.service;

import com.prks.predict.predict.dao.*;
import com.prks.predict.predict.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderPredictService {

    @Autowired
    private TbNewbeeMallOrderItemMapper orderItemMapper;

    @Autowired
    private TbNewbeeMallGoodsCompositionMapper compositionMapper;

    @Autowired
    private TbNewbeeMallGoodsInfoMapper goodsMapper;

    @Autowired
    private TbNewbeeMallStockMaterialsMapper stockMapper;

    @Autowired
    private TbNewbeeMallOrderQueueMapper orderQueueMapper;

    public List<TbNewbeeMallOrderItem> findAllGoodsInOrder(long orderId){
        return orderItemMapper.selectAllGoodsByOrderId(orderId);
    }

    public TbNewbeeMallGoodsComposition findMaterialsByGoodId(int goodId){
        return compositionMapper.selectById(goodId);
    }

    public Map<String,Integer> lackedMaterials(int goodId, int needsCount){
        Map<String,Integer> lacks = new HashMap<>();
        TbNewbeeMallGoodsComposition comp = compositionMapper.selectById(goodId);
        String[] allMaterials = comp.getComponents().split(";");
        for(int i=0;i<allMaterials.length;i++){
            String[] nameAndNeed=allMaterials[i].split("##");
            String materialName = nameAndNeed[0];
            int singleNeed= Integer.parseInt(nameAndNeed[1]);
            int stock = stockMapper.getStockByName(materialName).getStockNum();
            //此时，已经拿到库存，进行更新，然后设置订单的队列状态
            if(stock<singleNeed*needsCount){
                lacks.put(materialName,Math.abs(stock-singleNeed*needsCount));
            }
            //更新订单库存
            if(stock-singleNeed*needsCount>=0){
                stockMapper.updateStockByName(materialName,stock-singleNeed*needsCount);
            }else{
                stockMapper.updateStockByName(materialName,0);
            }
        }
        return lacks;
    }

    public TbNewbeeMallOrderQueue selectLastQueNum(){
        return orderQueueMapper.selectLastQueNum();
    }

    public int insertOrderQueue(TbNewbeeMallOrderQueue queue){
        return orderQueueMapper.insertOrder(queue);
    }

    public TbNewbeeMallGoodsInfo findGoodsInfoById(int id){
        return goodsMapper.queryGoodsById(id);
    }

    public int updateOrderFinishedTime(long orderId, Date date){
        return orderQueueMapper.updateFinishedTime(orderId, date);
    }

    public int updateOrderStatus(long orderId, int status){
        return orderQueueMapper.updateStatus(orderId,status);
    }

    public TbNewbeeMallOrderQueue selectOrderQueueById(long orderId){
        return orderQueueMapper.selectQueueByOrderId(orderId);
    }
}
