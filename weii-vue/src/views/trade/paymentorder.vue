<template>
  <el-container class="payorder-page">
    <el-header class="header-option-pannel" height="100px">
      <el-form :inline="true" :model="payOrderParam">
        <el-form-item label="商户编号">
          <el-input v-model="payOrderParam.merchantNo" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>
        <el-form-item label="商户名称">
          <el-input v-model="payOrderParam.merchantName" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>

        <el-form-item label="商户订单号">
          <el-input v-model="payOrderParam.merchantOrderNo" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>

        <el-form-item label="状态">
          <el-select v-model="payOrderParam.status" placeholder="请选择" size="small">
            <el-option v-for="item of payOrderInfo.statusEnums" :key="item.key" :label="item.desc" :value="item.key">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="下单开始日期">
          <el-input v-model="payOrderParam.orderDateBegin" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>

        <el-form-item label="下单结束日期">
          <el-input v-model="payOrderParam.orderDateEnd" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>


        <el-form-item label="支付方式">
          <el-select v-model="payOrderParam.payWayName" placeholder="请选择" size="small">
            <el-option v-for="item in payOrderInfo.payWayNameEnums" :key="item.name" :label="item.desc" :value="item.desc">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="支付类型">
          <el-select v-model="payOrderParam.payTypeName" placeholder="请选择" size="small">
            <el-option v-for="item in payOrderInfo.payTypeNameEnums" :key="item.name" :label="item.desc" :value="item.desc">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="资金流入类型">
          <el-select v-model="payOrderParam.fundIntoType" placeholder="请选择" size="small">
            <el-option v-for="item in payOrderInfo.fundIntoTypeEnums" :key="item.name" :label="item.desc" :value="item.desc">
            </el-option>
          </el-select>
        </el-form-item>


        <el-button type="primary" size="mini" @click="getPayOrderSearchData()" plain >查询</el-button>
        <el-button type="primary" size="mini" plain >重置查询条件</el-button>
      </el-form>
    </el-header>
    <el-main>
      <el-table border ref="deviceListTable" v-loading.body="listLoading" :data="payOrderListData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange" :reserve-selection="true">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="merchantNo" label="商户编号" width="160">
        </el-table-column>
        <el-table-column prop="merchantName" label="商家名称" width="160" >
        </el-table-column>
        <el-table-column prop="merchantOrderNo" label="商户订单号" >
        </el-table-column>
        <el-table-column prop="orderAmount" label="订单金额" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="status" label="状态" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="orderDate" label="下单日期" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="orderTime" label="下单时间" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="payWayName" label="支付方式" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="payTypeName" label="支付类型" show-overflow-tooltip>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentTablePageChange" :current-page.sync="payOrderInfo.page" :page-size="payOrderInfo.size" layout="total, prev, pager, next,jumper" :total="paginationOption.total" style="text-align:center;">
      </el-pagination>
    </el-main>
  </el-container>
</template>

<script>
  import { mapState, mapMutations } from 'vuex'
  import {formatTimeStr} from '../../utils/index'
  export default {
    name: 'payorder',
    data() {
      return {
        listLoading: false,
        payOrderListData:[],
        payOrderInfo:{
          statusEnums : {},
          payTypeNameEnums : {},
          fundIntoTypeEnums :{},
          payWayNameEnums : {},
          payInfo:{},
          page : 1,
          size : 12,
        },

        payOrderParam:{
          merchantNo:'',
          merchantName:'',
          merchantOrderNo:'',
          status:'',
          orderDateBegin:'',
          orderDateEnd:'',
          payWayName : '',
          payTypeName : '',
          fundIntoType :'',
        },
        queues : [],
        paginationOption: {
          total: 0
        },
        flag: true,
        uploadExcelHeaders: {
          'Access-Control-Allow-Origin': '*'
        },
      }
    },
    mounted() {

      this.getPayOrderListData()

    },
    methods: {

      // 获取设备列表
      getPayOrderListData() {
        this.listLoading = true;
        this.api({
          url: '/trade/listPaymentOrder',
          method: 'get',
        }).then(data => {
          this.listLoading = false;
          this.handlePayOrderListData(data.data)
        })
      },


      getPayOrderSearchData(){
        this.listLoading = true;
        this.api({
          url: '/trade/search',
          method: 'post',
          params: this.messageInfoParam,
        }).then(data => {
          this.listLoading = false;

          this.handlePayOrderListData(data.data)
        })
      },


      // 重置查询
      resetQuery() {
        this.getDeviceListParams.word = ''
        this.getDeviceListParams.appId = ''
        this.getDeviceListData()
      },
      // 设备列表数据处理
      handlePayOrderListData(data) {

        var payOrderList = data.pageBean.list;

        this.payOrderInfo.statusEnums = data.statusEnums;
        this.payOrderInfo.fundIntoTypeEnums = data.fundIntoTypeEnums;
        this.payOrderInfo.payTypeNameEnums = data.payTypeNameEnums;
        this.payOrderInfo.payWayNameEnums = data.payWayNameEnums;
        var temp = this.payOrderInfo.payWayNameEnums;
        var status = [];
        for(let i = 0, len = temp.length; i < len; i++){
          status[temp[i].name] = temp[i].desc;
        }
        for (let i = 0, len = payOrderList.length; i < len; i++) {
          payOrderList[i].orderTime = formatTimeStr(payOrderList[i].orderTime);
          payOrderList[i].orderDate = formatTimeStr(payOrderList[i].orderDate);
          payOrderList[i].editTime = formatTimeStr(payOrderList[i].editTime);
          payOrderList[i].createTime = formatTimeStr(payOrderList[i].createTime);
          payOrderList[i].editTime = formatTimeStr(payOrderList[i].editTime);
          payOrderList[i].payWayName = status[payOrderList[i].payWayName];
        }
        this.payOrderListData = payOrderList;

      },

    }
  }
</script>
<style scoped>
  .header-option-pannel {
    margin-top: 10px;
  }

  .header-option-pannel {
    margin-top: 10px;
  }
</style>
<style>
  .device-page .el-checkbox__inner {
    border: 1px solid #000;
  }
</style>



