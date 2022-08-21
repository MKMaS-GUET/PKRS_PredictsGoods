<template xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-title="http://www.w3.org/1999/xhtml">
  <div>
    <div class="header_section">
      <div class="container-fluid">
      </div>
      <!--banner section start -->
      <div class="banner_section layout_padding">
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
          <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
          </a>
        </div>
      </div>
      <!--banner section end -->
    </div>
    <!--header section end -->
    <!--services section start -->
    <div class="services_section layout_padding">
      <div class="container">
        <h1 v-if="codeAndData.status!==3" class="services_taital">订单内容</h1>
        <h1 v-if="codeAndData.status==3" class="services_taital">您查询的订单不存在</h1>
        <div class="services_section_2">
          <div v-for="goodsItem in codeAndData.frontData">
            <div class="news_main">
              <div class="news_section_left">
                <div class="image_2"><img :src='goodsItem.goodImgUrl'></div>
              </div>
              <div class="news_section_right">
                <div class="great_text">
                  <p class="dolor_text" style="font-weight: bold">{{goodsItem.goodName}} : {{goodsItem.goodNum}}件</p>
                </div>
                <!--                  <el-form-item>-->
                <!--                    <el-button type="primary" @click="submitForm('numberValidateForm')">提交</el-button>-->
                <!--                  </el-form-item>-->

                <el-table ref="materialTable" :data="goodsMaterials[goodsItem.goodName]" height="200" border
                          style="width: 80%">
                  <el-table-column prop="materialName" label="材料名" width="180"></el-table-column>
                  <el-table-column prop="singleNum" label="单个商品所需材料数量" width="180"></el-table-column>
                  <el-table-column prop="totalNum" label="需要材料总数" width="180"></el-table-column>
<!--                  <el-input v-model="goodsItem.goodName" placeholder="请输入姓名" style="width:50px"></el-input>-->
                </el-table>
                <!--                <div v-for="singleMaterial in goodsItem.materials">-->
                <!--                  <a class="dolor_text">{{singleMaterial.materialName}}：{{singleMaterial.materialNum}}件-&#45;&#45;{{singleMaterial.materialTotal}}} =-->
                <!--                    {{mNum*goodsItem.goodNum}}</a>-->
                <!--                </div>-->
              </div>
            </div>
          </div>
        </div>
        <!--      <div class="read_bt" v-if="codeAndData.allEnough" ><a href="#">评估订购时间</a></div>-->

      </div>
<!--      <p>{{orderId}}</p>-->
      <div class="services_section_2" v-if="!codeAndData.allEnough" align="center">
        <hr>

        <p class="great_text" font-color="red">提示：原料库存不足，请咨询原料采购时间并填写</p><br>
        <form :model="form" @submit.submit="submit" >
          <input type="hidden" name="orderId" :value="orderId">
          <table class="news_main" style="width:80%; margin: 0 ; float: none" border="1">
            <div style="width:100%">
              <div style="width:100%" v-for="item in codeAndData.frontData">
                <thead style="width:100%">
                <!--              <tr style="width:200%;background-color:red">-->
                <th style="width:40%">商品名称</th>
                <th style="width:30%">原材料</th>
                <th style="width:10%">缺少数量</th>
                <th style="width:20%">采购天数/天(未知可不填)</th>
                <!--              </tr>-->
                </thead>
                <tr style="width:100%" v-for="(materialNum,materialName) in item.lacks">
                  <td style="width:40%">{{item.goodName}}</td>
                  <td style="width:30%">{{materialName}}</td>
                  <td style="width:10%">{{materialNum}}</td>
                  <td style="width:20%"><input type="number" placeholder="请输入采购天数" :id="item.goodName+materialName" ></td>
                </tr>
              </div>
              <!--            <div class="read_bt"><a href="#">评估订单完成时间</a></div>-->

            </div>

          </table>
          <input class="read_bts" type="submit" value="评估订单完成时间">
          <p></p>
        </form>
      </div>
    </div>
    <div v-if="codeAndData.allEnough">
      <div class="school_section layout_padding">
        <div class="container">
          <h1 v-if="codeAndData.status==1" class="services_taital">经过评估，订单在{{codeAndData.finishedTime}}左右能够完成</h1>
          <h1 v-if="codeAndData.status==2" class="services_taital">订单已完成</h1>
        </div>
      </div>

      <div class="copyright_section">
        <div class="container">
          <p v-if="codeAndData.status==1" class="copyright_text">【受疫情影响，评估时间仅供参考，与真实情况可能存在误差】</p>
          <p v-if="codeAndData.status==2" class="copyright_text"></p>
        </div>
      </div>
    </div>

    <router-view/>
  </div>
