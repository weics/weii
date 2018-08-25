<template>
  <el-container class="message-page">
    <el-header class="header-option-pannel">
      <el-form :inline="true" :model="getDeviceListParams">
        <el-form-item label="消息ID">
          <el-input v-model="messageInfoParam.messageId" placeholder="请输入消息ID" :size="`small`"></el-input>
        </el-form-item>
        <el-form-item label="消息状态">
          <el-select v-model="messageInfoParam.status" placeholder="请选择状态" size="small">
            <el-option v-for="item in messageInfo.messageStatus" :key="item.name" :label="item.desc" :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="消费队列">
          <el-select v-model="messageInfoParam.consumerQueue" placeholder="请选择" size="small">
            <el-option v-for="item in messageInfo.consumerQueue" :key="item.name" :label="item.desc" :value="item.name">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="是否死亡">
          <el-select v-model="messageInfoParam.areadlyDead" placeholder="请选择" size="small">
            <el-option v-for="item in messageInfo.areadlyDead" :key="item.name" :label="item.desc" :value="item.desc">
            </el-option>
          </el-select>
        </el-form-item>


        <el-button type="primary" size="mini" @click="getMessageListDataBySearch()" plain >查询</el-button>
        <el-button type="primary" size="mini" plain >重置查询条件</el-button>
      </el-form>
    </el-header>
    <el-main>
      <el-table border ref="deviceListTable" v-loading.body="listLoading" :data="messageListData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange" :reserve-selection="true">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160">
        </el-table-column>
        <el-table-column prop="editTime" label="修改时间" width="160" >
        </el-table-column>
        <el-table-column prop="messageId" label="消息ID" >
        </el-table-column>
        <el-table-column prop="consumerQueue" label="消费队列" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="status" label="状态" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="messageSendTimes" label="重发次数" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="areadlyDead" label="是否死亡" show-overflow-tooltip>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentTablePageChange" :current-page.sync="messageInfo.page" :page-size="messageInfo.size" layout="total, prev, pager, next,jumper" :total="paginationOption.total" style="text-align:center;">
      </el-pagination>
    </el-main>
  </el-container>
</template>

<script>
  import { mapState, mapMutations } from 'vuex'
  import {formatTimeStr} from '../../utils/index'
  export default {
    name: 'device',
    computed: {
      ...mapState({
        user: state => state.user,
        count:state =>state.deviceList.count,
        tempMutileDeviceList: state => state.deviceList.tempMutileDeviceList
      })
    },
    data() {
      return {
        listLoading: false,
        modelVisible: {
          Qrcode: false,
          excel: false,
          transferDevice: false
        },
        messageListData:[],
        messageInfo:{
          messageStatus : [],
          consumerQueue : [],
          areadlyDead :[{name:'0',desc:"存活"},{name:'1',desc:"死亡"}],
          messageId : 1,
          page : 1,
          size : 12,
        },

        messageInfoParam:{
          status : '',
          consumerQueue : '',
          areadlyDead :'',
          messageId : '',
        },

        messageStatus : [],
        queues : [],
        fileList: [],
        multipleSelectionDevices: [],
        paginationOption: {
          total: 0
        },
        flag: true,
        uploadExcelHeaders: {
          'Access-Control-Allow-Origin': '*'
        },
        deletePermission: false,
        uploadExcelPermission: false
      }
    },
    mounted() {

      this.getMessageListData()

    },
    methods: {
      ...mapMutations({
        setMutileDeviceList: 'SET_MUTILE_DEVICE_LIST',
        resetMutileDeviceList: 'RESET_SET_MUTILE_DEVICE_LIST'
      }),
      toggleRowSelection(row, data) {
        this.$refs.deviceListTable.toggleRowSelection(data, row+1)
      },
      // 上传excel成功回调
      // 显示设备转移对话框

      // 处理appId 列表数据
      handleApplistData(data) {
        this.appIdList = this.appIdList.concat(data)
      },
      // 判断用户权限



      // 分页改变
      handleCurrentTablePageChange(page) {
        this.getDeviceListParams.page = page
        this.getDeviceListData()
      },

      // 获取设备列表
      getMessageListData() {
        this.listLoading = true;
        this.api({
          url: '/message/list',
          method: 'get',
        }).then(data => {
          this.listLoading = false;

          this.handleMessageListData(data.data)
          // this.showSelectRowSelection()
        })
      },


      getMessageListDataBySearch(){
        this.listLoading = true;
        this.api({
          url: '/message/search',
          method: 'post',
          params: this.messageInfoParam,
        }).then(data => {
          this.listLoading = false;

          this.handleMessageListData(data.data)
        })
      },


      // 重置查询
      resetQuery() {
        this.getDeviceListParams.word = ''
        this.getDeviceListParams.appId = ''
        this.getDeviceListData()
      },
      // 设备列表数据处理
      handleMessageListData(data) {

        var messageList = data.pageBean.list;

        for (let i = 0, len = messageList.length; i < len; i++) {
          messageList[i].createTime = formatTimeStr(messageList[i].createTime);
          messageList[i].editTime = formatTimeStr(messageList[i].editTime);
        }

        this.messageListData = messageList;
        this.messageInfo.messageStatus = data.messageStatus;
        this.messageInfo.consumerQueue = data.queues;
      },




      uniqueArray(arr, name) {
        var arrItem = {}
        arr = arr.reduce(function(item, next) {
          arrItem[next[name]]
            ? ''
            : (arrItem[next[name]] = true && item.push(next))
          return item
        }, [])
        return arr
      },
      // 显示选择的表格行
      showSelectRowSelection() {
        let { deviceListData, tempMutileDeviceList } = this
        // const len = deviceListData.length,
        //   l = multipleSelectionDevices.length;
        tempMutileDeviceList = tempMutileDeviceList[this.getDeviceListParams.page]
        if (tempMutileDeviceList) {
          deviceListData.forEach((element, index) => {
            tempMutileDeviceList.forEach(device => {
              if (device.machineId === element.machineId) {
                this.$nextTick(function() {
                  this.toggleRowSelection(index, element)
                })
              }
            })
          })
        }
      }
    }
  }
</script>
<style scoped>
  .header-option-pannel {
    margin-top: 10px;
  }
</style>
<style>
  .device-page .el-checkbox__inner {
    border: 1px solid #000;
  }
</style>



