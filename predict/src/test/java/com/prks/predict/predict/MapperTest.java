package com.prks.predict.predict;


import com.prks.predict.predict.dao.TbNewbeeMallGoodsCompositionMapper;
import com.prks.predict.predict.dao.TbNewbeeMallGoodsInfoMapper;
import com.prks.predict.predict.dao.TbNewbeeMallOrderItemMapper;
import com.prks.predict.predict.dao.TbNewbeeMallStockMaterialsMapper;
import com.prks.predict.predict.entity.*;

import com.prks.predict.predict.service.OrderPredictService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;


@SpringBootTest
@ContextConfiguration(classes = PredictApplication.class)
public class MapperTest {

    @Autowired
    private TbNewbeeMallGoodsCompositionMapper compositionMapper;

    @Autowired
    private TbNewbeeMallStockMaterialsMapper meterialMapper;

    @Autowired
    private TbNewbeeMallGoodsInfoMapper goodsMapper;

    @Autowired
    private TbNewbeeMallOrderItemMapper orderItemMapper;

    @Autowired
    private OrderPredictService orderService;


    @Test
    public void testSelectGoods(){
        TbNewbeeMallGoodsComposition composition = compositionMapper.selectById(11);
        System.out.println(composition);
        composition = compositionMapper.selectByName("测试商品");
        System.out.println(composition);
    }
    @Test
    public void testInsertGoods(){
        TbNewbeeMallGoodsComposition composition = new TbNewbeeMallGoodsComposition();
        composition.setGoodsId(12);
        composition.setGoodsName("测试商品2");
        composition.setFormed(false);
        composition.setFormedTime(-1);
        composition.setComponents("测试商品2");
        int rows = compositionMapper.insertTbNewbeeMallGoodsComposition(composition);
        System.out.println(rows);
    }
    @Test
    public void updateGoods(){
        compositionMapper.updateFormedTime(11,2356);
    }

    @Test
    public void testMetrialStock(){
//        TbNewbeeMallStockMaterials metrials = meterialMapper.getStockById(11);
//        System.out.println(metrials);
//        meterialMapper.updateStockByName("口红粉",999);

//        metrials = new TbNewbeeMallStockMaterials();
//        metrials.setMaterialsId(12);
//        metrials.setMaterialsName("测试材料2");
//        metrials.setStockNum(10);
//        meterialMapper.addNewMetrial(metrials);
    }

    //为商品组成表插入数据
    @Test
    public void testGoods(){
        List<TbNewbeeMallGoodsInfo> goodsList = goodsMapper.selectAllGoods();
        Map<Integer,Integer> compTime = new HashMap<>();
        Map<Integer,String> compMap = new HashMap<>();
        Set<Long> isNotFormedSet = new HashSet<>();
        isNotFormedSet.add(0L);isNotFormedSet.add(20L);
        //默认手机、口红组装时间10min
        compTime.put(45,600);compTime.put(46,600);compTime.put(51,600);
        compMap.put(45,"TP/LCD组件##1;中框##1;主板##1;小板##1;摄像头##1;转接排线/同轴线##2;指纹模组##1;扬声器/听筒##2;天线/无线线圈##1;电池##1;电池盖##1");
        compMap.put(46,"TP/LCD组件##1;中框##1;主板##1;小板##1;摄像头##1;转接排线/同轴线##2;指纹模组##1;扬声器/听筒##2;天线/无线线圈##1;电池##1;电池盖##1");
        compMap.put(51,"TP/LCD组件##1;中框##1;主板##1;小板##1;摄像头##1;转接排线/同轴线##2;指纹模组##1;扬声器/听筒##2;天线/无线线圈##1;电池##1;电池盖##1");
        compTime.put(86,600);
        compMap.put(86,"口红粉##2;基础油##1;蜂蜡##1;小烛树蜡##1;维生素E##1");
        compTime.put(117,200);//音响200s
        compMap.put(117,"喇叭##2;分频器##1;接线柱##2;吸音棉##2;内六角螺丝##8;音响线##2;油漆##2");
        //电脑1800s
        compTime.put(125,1800);compTime.put(202,1800);compTime.put(207,1800);
        compMap.put(125,"CPU处理器##1;显卡##1;内存条##2;硬盘##2;主板##1;机箱##1;散热器##1;电源##1;鼠标##1;键盘套装##1;显示器##1");
        compMap.put(202,"CPU处理器##1;显卡##1;内存条##2;硬盘##2;主板##1;机箱##1;散热器##1;电源##1;鼠标##1;键盘套装##1;显示器##1");
        compMap.put(207,"CPU处理器##1;显卡##1;内存条##2;硬盘##2;主板##1;机箱##1;散热器##1;电源##1;鼠标##1;键盘套装##1;显示器##1");
        //蓝牙耳机500s
        compTime.put(142,500);
        compMap.put(142,"充电接口电路##1;电池电路##1;蓝牙收发模块##1;音频组件##1;按键电路##1;受话电路##1;铜电板##1;状态灯##1;电池##1");
        compTime.put(151,600);
        compMap.put(151,"TP/LCD组件##1;中框##1;主板##1;小板##1;摄像头##1;转接排线/同轴线##2;指纹模组##1;扬声器/听筒##2;天线/无线线圈##1;电池##1;电池盖##1");
        for(int i=0;i<goodsList.size();i++){
            TbNewbeeMallGoodsInfo itemGoods = goodsList.get(i);
            TbNewbeeMallGoodsComposition itemComp = new TbNewbeeMallGoodsComposition();
            itemComp.setGoodsId(itemGoods.getGoodsId());
            itemComp.setGoodsName(itemGoods.getGoodsName());
            String imgUrl = itemGoods.getGoodsCoverImg();
            if(isNotFormedSet.contains(itemGoods.getGoodsCategoryId())){
                itemComp.setFormed(false);
                itemComp.setFormedTime(-1);
                itemComp.setComponents(itemGoods.getGoodsName()+"##1");
                itemComp.setGoodsImg(imgUrl);
            }else{
                itemComp.setFormed(true);
                if(!compTime.containsKey(itemGoods.getGoodsId())){
                    System.out.println("error:"+itemGoods);
                }
                itemComp.setFormedTime(compTime.get((int)itemGoods.getGoodsCategoryId()));
                itemComp.setComponents(compMap.get((int)itemGoods.getGoodsCategoryId()));
                itemComp.setGoodsImg(imgUrl);
            }
            System.out.println(itemComp);
            compositionMapper.insertTbNewbeeMallGoodsComposition(itemComp);

        }
        System.out.println(goodsList.size());

    }

