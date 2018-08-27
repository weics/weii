<template>
  <el-container class="payrecord-page">
    <el-header class="header-option-pannel" height="100px">
      <el-form :inline="true" :model="payRecordParam">
        <el-form-item label="商户编号">
          <el-input v-model="payRecordParam.merchantNo" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>
        <el-form-item label="商户名称">
          <el-input v-model="payRecordParam.merchantName" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>

        <el-form-item label="商户订单号">
          <el-input v-model="payRecordParam.merchantOrderNo" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>

        <el-form-item label="状态">
          <el-select v-model="payRecordParam.status" placeholder="请选择" size="small">
            <el-option v-for="item of payRecordInfo.statusEnums" :key="item.key" :label="item.desc" :value="item.key">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="下单开始日期">
          <el-input v-model="payRecordParam.orderDateBegin" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>

        <el-form-item label="下单结束日期">
          <el-input v-model="payRecordParam.orderDateEnd" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>


        <el-form-item label="支付方式">
          <el-select v-model="payRecordParam.payWayName" placeholder="请选择" size="small">
            <el-option v-for="item in payRecordInfo.payWayNameEnums" :key="item.name" :label="item.desc" :value="item.desc">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="支付类型">
          <el-select v-model="payRecordParam.payTypeName" placeholder="请选择" size="small">
            <el-option v-for="item in payRecordInfo.payTypeNameEnums" :key="item.name" :label="item.desc" :value="item.desc">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="资金流入类型">
          <el-select v-model="payRecordParam.fundIntoType" placeholder="请选择" size="small">
            <el-option v-for="item in payRecordInfo.fundIntoTypeEnums" :key="item.name" :label="item.desc" :value="item.desc">
            </el-option>
          </el-select>
        </el-form-item>


        <el-button type="primary" size="mini" @click="getPayRecordSearchData()" plain >查询</el-button>
        <el-button type="primary" size="mini" plain >重置查询条件</el-button>
      </el-form>
    </el-header>
    <el-main>
      <el-table border ref="deviceListTable" v-loading.body="listLoading" :data="payRecordListData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange" :reserve-selection="true">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="merchantName" label="商家名称" width="160" >
        </el-table-column>
        <el-table-column prop="merchantOrderNo" label="商户订单号" >
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="payWayName" label="支付方式" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="payTypeName" label="支付类型" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="trxNo" label="支付流水号" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="bankOrderNo" label="银行订单号" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="orderAmount" label="订单金额" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="status" label="状态" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="paySuccessTime" label="成功支付时间" show-overflow-tooltip>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentTablePageChange" :current-page.sync="payRecordInfo.page" :page-size="payRecordInfo.size" layout="total, prev, pager, next,jumper" :total="paginationOption.total" style="text-align:center;">
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
        payRecordListData:[],
        payRecordInfo:{
          statusEnums : {},
          payTypeNameEnums : {},
          fundIntoTypeEnums :{},
          payWayNameEnums : {},
          payInfo:{},
          page : 1,
          size : 12,
        },

        payRecordParam:{
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

      this.getPayRecordListData()

    },
    methods: {

      // 获取设备列表
      getPayRecordListData() {
        this.listLoading = true;
        this.api({
          url: '/trade/listPaymentRecord',
          method: 'get',
        }).then(data => {
          this.listLoading = false;
          this.handlePayRecordListData(data.data)
        })
      },


      getPayRecordSearchData(){
        this.listLoading = true;
        this.api({
          url: '/trade/search',
          method: 'post',
          params: this.messageInfoParam,
        }).then(data => {
          this.listLoading = false;

          this.handlePayRecordListData(data.data)
        })
      },

      // 设备列表数据处理
      handlePayRecordListData(data) {

        var payRecordList = data.pageBean.list;

        this.payRecordInfo.statusEnums = data.statusEnums;
        this.payRecordInfo.fundIntoTypeEnums = data.fundIntoTypeEnums;
        this.payRecordInfo.payTypeNameEnums = data.payTypeNameEnums;
        this.payRecordInfo.payWayNameEnums = data.payWayNameEnums;
        var temp = this.payRecordInfo.payWayNameEnums;
        var status = [];
        for(let i = 0, len = temp.length; i < len; i++){
          status[temp[i].name] = temp[i].desc;
        }
        for (let i = 0, len = payRecordList.length; i < len; i++) {
          payRecordList[i].createTime = formatTimeStr(payRecordList[i].createTime);
          payRecordList[i].paySuccessTime = formatTimeStr(payRecordList[i].paySuccessTime);
          payRecordList[i].payWayName = status[payRecordList[i].payWayName];
        }
        this.payRecordListData = payRecordList;

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