</template>

<script>
  import axios from "axios";
  import qs from "postcss";

  export default {
    name: 'Predict',

    data() {
      return {
        url:'',
        date:'',
        orderId:'',
        numberValidateForm: {
          age: ''
        },
        goodsMaterials: {
          goodsName: '',
          tableData1: [
            {
              materialName: '',
              singleNum: '',
              totalNum: ''
            }
          ]
        }
        ,
        codeAndData: {
          status:'',
          finishedTime:'',
          allEnough: '',
          frontData: [
            {
              goodName: '',
              goodImgUrl: '',
              goodNum: '',
              materials: [{
                materialName: '',
                materialNum: '',
                materialTotal: ''
              }],
              materialEnough: '',
              lacks: [{
                lacksMaterialName: '',
                lacksMaterialNum: ''
              }]
            }
          ]
        }
      }
    },
    created() {
      this.url= window.location.href
      this.getOrderId();
    },
    mounted() {
      // axios.get("http://localhost:7979/predict?orderId="+this.orderId)
      //   .then(response => this.codeAndData = response.data)
      //console.log(this.orderId);
      axios.get('http://localhost:7979/predict?orderId='+this.orderId)
        .then(response => this.codeAndData = response.data)

      axios.get("http://localhost:7979/getTable1?orderId="+this.orderId)
        .then(response => this.goodsMaterials = response.data)


    },
    methods: {

      // submitForm(formName) {
      //   this.$refs[formName].validate((valid) => {
      //     if (valid) {
      //       alert('submit!');
      //     } else {
      //       console.log('error submit!!');
      //       return false;
      //     }
      //   });
      // },
      // resetForm(formName) {
      //   this.$refs[formName].resetFields();
      // }
      submit(){
        // let day = 0;
        var inputIds = [];
        var inputDays = [];
        for(var item in this.codeAndData.frontData){
          console.log(this.codeAndData.frontData[item])
          for( var key in this.codeAndData.frontData[item].lacks){
            var goodKey = this.codeAndData.frontData[item].goodName;
            inputDays.push(parseInt(document.getElementById(goodKey+key).value));
            inputIds.push(key);
          }
        }
        // console.log("days"+inputDays)
        // console.log(inputIds)
        let day1 = 0;
        for(var i=0;i<inputDays.length;i++){
          // console.log("day1?"+day1);
          if(inputDays[i]>day1){
            day1=inputDays[i];
            //console.log("day1:"+day1);
          }
        }


        let id = this.orderId;
        axios.get("http://localhost:7979/predictService?orderId="+id+"&status=1&day="+day1)
          .then(response => this.date = response.data)
        axios.get("http://localhost:7979/predict?orderId="+this.orderId)
          .then(response => this.codeAndData = response.data)
        axios.get("http://localhost:7979/getTable1?orderId="+this.orderId)
          .then(response => this.goodsMaterials = response.data)
        this.$router.go(0);
        window.location.reload();

      },
      getOrderId(){
        let url = window.location.href ;             //获取当前url
        let dz_url = url.split('#')[0];                //获取#/之前的字符串
        let cs = dz_url.split('?')[1];                //获取?之后的参数字符串
        let cs_arr = cs.split('&');                    //参数字符串分割为数组
        let cs1={};
        for(var i=0;i<cs_arr.length;i++){         //遍历数组，拿到json对象
          cs1[cs_arr[i].split('=')[0]] = cs_arr[i].split('=')[1]
        }
        this.orderId = cs1.orderId;
      }
    }
  }
</script>

<style scoped>
  @import '../assets/css/animate.min.css';
  @import '../assets/css/bootstrap.css';
  @import '../assets/css/bootstrap-grid.css';
  @import '../assets/css/style.css';

</style>
