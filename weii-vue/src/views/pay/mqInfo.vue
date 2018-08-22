<template>
  <el-container class="message-page">
    <el-header class="header-option-pannel">
      <el-form :inline="true" :model="getDeviceListParams">
        <el-form-item label="消息队列">
          <el-input v-model="getDeviceListParams.word" placeholder="请输入设备编码" :size="`small`"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="getDeviceListParams.appId" placeholder="请选择应用" size="small">
            <el-option v-for="item in appIdList" :key="item.appId" :label="item.appName" :value="item.appId">
            </el-option>
          </el-select>
          <el-button type="primary" size="mini" plain @click="getDeviceListData">查询</el-button>
          <el-button type="primary" size="mini" plain @click="resetQuery">重置查询条件</el-button>
        </el-form-item>
      </el-form>
    </el-header>
    <el-main>
      <el-table border ref="deviceListTable" v-loading.body="listLoading" :data="deviceListData" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange" :reserve-selection="true">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="machineId" label="设备编号" width="180">
        </el-table-column>
        <el-table-column prop="machineFunction" label="设备类型" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="appName" label="应用" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="deviceType" label="设备型号" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="createdAt" label="导入时间" show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="statusText" label="设备状态" show-overflow-tooltip>
        </el-table-column>
        <el-table-column label="操作" width="400">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="showSingleDeviceQRCode(scope.row.machineId)">二维码</el-button>
            <el-button size="mini" @click="upDateDeviceStatus(scope.row.machineId,scope.row.status)" v-if="scope.row.status">进行测试</el-button>
            <el-button size="mini" type="success" @click="upDateDeviceStatus(scope.row.machineId,scope.row.status)" v-if="!scope.row.status">测试完成</el-button>
            <el-button size="mini" type="danger" @click="handleDeleteDevice(scope.row.machineId)" v-if="deletePermission">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @current-change="handleCurrentTablePageChange" :current-page.sync="getDeviceListParams.page" :page-size="getDeviceListParams.size" layout="total, prev, pager, next,jumper" :total="paginationOption.total" style="text-align:center;">
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
        getDeviceListParams: {
          word: '',
          appId: '',
          page: 1,
          size: 15
        },
        fileList: [],
        multipleSelectionDevices: [],
        deviceListData: [],
        paginationOption: {
          total: 0
        },
        appIdList: [
          {
            appId: '',
            appName: '所有应用'
          }
        ],
        flag: true,
        uploadExcelHeaders: {
          'Access-Control-Allow-Origin': '*'
        },
        deletePermission: false,
        uploadExcelPermission: false
      }
    },
    mounted() {
      this.getUserPermission()
      this.getDeviceListData()
      this.getAppList()
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
      onUploadExcelSuccessCallback() {
        // this.multipleSelectionDevices = []
        this.resetMutileDeviceList()
        this.getDeviceListData()
      },
      // 转移设备成功回调
      onTransferDeviceSuccessCallback() {
        //this.multipleSelectionDevices = []
        this.resetMutileDeviceList()
        this.getDeviceListData()
      },
      // 显示设备转移对话框
      showTransferDeviceModel() {
        if (this.count) {
          this.modelVisible.transferDevice = true
        } else {
          this.$message({
            type: 'error',
            message: '请选择要转移的设备'
          })
        }
      },
      // 显示excel导入对话框
      showImportExcelModel() {
        this.modelVisible.excel = true
      },
      /**
       * @param {Object} payload 回调返回参数
       *
       */
      onModelVisibleChange(payload) {
        const { type, visible } = payload
        switch (type) {
          case 'QRcode':
            this.modelVisible.Qrcode = visible
            break
          case 'excel':
            this.modelVisible.excel = visible
            break
          case 'transferDevice':
            this.modelVisible.transferDevice = visible
            break
          default:
            break
        }
      },
      // 获取appId(厂商列表)
      getAppList() {
        this.api({
          url: '',
          method: 'get'
        }).then(data => {
          this.handleApplistData(data)
        })
      },
      // 处理appId 列表数据
      handleApplistData(data) {
        this.appIdList = this.appIdList.concat(data)
      },
      // 判断用户权限
      getUserPermission() {
        const { permissions } = this.user
        permissions.map(permission => {
          if (permission === 'device:add') {
            this.uploadExcelPermission = true
          }
          if (permission === 'device:delete') {
            this.deletePermission = true
          }
        })
      },
      // 上传Excel前对文件进行判断
      beforeUploadExcel(file) {
        const { name } = file
        if (name == '') {
          this.$message({
            type: 'error',
            message: '上传的文件不能为空!'
          })
          return false
        }
        let lastPath = name.substring(name.lastIndexOf('.') + 1).toLowerCase()
        let reg = /(xls|xlsx)$/i
        if (!reg.test(lastPath)) {
          this.$message({
            type: 'error',
            message: '上传文件格式错误，支持格式：xls,xlsx!'
          })
          return false
        }
      },
      // 切换设备状态  正常使用/未测试
      upDateDeviceStatus(machineId, status) {
        let that = this
        this.api({
          url: '/device/update',
          method: 'post',
          params: {
            machineId: machineId,
            status: status
          }
        }).then(data => {
          this.$message({
            type: 'success',
            message: '更改成功!'
          })
          setTimeout(function() {
            that.getDeviceListData()
          }, 500)
        })
      },
      onUploadExcelError(err) {
        this.$message({
          type: 'error',
          message: '上传失败!'
        })
      },
      // 测量数据
      deviceTestData(machineId, uid, machineFunction) {
        this.$router.push({
          path: '/measure-device-data',
          query: {
            machineId: machineId,
            machineFunction
          }
        })
      },
      // 分页改变
      handleCurrentTablePageChange(page) {
        this.getDeviceListParams.page = page
        this.getDeviceListData()
      },
      // 获取设备列表
      getDeviceListData() {
        // this.listLoading = true
        // this.api({
        //   url: '/device/search',
        //   method: 'get',
        //   params: this.getDeviceListParams
        // }).then(data => {
        //   // this.listLoading = false
        //   // this.paginationOption.total = data.total
        //   // this.handleDeviceListData(data.list)
        //   // this.showSelectRowSelection()
        // })
      },
      // 重置查询
      resetQuery() {
        this.getDeviceListParams.word = ''
        this.getDeviceListParams.appId = ''
        this.getDeviceListData()
      },
      // 设备列表数据处理
      handleDeviceListData(data) {
        for (let i = 0, len = data.length; i < len; i++) {
          console.log(data[i].status)
          if(data[i].status ==1) {
            data[i].statusText = '正常使用'
          }else if(data[i].status ==0){
            data[i].statusText = '测试中'
          }
          data[i].createdAt = formatTimeStr(data[i].createdAt)
        }
        this.deviceListData = data
      },
      // 批量生成二维码
      getPrintQRcode() {
        let len = this.multipleSelectionDevices.length,
          devices = []
        this.modelVisible.Qrcode = true
      },
      // 校验二维码设置回调
      handleSubmitQRcodeSuccess() {

        this.resetMutileDeviceList()
        // this.modelVisible.Qrcode = false
        this.getDeviceListData()
      },
      // 显示单个设备二维码
      showSingleDeviceQRCode(machineId) {
        window.open(`http://device.ubody.net/device/image?machineId=${machineId}`)
      },
      // 删除设备
      handleDeleteDevice(machineId) {
        this.$confirm('此操作将删除该设备, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.api({
              url: '/device/del',
              method: 'get',
              params: {
                machineId
              }
            }).then(data => {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.getDeviceListData()
            })
          })
          .catch(() => {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          })
      },
      handleRemove(file, fileList) {
        //console.log(file, fileList)
      },
      handleExceed(files, fileList) {
        this.$message.warning(`一次只能上传一个excel文件`)
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`)
      },
      // 选择批量生成二维码的设备
      handleSelectionChange(val, row) {
        //this.multipleSelectionDevices = val
        this.setMutileDeviceList({
          selectDevice: val,
          page: this.getDeviceListParams.page
        })
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



