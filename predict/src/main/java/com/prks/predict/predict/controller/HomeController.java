package com.prks.predict.predict.controller;


import com.prks.predict.predict.dao.TbNewbeeMallGoodsInfoMapper;
import com.prks.predict.predict.dao.TbNewbeeMallOrderItemMapper;
import com.prks.predict.predict.entity.*;
import com.prks.predict.predict.service.OrderPredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class HomeController {

    private class FrontData {
        public String goodName;
        public String goodImgUrl;
        public int goodNum;
        public boolean materialEnough;
        public Map<String, Integer> lacks;
        //        public List<TbNewbeeMallGoodsComposition> materials;
//        public Map<String,Integer> materials;
        public List<String> materialName;
        public List<Integer> materialNum;
        public List<Integer> materialTotal;

        @Override
        public String toString() {
            return "FrontData{" +
                    "goodName='" + goodName + '\'' +
                    ", goodImgUrl='" + goodImgUrl + '\'' +
                    ", goodNum=" + goodNum +
                    ", materialEnough=" + materialEnough +
                    ", lacks=" + lacks +
                    ", materialName=" + materialName +
                    ", materialNum=" + materialNum +
                    ", materialTotal=" + materialTotal +
                    '}';
        }
    }

    private class CodeAndData {
        boolean allEnough;
        int status;
        String finishedTime;
        List<FrontData> frontData;

        @Override
        public String toString() {
            return "CodeAndData{" +
                    "allEnough=" + allEnough +
                    ", status=" + status +
                    ", finishedTime='" + finishedTime + '\'' +
                    ", frontData=" + frontData +
                    '}';
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getFinishedTime() {
            return finishedTime;
        }

        public void setFinishedTime(String finishedTime) {
            this.finishedTime = finishedTime;
        }

        public boolean isAllEnough() {
            return allEnough;
        }

        public void setAllEnough(boolean allEnough) {
            this.allEnough = allEnough;
        }

        public List<FrontData> getFrontData() {
            return frontData;
        }

        public void setFrontData(List<FrontData> frontData) {
            this.frontData = frontData;
        }
    }

    private class TableData1 {
        public String materialName;
        public int singleNum;
        public int totalNum;
    }

    @Autowired
    private TbNewbeeMallOrderItemMapper orderItemMapper;
    @Autowired
    private TbNewbeeMallGoodsInfoMapper goodsInfoMapper;
    @Autowired
    private OrderPredictService orderService;

    @CrossOrigin
    @RequestMapping(path = "/getTable1",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String, List<TableData1>> getTable1(@RequestParam long orderId) {
        //long orderId = 103L;
        Map<String, List<TableData1>> goodsMaterials = new HashMap<>();
        List<TbNewbeeMallOrderItem> goods = orderService.findAllGoodsInOrder(orderId);
        List<TableData1> res;
        for (TbNewbeeMallOrderItem item : goods) {
            res = new ArrayList<>();
            int count = item.getGoodsCount();
            TbNewbeeMallGoodsComposition comp = orderService.findMaterialsByGoodId((int) item.getGoodsId());
            String[] compSet = comp.getComponents().split(";");
            for (int i = 0; i < compSet.length; i++) {
                String[] compDetails = compSet[i].split("##");
                TableData1 tableData1 = new TableData1();
                int singleCnt = Integer.valueOf(compDetails[1]);
                tableData1.materialName = compDetails[0];
                tableData1.singleNum = singleCnt;
                tableData1.totalNum = singleCnt * count;
                res.add(tableData1);
            }
            goodsMaterials.put(item.getGoodsName(), res);
        }

        return goodsMaterials;
    }

    @CrossOrigin
    @RequestMapping(path = {"/predict","predict1"},method = {RequestMethod.GET,RequestMethod.POST})
    public CodeAndData homePage(@RequestParam long orderId) {
        //long orderId = 103L;
        //?????????????????????0????????????????????????1????????????????????????????????????????????????????????????//2????????????????????????
        //int orderStatus = orderService.selectOrderQueueById(orderId).getOrderStatus();
        System.out.println("inside methods");
        List<FrontData> res = new ArrayList<>();
        List<TbNewbeeMallOrderItem> goods = orderService.findAllGoodsInOrder(orderId);
        boolean allEnough = true;
        boolean orderNotExists = false;
        if(goods.size()==0){
            orderNotExists=true;
        }
        //?????????????????????????????????,????????????????????????
        for (TbNewbeeMallOrderItem item : goods) {
            FrontData frontData = new FrontData();
            frontData.goodName = item.getGoodsName();
            frontData.goodImgUrl = item.getGoodsCoverImg();
            frontData.goodNum = item.getGoodsCount();
            //???????????????????????????????????????????????????????????????
            TbNewbeeMallGoodsComposition comp = orderService.findMaterialsByGoodId((int) item.getGoodsId());
            String[] compSet = comp.getComponents().split(";");
            List<String> materialName = new ArrayList<>();
            List<Integer> materialNum = new ArrayList<>();
            List<Integer> materialTotal = new ArrayList<>();
            frontData.materialName = materialName;
            frontData.materialNum = materialNum;
            frontData.materialTotal = materialTotal;
            for (int i = 0; i < compSet.length; i++) {
                String[] compDetails = compSet[i].split("##");
                frontData.materialName.add(compDetails[0]);
                int singleNeed = Integer.valueOf(compDetails[1]);
                frontData.materialNum.add(singleNeed);
                frontData.materialTotal.add(item.getGoodsCount() * singleNeed);
            }
            //??????????????????????????????????????????????????????????????????----????????????????????????????????????????????????
            TbNewbeeMallOrderQueue queue = orderService.selectOrderQueueById(orderId);
            if(queue==null||queue.getOrderStatus()==1||queue.getOrderStatus()==2){
                frontData.lacks = null;
                frontData.materialEnough = true;
            }else{
                //status==0?????????????????????
                frontData.lacks = orderService.lackedMaterials((int) item.getGoodsId(), item.getGoodsCount());
                frontData.materialEnough = frontData.lacks == null;
                orderService.updateOrderStatus(orderId,1);
            }

            if (frontData.lacks==null || frontData.lacks.size()==0) {
                allEnough = true;
                //orderService.updateOrderStatus(orderId,2);
            }else{
                allEnough = false;
            }
            res.add(frontData);

        }
        CodeAndData codeAndData = new CodeAndData();
        TbNewbeeMallOrderQueue queue = orderService.selectOrderQueueById(orderId);
        if (queue == null) {
            //???????????????????????????
            if(orderNotExists){
                codeAndData.setStatus(3);
            }else{
                codeAndData.setStatus(2);
            }
            codeAndData.setFinishedTime("");
        }else{
            String time = convertTime(queue.getFinishedTime());
            Date now = new Date();
            //???????????????????????????
            if(queue.getFinishedTime().getTime()<=now.getTime()){
                codeAndData.setStatus(2);
            }else{
                codeAndData.setStatus(queue.getOrderStatus());
            }

            codeAndData.setFinishedTime(time);
        }

        codeAndData.allEnough = allEnough;
        codeAndData.frontData = res;
//        ????????????????????????
        System.out.println("finished"+codeAndData);
        return codeAndData;
    }

    private String convertTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateAdded = new Date(date.getTime()+72*3600*1000);
        String time = simpleDateFormat.format(dateAdded);
        String[] res = time.split("-");
        return res[0]+"???"+res[1]+"???"+res[2]+"???";
    }

    //?????????????????????????????????
    @CrossOrigin
    @GetMapping(path = "/predictService")
    @ResponseBody
    public String calculateFinishedTime(@RequestParam long orderId,
                                        @RequestParam(required = false,defaultValue = "0") int status,
                                        @RequestParam(required = false,defaultValue = "3")int day) {
        //long orderId = 103L;
        System.out.println("day:"+day);
        System.out.println("in predict service");
        long buyTime = day * 24 * 3600 * 1000L;//???????????????????????????
        int humanNumber = 100;  //??????????????????100??????????????????????????????100??????
        int workStatus = 2; //??????or?????????-->????????????12h
        //int status = 1;
        System.out.println("????????????");
        return calculateFinishedTimeDefalt(orderId,buyTime,humanNumber,workStatus,status);
    }

    private String calculateFinishedTimeDefalt(long orderId,long buyTime,int humanNumber,int workStatus,int status){
        TbNewbeeMallOrderQueue orderQueue = new TbNewbeeMallOrderQueue();
        orderQueue.setOrderId(orderId);
        //??????????????????????????????
        //1.?????????????????????????????????id
        List<TbNewbeeMallOrderItem> goodsItems = orderService.findAllGoodsInOrder(orderId);
        Map<Long, Integer> lacksGoodsNum = new HashMap<>();
        boolean goodsStockEnough = true;
        for (TbNewbeeMallOrderItem item : goodsItems) {
            long goodId = item.getGoodsId();
            int stockNum = orderService.findGoodsInfoById((int) goodId).getStockNum();
            if (stockNum < item.getGoodsCount()) {
                goodsStockEnough = false;
                lacksGoodsNum.put(goodId, item.getGoodsCount() - stockNum);
            }
        }
        if(goodsStockEnough) {
            //??????????????????????????????????????????????????????????????????????????????????????????
            orderService.updateOrderStatus(orderId,2);
            return "ok";
        }
        //2.????????????????????????????????????????????????
        if (!goodsStockEnough) {
            long formedTime = 0;
            Set<Long> lacksGoodId = lacksGoodsNum.keySet();
            for (long id : lacksGoodId) {
                int count = lacksGoodsNum.get(id);
                TbNewbeeMallGoodsComposition comp = orderService.findMaterialsByGoodId((int) id);
                if (comp.isFormed()) {
                    formedTime = formedTime + (long) count * comp.getFormedTime();
                }
                //????????????????????????
                String[] materials = comp.getComponents().split(";");
            }

            long finalFormedTime = formedTime*1000 / humanNumber * workStatus;
            //???????????????????????????????????????????????????????????????
            orderQueue.setOrderFormedTime(finalFormedTime/1000);
            TbNewbeeMallOrderQueue queLast = orderService.selectLastQueNum();
            Date lastOrderTime = queLast.getFinishedTime();
            Date now = new Date();
            long waitTime = lastOrderTime.getTime() - now.getTime();
            long finalWaitTime = Math.max(waitTime, buyTime);
            //??????????????????
            if (now.getTime() >= lastOrderTime.getTime()) {
                orderQueue.setFinishedTime(new Date(now.getTime() + buyTime + finalFormedTime));
            } else {
                orderQueue.setFinishedTime(new Date(finalWaitTime+now.getTime()+finalFormedTime));
            }
            //????????????????????????????????????,status???1??????????????????
            if(status==0) {
                orderQueue.setOrderStatus(status);
                orderQueue.setQueNum(queLast.getQueNum() + 1);
                orderService.insertOrderQueue(orderQueue);
                return "notok";
            }else{
                //???update
                Date d = orderService.selectOrderQueueById(orderId).getFinishedTime();
                orderService.updateOrderFinishedTime( orderId,new Date(finalWaitTime+now.getTime()+finalFormedTime));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String time = simpleDateFormat.format(d);
                return time;
            }
        }
        return "ok";
    }
}