    @Test
    public void testMaterial(){
        List<TbNewbeeMallGoodsComposition> allComp = compositionMapper.selectAllComp();
        Set<String> materials = new HashSet<>();
        for(int i=0;i<allComp.size();i++){
            TbNewbeeMallGoodsComposition item = allComp.get(i);
            String allComp4Item = item.getComponents();
            String[] compArr = allComp4Item.split(";");
            for(String s : compArr){
                s=s.substring(0,s.length()-1);
                materials.add(s);
            }
        }
        int material_id = 80000;
        for(String s : materials){
            TbNewbeeMallStockMaterials material = new TbNewbeeMallStockMaterials();
            material.setMaterialsId(material_id);
            material.setMaterialsName(s);
            material.setStockNum(1000);
            meterialMapper.addNewMetrial(material);
            material_id++;
        }
    }

    @Test
    public void testOrder(){
        List<TbNewbeeMallOrderItem> goodsList=orderItemMapper.selectAllGoodsByOrderId(9);
        System.out.println(goodsList);
        System.out.println(goodsList.size());
        System.out.println("id:"+goodsList.get(0).getGoodsId()+"  goodsCount"+goodsList.get(0).getGoodsCount()+"  createTime"+goodsList.get(0).getCreateTime());

    }

    @Test
    public void testOrderQueue(){
        TbNewbeeMallOrderQueue orderQueue = new TbNewbeeMallOrderQueue();
        orderQueue.setOrderId(103);
        //查找订单中所有商品的组装时间
        List<TbNewbeeMallOrderItem> goodsList = orderService.findAllGoodsInOrder(103);
        //计算组装时间
        long formedTime = 0;
        for(TbNewbeeMallOrderItem item : goodsList){
            int count = item.getGoodsCount();
            int id = item.getGoodsId();
            TbNewbeeMallGoodsComposition comp = orderService.findMaterialsByGoodId(id);
            if(comp.isFormed()){
                formedTime = formedTime + (long) count *comp.getFormedTime();
            }
        }
        int humanNumber = 100;
        int workStatus = 2;
        long finalFormedTime = formedTime/humanNumber*workStatus;
        System.out.println(formedTime/humanNumber*workStatus);
        //得到上一个订单的结束时间，然后计算组装时间
        long buyTime = 72*3600*1000;
        orderQueue.setOrderFormedTime(finalFormedTime);
        TbNewbeeMallOrderQueue queLast = orderService.selectLastQueNum();
        Date lastOrderTime = queLast.getFinishedTime();
        Date now = new Date();
        //队列是空的
        if(now.getTime() >= lastOrderTime.getTime()){
            orderQueue.setFinishedTime(new Date(now.getTime()+buyTime+finalFormedTime));
//            orderQueue.setOrderFinishedTime(new Date(now.getTime()));
        }else{
            long waitTime = lastOrderTime.getTime() - now.getTime();
            long finalWaitTime = waitTime >= buyTime? waitTime-buyTime:buyTime;
            orderQueue.setFinishedTime(new Date(finalWaitTime));
        }
        orderQueue.setQueNum(queLast.getQueNum()+1);
        orderService.insertOrderQueue(orderQueue);
//        now.getTime();
//        orderQueue.setFinishedTime();


    }

    @Test
    public void testOrderQueueQuery(){
        long orderId = 1003L;
        System.out.println(orderService.selectOrderQueueById(orderId));
    }
}
