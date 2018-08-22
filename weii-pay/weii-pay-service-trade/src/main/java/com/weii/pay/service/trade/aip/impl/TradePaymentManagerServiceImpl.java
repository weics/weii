package com.weii.pay.service.trade.aip.impl;

import com.alibaba.fastjson.JSONObject;
import com.weii.pay.common.core.enums.NotifyDestinationNameEnum;
import com.weii.pay.common.core.enums.PayTypeEnum;
import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.enums.TrxTypeEnum;
import com.weii.pay.common.core.utils.DateUtils;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.account.api.AccountTransactionService;
import com.weii.pay.service.accounting.entity.AccountingVoucher;
import com.weii.pay.service.message.api.TransactionMessageService;
import com.weii.pay.service.message.entity.TransactionMessage;
import com.weii.pay.service.notify.entity.NotifyRecord;
import com.weii.pay.service.notify.enums.NotifyStatusEnum;
import com.weii.pay.service.notify.enums.NotifyTypeEnum;
import com.weii.pay.service.trade.api.TradePaymentManagerService;
import com.weii.pay.service.trade.dao.RpTradePaymentOrderDao;
import com.weii.pay.service.trade.dao.RpTradePaymentRecordDao;
import com.weii.pay.service.trade.entity.TradePaymentOrder;
import com.weii.pay.service.trade.entity.TradePaymentRecord;
import com.weii.pay.service.trade.entity.weixinpay.WeiXinPrePay;
import com.weii.pay.service.trade.enums.OrderFromEnum;
import com.weii.pay.service.trade.enums.TradeStatusEnum;
import com.weii.pay.service.trade.enums.alipay.AliPayTradeStateEnum;
import com.weii.pay.service.trade.enums.weixinpay.WeiXinTradeTypeEnum;
import com.weii.pay.service.trade.enums.weixinpay.WeixinTradeStateEnum;
import com.weii.pay.service.trade.exceptions.TradeBizException;
import com.weii.pay.service.trade.utils.MerchantApiUtil;
import com.weii.pay.service.trade.utils.WeiXinPayUtils;
import com.weii.pay.service.trade.utils.WeixinConfigUtil;
import com.weii.pay.service.trade.utils.alipay.config.AlipayConfigUtil;
import com.weii.pay.service.trade.utils.alipay.util.AlipayNotify;
import com.weii.pay.service.trade.utils.alipay.util.AlipaySubmit;
import com.weii.pay.service.trade.vo.OrderPayResultVo;
import com.weii.pay.service.trade.vo.PayGateWayPageShowVo;
import com.weii.pay.service.trade.vo.ScanPayResultVo;
import com.weii.pay.service.user.api.PayWayService;
import com.weii.pay.service.user.api.UserInfoService;
import com.weii.pay.service.user.api.UserPayConfigService;
import com.weii.pay.service.user.api.UserPayInfoService;
import com.weii.pay.service.user.entity.PayWay;
import com.weii.pay.service.user.entity.UserInfo;
import com.weii.pay.service.user.entity.UserPayConfig;
import com.weii.pay.service.user.entity.UserPayInfo;
import com.weii.pay.service.user.enums.FundInfoTypeEnum;
import com.weii.pay.service.user.exceptions.UserBizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mumu
 * @version V1.0
 * @date Created in 19:37 2018/5/11
 * @Description:
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class TradePaymentManagerServiceImpl implements TradePaymentManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(TradePaymentManagerServiceImpl.class);

    @Autowired
    private RpTradePaymentOrderDao rpTradePaymentOrderDao;

    @Autowired
    private RpTradePaymentRecordDao rpTradePaymentRecordDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserPayInfoService userPayInfoService;

    @Autowired
    private UserPayConfigService userPayConfigService;

    @Autowired
    private PayWayService payWayService;

    @Autowired
    private AccountTransactionService accountTransactionService;

    @Autowired
    private TransactionMessageService transactionMessageService;

    /**
     * 初始化直连扫码支付数据,直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单存在,抛异常,提示订单已存在
     * 1.2:如果订单不存在,创建支付订单
     * 2:创建支付记录
     * 3:根据相应渠道方法
     * 4:调转到相应支付渠道扫码界面
     *
     * @param payKey  商户支付KEY
     * @param productName 产品名称
     * @param orderNo     商户订单号
     * @param orderDate   下单日期
     * @param orderTime   下单时间
     * @param orderPrice  订单金额(元)
     * @param payWayCode      支付方式编码
     * @param orderIp     下单IP
     * @param orderPeriod 订单有效期(分钟)
     * @param returnUrl   支付结果页面通知地址
     * @param notifyUrl   支付结果后台通知地址
     * @param remark      支付备注
     * @param field1      扩展字段1
     * @param field2      扩展字段2
     * @param field3      扩展字段3
     * @param field4      扩展字段4
     * @param field5      扩展字段5
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScanPayResultVo initDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {
        LOG.info("接收到订单数据{}",orderNo);
        UserPayConfig userPayConfig = userPayConfigService.getByPayKey(payKey);
        if (userPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        //根据支付产品及支付方式获取费率
        PayWay payWay = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){
            payWay = payWayService.getByPayWayTypeCode(userPayConfig.getProductCode(), payWayCode, PayTypeEnum.SCANPAY.name());
        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            payWay = payWayService.getByPayWayTypeCode(userPayConfig.getProductCode(), payWayCode, PayTypeEnum.DIRECT_PAY.name());
        }else if (PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWayCode)){
            payWay = payWayService.getByPayWayTypeCode(userPayConfig.getProductCode(), payWayCode, PayTypeEnum.TEST_PAY_HTTP_CLIENT.name());
        }

        if(payWay == null){
            LOG.error("用户支付配置有误");
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = userPayConfig.getUserNo();//商户编号

        UserInfo userInfo = userInfoService.getDataByMerchentNo(merchantNo);
        if (userInfo == null){
            LOG.error("用户不存在");
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        // 先判断支付订单是否已存在
        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (tradePaymentOrder == null){
            // 订单不存在则创建
            tradePaymentOrder = sealRpTradePaymentOrder( merchantNo,  userInfo.getUserName() , productName,  orderNo,  orderDate,  orderTime,  orderPrice, payWayCode, PayWayEnum.getEnum(payWayCode).getDesc() ,  userPayConfig.getFundIntoType() ,  orderIp,  orderPeriod,  returnUrl,  notifyUrl,  remark,  field1,  field2,  field3,  field4,  field5);
            rpTradePaymentOrderDao.insert(tradePaymentOrder);
        }else{
            // 订单存在
            if (tradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0 ){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"错误的订单");
            }

            if (TradeStatusEnum.SUCCESS.name().equals(tradePaymentOrder.getStatus())){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
            }
        }

        // 通过支付订单及商户费率生成支付记录
        return getScanPayResultVo(tradePaymentOrder, payWay);

    }

    /**
     * 完成扫码支付(支付宝即时到账支付，微信扫码支付)
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeScanPay(String payWayCode ,Map<String, String> notifyMap){

        LOG.info("接收到支付结果{}",notifyMap);

        String bankOrderNo = notifyMap.get("out_trade_no");

        LOG.info("------[接收到要处理的订单{}]--------[开始处理时间{}]------",bankOrderNo,DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss SSS"));

        TradePaymentRecord tradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (tradePaymentRecord == null){
            LOG.error("非法订单，订单{}不存在", bankOrderNo);
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(tradePaymentRecord.getStatus())){
            LOG.info("订单{}为成功状态,不做业务处理",bankOrderNo);
            return;
        }

        if (!TradeStatusEnum.WAITING_PAYMENT.name().equals(tradePaymentRecord.getStatus())){
            LOG.info("订单{}状态为非等待支付状态,不做业务处理",bankOrderNo);
            return;
        }

        boolean orderIsSuccess = false;// 银行返回订单是否为支付成功状态
        String bankTrxNo = "";//银行流水号
        Date timeEnd = null;//订单完成时间
        String bankReturnMsg = "";//银行返回信息

        if(PayWayEnum.WEIXIN.name().equals(payWayCode)){
            if (WeixinTradeStateEnum.SUCCESS.name().equals(notifyMap.get("result_code"))){//业务结果 成功
                String timeEndStr = notifyMap.get("time_end");
                if (!StringUtil.isEmpty(timeEndStr)){
                    timeEnd =  DateUtils.getDateFromString(timeEndStr,"yyyyMMddHHmmss");//订单支付完成时间
                }
                orderIsSuccess = true;
                bankTrxNo = notifyMap.get("transaction_id");
                bankReturnMsg = notifyMap.toString();
            }

        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            String tradeStatus = notifyMap.get("trade_status");
            if(AliPayTradeStateEnum.TRADE_FINISHED.name().equals(tradeStatus)){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            } else if (AliPayTradeStateEnum.TRADE_SUCCESS.name().equals(tradeStatus)){
                String gmtPaymentStr = notifyMap.get("gmt_payment");//付款时间
                if(!StringUtil.isEmpty(gmtPaymentStr)){
                    timeEnd = DateUtils.getDateFromString(gmtPaymentStr,"yyyy-MM-dd HH:mm:ss");
                }
                orderIsSuccess = true;
                bankTrxNo = notifyMap.get("trade_no");
                bankReturnMsg = notifyMap.toString();
            }

        }else if (PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWayCode)){
            // 模拟网关支付
            if (WeixinTradeStateEnum.SUCCESS.name().equals(notifyMap.get("result_code"))){//业务结果 成功
                String timeEndStr = notifyMap.get("time_end");
                if (!StringUtil.isEmpty(timeEndStr)){
                    timeEnd =  DateUtils.getDateFromString(timeEndStr,"yyyyMMddHHmmss");//订单支付完成时间
                }
                orderIsSuccess = true;
                bankTrxNo = notifyMap.get("transaction_id");
                bankReturnMsg = notifyMap.toString();
            }
        }else{
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR,"错误的支付方式");
        }

        if (orderIsSuccess){//银行返回订单支付成功

            LOG.info("==>开始处理支付成功的订单结果");
            TransactionMessage transactionMessage = sealRpTransactionMessage(tradePaymentRecord); // 封装会计原始凭证数据
            transactionMessageService.saveMessageWaitingConfirm(transactionMessage);
            LOG.info("==>保存消息数据");

            try {
                completeSuccessOrder(tradePaymentRecord, bankTrxNo, timeEnd, bankReturnMsg);
            } catch (Exception e) {
                throw new TradeBizException(TradeBizException.TRADE_SYSTEM_ERROR,"交易系统异常,处理支付结果失败");
            }

            transactionMessageService.confirmAndSendMessage(transactionMessage.getMessageId());
            LOG.info("==>修改消息状态");

        }else{
            LOG.info("==>开始处理支付失败的订单结果");
            completeFailOrder(tradePaymentRecord);
        }

        LOG.info("------[结束要处理的订单{}]--------[结束处理时间{}]------",bankOrderNo,DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss SSS"));
    }

    @Override
    public OrderPayResultVo completeScanPayResult(String payWayCode, Map<String, String> resultMap) throws TradeBizException {
        return null;
    }

    /**
     * 支付成功方法（更新支付记录状态和更新支付订单状态，给资金账户加款）
     * @param tradePaymentRecord
     */
    private void completeSuccessOrder(TradePaymentRecord tradePaymentRecord, String bankTrxNo , Date timeEnd , String bankReturnMsg){

        LOG.info("===>completeSuccessOrder");

        // 修改支付记录状态
        tradePaymentRecord.setPaySuccessTime(timeEnd);
        tradePaymentRecord.setBankTrxNo(bankTrxNo);
        tradePaymentRecord.setBankReturnMsg(bankReturnMsg);
        tradePaymentRecord.setStatus(TradeStatusEnum.SUCCESS.name());
        rpTradePaymentRecordDao.update(tradePaymentRecord);

        // 修改支付订单状态
        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(tradePaymentRecord.getMerchantNo(), tradePaymentRecord.getMerchantOrderNo());
        tradePaymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        rpTradePaymentOrderDao.update(tradePaymentOrder);

        // 给商户资金帐户加款（平台收款）
        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(tradePaymentRecord.getFundIntoType())){
            LOG.info("==>修改订单账户金额");
            accountTransactionService.creditToAccount(tradePaymentRecord.getMerchantNo(), tradePaymentRecord.getOrderAmount().subtract(tradePaymentRecord.getPlatIncome()), tradePaymentRecord.getBankOrderNo(), tradePaymentRecord.getBankTrxNo(), tradePaymentRecord.getTrxType(), tradePaymentRecord.getRemark());
        }

    }

    /**
     * 构建商户通知URL.
     * @param tradePaymentRecord
     * @param tradePaymentOrder
     * @param sourceUrl
     * @param tradeStatusEnum
     * @return
     */
    private String getMerchantNotifyUrl(TradePaymentRecord tradePaymentRecord, TradePaymentOrder tradePaymentOrder, String sourceUrl , TradeStatusEnum tradeStatusEnum){

        UserPayConfig userPayConfig = userPayConfigService.getByUserNo(tradePaymentRecord.getMerchantNo());
        if (userPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        Map<String , Object> paramMap = new HashMap<>();

        String payKey = userPayConfig.getPayKey();// 企业支付KEY
        paramMap.put("payKey",payKey);
        String productName = tradePaymentRecord.getProductName(); // 商品名称
        paramMap.put("productName",productName);
        String orderNo = tradePaymentRecord.getMerchantOrderNo(); // 订单编号
        paramMap.put("orderNo",orderNo);
        BigDecimal orderPrice = tradePaymentRecord.getOrderAmount(); // 订单金额 , 单位:元
        paramMap.put("orderPrice",orderPrice);
        String payWayCode = tradePaymentRecord.getPayWayCode(); // 支付方式编码 支付宝: ALIPAY  微信:WEIXIN
        paramMap.put("payWayCode",payWayCode);
        paramMap.put("tradeStatus",tradeStatusEnum);//交易状态
        String orderDateStr = DateUtils.formatDate(tradePaymentOrder.getOrderDate(),"yyyyMMdd"); // 订单日期
        paramMap.put("orderDate",orderDateStr);
        String orderTimeStr = DateUtils.formatDate(tradePaymentOrder.getOrderTime(), "yyyyMMddHHmmss"); // 订单时间
        paramMap.put("orderTime",orderTimeStr);
        String remark = tradePaymentRecord.getRemark(); // 支付备注
        paramMap.put("remark",remark);

        String field1 = tradePaymentOrder.getField1(); // 扩展字段1
        paramMap.put("field1",field1);
        String field2 = tradePaymentOrder.getField2(); // 扩展字段2
        paramMap.put("field2",field2);
        String field3 = tradePaymentOrder.getField3(); // 扩展字段3
        paramMap.put("field3",field3);
        String field4 = tradePaymentOrder.getField4(); // 扩展字段4
        paramMap.put("field4",field4);
        String field5 = tradePaymentOrder.getField5(); // 扩展字段5
        paramMap.put("field5",field5);

        String paramStr = MerchantApiUtil.getParamStr(paramMap);
        String sign = MerchantApiUtil.getSign(paramMap, userPayConfig.getPaySecret());
        String notifyUrl = sourceUrl + "?" + paramStr + "&sign=" + sign;

        return notifyUrl;
    }


    /**
     * 支付失败方法
     * @param tradePaymentRecord
     */
    private void completeFailOrder(TradePaymentRecord tradePaymentRecord){

        // 修改支付记录状态
        tradePaymentRecord.setStatus(TradeStatusEnum.FAILED.name());
        rpTradePaymentRecordDao.update(tradePaymentRecord);

        // 修改支付订单状态
        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(tradePaymentRecord.getMerchantNo(), tradePaymentRecord.getMerchantOrderNo());
        tradePaymentOrder.setStatus(TradeStatusEnum.FAILED.name());
        rpTradePaymentOrderDao.update(tradePaymentOrder);

        // 异步通知商户
        // TODO
    }

    /**
     * 初始化非直连扫码支付数据,非直连扫码支付初始化方法规则
     * 1:根据(商户编号 + 商户订单号)确定订单是否存在
     * 1.1:如果订单存在且为未支付,抛异常提示订单已存在
     * 1.2:如果订单不存在,创建支付订单
     * 2:获取商户支付配置,跳转到支付网关,选择支付方式
     *
     * @param payKey  商户支付KEY
     * @param productName 产品名称
     * @param orderNo     商户订单号
     * @param orderDate   下单日期
     * @param orderTime   下单时间
     * @param orderPrice  订单金额(元)
     * @param orderIp     下单IP
     * @param orderPeriod 订单有效期(分钟)
     * @param returnUrl   支付结果页面通知地址
     * @param notifyUrl   支付结果后台通知地址
     * @param remark      支付备注
     * @param field1      扩展字段1
     * @param field2      扩展字段2
     * @param field3      扩展字段3
     * @param field4      扩展字段4
     * @param field5      扩展字段5
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayGateWayPageShowVo initNonDirectScanPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {

        UserPayConfig userPayConfig = userPayConfigService.getByPayKey(payKey);
        if (userPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = userPayConfig.getUserNo();//商户编号
        UserInfo userInfo = userInfoService.getDataByMerchentNo(merchantNo);
        if (userInfo == null){
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        List<PayWay> payWayList = payWayService.listByProductCode(userPayConfig.getProductCode());
        if (payWayList == null || payWayList.size() <= 0){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"支付产品配置有误");
        }

        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (tradePaymentOrder == null){
            tradePaymentOrder = sealRpTradePaymentOrder( merchantNo,  userInfo.getUserName() , productName,  orderNo,  orderDate,  orderTime,  orderPrice, null, null ,  userPayConfig.getFundIntoType() ,  orderIp,  orderPeriod,  returnUrl,  notifyUrl,  remark,  field1,  field2,  field3,  field4,  field5);
            rpTradePaymentOrderDao.insert(tradePaymentOrder);
        }else{
            if (tradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0 ){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"错误的订单");
            }
        }

        PayGateWayPageShowVo payGateWayPageShowVo = new PayGateWayPageShowVo();
        payGateWayPageShowVo.setProductName(tradePaymentOrder.getProductName());//产品名称
        payGateWayPageShowVo.setMerchantName(tradePaymentOrder.getMerchantName());//商户名称
        payGateWayPageShowVo.setOrderAmount(tradePaymentOrder.getOrderAmount());//订单金额
        payGateWayPageShowVo.setMerchantOrderNo(tradePaymentOrder.getMerchantOrderNo());//商户订单号
        payGateWayPageShowVo.setPayKey(payKey);//商户支付key

        Map<String , PayWayEnum> payWayEnumMap = new HashMap<String , PayWayEnum>();
        for (PayWay payWay :payWayList){
            payWayEnumMap.put(payWay.getPayWayCode(), PayWayEnum.getEnum(payWay.getPayWayCode()));
        }

        payGateWayPageShowVo.setPayWayEnumMap(payWayEnumMap);

        return payGateWayPageShowVo;

    }

    /**
     * 非直连扫码支付,选择支付方式后,去支付
     * @param payKey
     * @param orderNo
     * @param payWayCode
     * @return
     */
    @Override
    public ScanPayResultVo toNonDirectScanPay(String payKey , String orderNo, String payWayCode) {

        UserPayConfig userPayConfig = userPayConfigService.getByPayKey(payKey);
        if (userPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        //根据支付产品及支付方式获取费率
        PayWay payWay = null;
        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){
            payWay = payWayService.getByPayWayTypeCode(userPayConfig.getProductCode(), payWayCode, PayTypeEnum.SCANPAY.name());
        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            payWay = payWayService.getByPayWayTypeCode(userPayConfig.getProductCode(), payWayCode, PayTypeEnum.DIRECT_PAY.name());
        }

        if(payWay == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = userPayConfig.getUserNo();//商户编号
        UserInfo userInfo = userInfoService.getDataByMerchentNo(merchantNo);
        if (userInfo == null){
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        //根据商户订单号获取订单信息
        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (tradePaymentOrder == null){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(tradePaymentOrder.getStatus())){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
        }

        return getScanPayResultVo(tradePaymentOrder, payWay);

    }


    /**
     * 通过支付订单及商户费率生成支付记录
     * @param tradePaymentOrder   支付订单
     * @param payWay   商户支付配置
     * @return
     */
    private ScanPayResultVo getScanPayResultVo(TradePaymentOrder tradePaymentOrder, PayWay payWay){

        ScanPayResultVo scanPayResultVo = new ScanPayResultVo();

        String payWayCode = payWay.getPayWayCode();//支付方式

        if (PayWayEnum.WEIXIN.name().equals(payWay.getPayWayCode())){
            tradePaymentOrder.setPayTypeCode(PayTypeEnum.SCANPAY.name());//支付类型
            tradePaymentOrder.setPayTypeName(PayTypeEnum.SCANPAY.getDesc());//支付方式
        }else if(PayWayEnum.ALIPAY.name().equals(payWay.getPayWayCode())){
            tradePaymentOrder.setPayTypeCode(PayTypeEnum.DIRECT_PAY.name());//支付类型
            tradePaymentOrder.setPayTypeName(PayTypeEnum.DIRECT_PAY.getDesc());//支付方式
        }

        // 更新支付订单（因为支付方式可能会变换）
        tradePaymentOrder.setPayWayCode(payWay.getPayWayCode());
        tradePaymentOrder.setPayWayName(payWay.getPayWayName());
        rpTradePaymentOrderDao.update(tradePaymentOrder);


        // 创建支付记录
        TradePaymentRecord tradePaymentRecord = sealRpTradePaymentRecord( tradePaymentOrder.getMerchantNo(),  tradePaymentOrder.getMerchantName() , tradePaymentOrder.getProductName(),  tradePaymentOrder.getMerchantOrderNo(),  tradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(),  payWay.getPayWayName() ,  tradePaymentOrder.getFundIntoType()  , BigDecimal.valueOf(payWay.getPayRate()) ,  tradePaymentOrder.getOrderIp(),  tradePaymentOrder.getReturnUrl(),  tradePaymentOrder.getNotifyUrl(),  tradePaymentOrder.getRemark(),  tradePaymentOrder.getField1(),  tradePaymentOrder.getField2(),  tradePaymentOrder.getField3(),  tradePaymentOrder.getField4(),  tradePaymentOrder.getField5());
        rpTradePaymentRecordDao.insert(tradePaymentRecord);

        if (PayWayEnum.WEIXIN.name().equals(payWayCode)){//微信支付
            String appid = "";
            String mch_id = "";
            String partnerKey = "";
            if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(tradePaymentOrder.getFundIntoType())){//商户收款
                //根据资金流向获取配置信息
                UserPayInfo userPayInfo = userPayInfoService.getByUserNo(tradePaymentOrder.getMerchantNo(),payWayCode);
                appid = userPayInfo.getAppId();
                mch_id = userPayInfo.getMerchantId();
                partnerKey = userPayInfo.getPartnerKey();
            }else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(tradePaymentOrder.getFundIntoType())){//平台收款
                appid = WeixinConfigUtil.readConfig("appId");
                mch_id = WeixinConfigUtil.readConfig("mch_id");
                partnerKey = WeixinConfigUtil.readConfig("partnerKey");
            }

            WeiXinPrePay weiXinPrePay = sealWeixinPerPay(appid, mch_id, tradePaymentOrder.getProductName(), tradePaymentOrder.getRemark(), tradePaymentRecord.getBankOrderNo(), tradePaymentOrder.getOrderAmount(), tradePaymentOrder.getOrderTime(), tradePaymentOrder.getOrderPeriod(), WeiXinTradeTypeEnum.NATIVE,
                    tradePaymentRecord.getBankOrderNo(), "", tradePaymentOrder.getOrderIp());
            String prePayXml = WeiXinPayUtils.getPrePayXml(weiXinPrePay, WeixinConfigUtil.readConfig("partnerKey"));
            //调用微信支付的功能,获取微信支付code_url
            Map<String, Object> prePayRequest = WeiXinPayUtils.httpXmlRequest(WeixinConfigUtil.readConfig("prepay_url"), "POST", prePayXml);
            if (WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("return_code")) && WeixinTradeStateEnum.SUCCESS.name().equals(prePayRequest.get("result_code"))) {
                String weiXinPrePaySign = WeiXinPayUtils.geWeiXintPrePaySign(appid, mch_id, weiXinPrePay.getDeviceInfo(), WeiXinTradeTypeEnum.NATIVE.name(), prePayRequest, partnerKey);
                String codeUrl = String.valueOf(prePayRequest.get("code_url"));
                LOG.info("预支付生成成功,{}",codeUrl);
                if (prePayRequest.get("sign").equals(weiXinPrePaySign)) {
                    tradePaymentRecord.setBankReturnMsg(prePayRequest.toString());
                    rpTradePaymentRecordDao.update(tradePaymentRecord);
                    scanPayResultVo.setCodeUrl(codeUrl);//设置微信跳转地址
                    scanPayResultVo.setPayWayCode(PayWayEnum.WEIXIN.name());
                }else{
                    throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"微信返回结果签名异常");
                }
            }else{
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"请求微信异常");
            }
        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){//支付宝支付

            //把请求参数打包成数组
            Map<String, String> sParaTemp = new HashMap<String, String>();
            sParaTemp.put("service", AlipayConfigUtil.service);
            sParaTemp.put("partner", AlipayConfigUtil.partner);
            sParaTemp.put("seller_id", AlipayConfigUtil.seller_id);
            sParaTemp.put("_input_charset", AlipayConfigUtil.input_charset);
            sParaTemp.put("payment_type", AlipayConfigUtil.payment_type);
            sParaTemp.put("notify_url", AlipayConfigUtil.notify_url);
            sParaTemp.put("return_url", AlipayConfigUtil.return_url);
            sParaTemp.put("anti_phishing_key", AlipayConfigUtil.anti_phishing_key);
            sParaTemp.put("exter_invoke_ip", AlipayConfigUtil.exter_invoke_ip);
            sParaTemp.put("out_trade_no", tradePaymentRecord.getBankOrderNo());
            sParaTemp.put("subject", tradePaymentOrder.getProductName());
            sParaTemp.put("total_fee", String.valueOf(tradePaymentOrder.getOrderAmount().setScale(2,BigDecimal.ROUND_HALF_UP)));//小数点后两位
            sParaTemp.put("body", "");
            //获取请求页面数据
            String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get", "确认");

            tradePaymentRecord.setBankReturnMsg(sHtmlText);
            rpTradePaymentRecordDao.update(tradePaymentRecord);
            scanPayResultVo.setCodeUrl(sHtmlText);//设置微信跳转地址
            scanPayResultVo.setPayWayCode(PayWayEnum.ALIPAY.name());
            scanPayResultVo.setProductName(tradePaymentOrder.getProductName());
            scanPayResultVo.setOrderAmount(tradePaymentOrder.getOrderAmount());

        }else if (PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWayCode)){
            //测试模拟httpclient支付
            tradePaymentRecord.setBankReturnMsg("模拟支付"); // 模拟请求支付通道返回结果
            rpTradePaymentRecordDao.update(tradePaymentRecord);

        }
        else{
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR,"错误的支付方式");
        }

        return scanPayResultVo;
    }



    /**
     * 支付成功后,又是会出现页面通知早与后台通知
     * 现页面通知,暂时不做数据处理功能,只生成页面通知URL
     * @param payWayCode
     * @param resultMap
     * @return
     */
    @Override
    public OrderPayResultVo completeScanPayByResult(String payWayCode, Map<String, String> resultMap) {

        OrderPayResultVo orderPayResultVo = new OrderPayResultVo();

        String bankOrderNo = resultMap.get("out_trade_no");
        //根据银行订单号获取支付信息
        TradePaymentRecord tradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (tradePaymentRecord == null){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
        }

        orderPayResultVo.setOrderPrice(tradePaymentRecord.getOrderAmount());//订单金额
        orderPayResultVo.setProductName(tradePaymentRecord.getProductName());//产品名称

        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(tradePaymentRecord.getMerchantNo(), tradePaymentRecord.getMerchantOrderNo());

        String trade_status = resultMap.get("trade_status");
        //验证签名
        boolean verify_result = AlipayNotify.verify(resultMap);
        if(verify_result){
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                String resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
                orderPayResultVo.setReturnUrl(resultUrl);
                orderPayResultVo.setStatus(TradeStatusEnum.SUCCESS.name());
            }else{
                String resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.FAILED);
                orderPayResultVo.setReturnUrl(resultUrl);
                orderPayResultVo.setStatus(TradeStatusEnum.FAILED.name());
            }
        }else{
            throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR,"支付宝签名异常");
        }
        return orderPayResultVo;
    }


    @Override
    public OrderPayResultVo completeTestPay(String payKey, String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice, String payWayCode, String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5) {

        UserPayConfig userPayConfig = userPayConfigService.getByPayKey(payKey);
        if (userPayConfig == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        //根据支付产品及支付方式获取费率
        PayWay payWay = payWayService.getByPayWayTypeCode(userPayConfig.getProductCode(), payWayCode, PayTypeEnum.TEST_PAY.name());

        if(payWay == null){
            throw new UserBizException(UserBizException.USER_PAY_CONFIG_ERRPR,"用户支付配置有误");
        }

        String merchantNo = userPayConfig.getUserNo();//商户编号

        UserInfo userInfo = userInfoService.getDataByMerchentNo(merchantNo);
        if (userInfo == null){
            throw new UserBizException(UserBizException.USER_IS_NULL,"用户不存在");
        }

        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(merchantNo, orderNo);
        if (tradePaymentOrder == null){
            tradePaymentOrder = sealRpTradePaymentOrder( merchantNo,  userInfo.getUserName() , productName,  orderNo,  orderDate,  orderTime,  orderPrice, payWayCode, PayWayEnum.getEnum(payWayCode).getDesc() ,  userPayConfig.getFundIntoType() ,  orderIp,  orderPeriod,  returnUrl,  notifyUrl,  remark,  field1,  field2,  field3,  field4,  field5);
            rpTradePaymentOrderDao.insert(tradePaymentOrder);
        }else{
            if (tradePaymentOrder.getOrderAmount().compareTo(orderPrice) != 0 ){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"错误的订单");
            }

            if (TradeStatusEnum.SUCCESS.name().equals(tradePaymentOrder.getStatus())){
                throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单已支付成功,无需重复支付");
            }
        }

        tradePaymentOrder.setPayTypeCode(PayTypeEnum.TEST_PAY.name());//支付类型
        tradePaymentOrder.setPayTypeName(PayTypeEnum.TEST_PAY.getDesc());//支付方式
        tradePaymentOrder.setPayWayCode(payWay.getPayWayCode());
        tradePaymentOrder.setPayWayName(payWay.getPayWayName());
        tradePaymentOrder.setStatus(TradeStatusEnum.SUCCESS.name());
        rpTradePaymentOrderDao.update(tradePaymentOrder);

        TradePaymentRecord tradePaymentRecord = sealRpTradePaymentRecord( tradePaymentOrder.getMerchantNo(),  tradePaymentOrder.getMerchantName() , tradePaymentOrder.getProductName(),  tradePaymentOrder.getMerchantOrderNo(),  tradePaymentOrder.getOrderAmount(), payWay.getPayWayCode(),  payWay.getPayWayName() ,  tradePaymentOrder.getFundIntoType()  , BigDecimal.valueOf(payWay.getPayRate()) ,  tradePaymentOrder.getOrderIp(),  tradePaymentOrder.getReturnUrl(),  tradePaymentOrder.getNotifyUrl(),  tradePaymentOrder.getRemark(),  tradePaymentOrder.getField1(),  tradePaymentOrder.getField2(),  tradePaymentOrder.getField3(),  tradePaymentOrder.getField4(),  tradePaymentOrder.getField5());
        tradePaymentRecord.setBankReturnMsg("模拟银行支付");
        tradePaymentRecord.setStatus(TradeStatusEnum.SUCCESS.name());
        tradePaymentRecord.setPaySuccessTime(new Date());
        rpTradePaymentRecordDao.insert(tradePaymentRecord);

        // 给商户资金帐户加款（平台收款）
        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(tradePaymentRecord.getFundIntoType())){
            LOG.info("==>修改订单账户金额");
            accountTransactionService.creditToAccount(tradePaymentRecord.getMerchantNo(), tradePaymentRecord.getOrderAmount().subtract(tradePaymentRecord.getPlatIncome()), tradePaymentRecord.getBankOrderNo(), tradePaymentRecord.getBankTrxNo(), tradePaymentRecord.getTrxType(), tradePaymentRecord.getRemark());
        }

        OrderPayResultVo orderPayResultVo = new OrderPayResultVo();
        orderPayResultVo.setOrderPrice(tradePaymentRecord.getOrderAmount());//订单金额
        orderPayResultVo.setProductName(tradePaymentRecord.getProductName());//产品名称
        String resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
        orderPayResultVo.setReturnUrl(resultUrl);
        orderPayResultVo.setStatus(TradeStatusEnum.SUCCESS.name());

        return orderPayResultVo;
    }

    /**
     * 调用
     * @param tradePaymentRecord
     * @return
     */
    private TransactionMessage sealRpTransactionMessage(TradePaymentRecord tradePaymentRecord){

        //封装会计需要的实体
        AccountingVoucher accountingVoucher = new AccountingVoucher();

        accountingVoucher.setEntryType(1);//交易类型
        accountingVoucher.setPayerChangeAmount(0D);//付款方变动金额
        accountingVoucher.setVoucherNo(tradePaymentRecord.getTrxNo());//平台交易流水号
        accountingVoucher.setPayerAccountNo("");//付款方账户
        accountingVoucher.setFromSystem(1);//系统来源
        accountingVoucher.setPayerAccountType(1);//账户类型
        accountingVoucher.setPayerFee(0d);//付款方手续费
        accountingVoucher.setBankChangeAmount(tradePaymentRecord.getOrderAmount().doubleValue());//银行订单金额
        accountingVoucher.setReceiverChangeAmount(tradePaymentRecord.getOrderAmount().doubleValue());
        accountingVoucher.setReceiverAccountNo(tradePaymentRecord.getMerchantNo());
        accountingVoucher.setBankAccount("");
        accountingVoucher.setBankChannelCode(tradePaymentRecord.getPayWayCode());
        accountingVoucher.setProfit(tradePaymentRecord.getPlatProfit().doubleValue());
        accountingVoucher.setIncome(tradePaymentRecord.getPlatIncome().doubleValue());
        accountingVoucher.setCost(tradePaymentRecord.getPlatCost().doubleValue());
        accountingVoucher.setBankOrderNo(tradePaymentRecord.getBankOrderNo());
        accountingVoucher.setPayAmount(0d);
        accountingVoucher.setReceiverAccountType(1);
        accountingVoucher.setReceiverFee(tradePaymentRecord.getReceiverFee() == null ? 0d : tradePaymentRecord.getReceiverFee().doubleValue());
        accountingVoucher.setRemark("模拟支付信息");

        String messageId = StringUtil.get32UUID();
        accountingVoucher.setMessageId(messageId);

        String messageBody = JSONObject.toJSONString(accountingVoucher);
        TransactionMessage transactionMessage = new TransactionMessage( messageId, messageBody, NotifyDestinationNameEnum.ACCOUNTING_NOTIFY.name());
        transactionMessage.setField1(tradePaymentRecord.getBankOrderNo()); // 备用字段存储订单状态回查用的业务订单号

        return transactionMessage;
    }

    /**
     * 支付订单实体封装
     * @param merchantNo    商户编号
     * @param merchantName  商户名称
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderDate 下单日期
     * @param orderTime 下单时间
     * @param orderPrice    订单金额
     * @param payWay    支付方式
     * @param payWayName    支付方式名称
     * @param fundIntoType  资金流入类型
     * @param orderIp   下单IP
     * @param orderPeriod   订单有效期
     * @param returnUrl 页面通知地址
     * @param notifyUrl 后台通知地址
     * @param remark    支付备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     * @return
     */
    private TradePaymentOrder sealRpTradePaymentOrder(String merchantNo, String merchantName , String productName, String orderNo, Date orderDate, Date orderTime, BigDecimal orderPrice,
                                                      String payWay, String payWayName , String fundIntoType , String orderIp, Integer orderPeriod, String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5){

        LOG.info("sealRpTradePaymentOrder封装数据开始");
        TradePaymentOrder tradePaymentOrder = new TradePaymentOrder();
        tradePaymentOrder.setProductName(productName);//商品名称
        if (StringUtil.isEmpty(orderNo)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"订单号错误");
        }

        tradePaymentOrder.setMerchantOrderNo(orderNo);//订单号

        if (orderPrice == null || orderPrice.doubleValue() <= 0){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"订单金额错误");
        }

        tradePaymentOrder.setOrderAmount(orderPrice);//订单金额

        if (StringUtil.isEmpty(merchantName)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"商户名称错误");
        }
        tradePaymentOrder.setMerchantName(merchantName);//商户名称

        if (StringUtil.isEmpty(merchantNo)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"商户编号错误");
        }
        tradePaymentOrder.setMerchantNo(merchantNo);//商户编号

        if (orderDate == null){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"下单日期错误");
        }
        tradePaymentOrder.setOrderDate(orderDate);//下单日期

        if (orderTime == null){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"下单时间错误");
        }
        tradePaymentOrder.setOrderTime(orderTime);//下单时间
        tradePaymentOrder.setOrderIp(orderIp);//下单IP
        tradePaymentOrder.setOrderRefererUrl("");//下单前页面

        if (StringUtil.isEmpty(returnUrl)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"页面通知错误");
        }
        tradePaymentOrder.setReturnUrl(returnUrl);//页面通知地址

        if (StringUtil.isEmpty(notifyUrl)){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"后台通知错误");
        }
        tradePaymentOrder.setNotifyUrl(notifyUrl);//后台通知地址

        if (orderPeriod == null || orderPeriod <= 0){
            throw new TradeBizException(TradeBizException.TRADE_PARAM_ERROR,"订单有效期错误");
        }
        tradePaymentOrder.setOrderPeriod(orderPeriod);//订单有效期

        Date expireTime = DateUtils.addMinute(orderTime,orderPeriod);//订单过期时间
        tradePaymentOrder.setExpireTime(expireTime);//订单过期时间
        tradePaymentOrder.setPayWayCode(payWay);//支付通道编码
        tradePaymentOrder.setPayWayName(payWayName);//支付通道名称
        tradePaymentOrder.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());//订单状态 等待支付

        if (PayWayEnum.WEIXIN.name().equals(payWay)){
            tradePaymentOrder.setPayTypeCode(PayTypeEnum.SCANPAY.name());//支付类型
            tradePaymentOrder.setPayTypeName(PayTypeEnum.SCANPAY.getDesc());//支付方式
        }else if(PayWayEnum.ALIPAY.name().equals(payWay)) {
            tradePaymentOrder.setPayTypeCode(PayTypeEnum.DIRECT_PAY.name());//支付类型
            tradePaymentOrder.setPayTypeName(PayTypeEnum.DIRECT_PAY.getDesc());//支付方式
        }else if(PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWay)){//模拟httpclient支付
            tradePaymentOrder.setPayTypeCode(PayTypeEnum.TEST_PAY_HTTP_CLIENT.name());//支付类型
            tradePaymentOrder.setPayTypeName(PayTypeEnum.TEST_PAY_HTTP_CLIENT.getDesc());//支付方式
        }else if(PayWayEnum.TEST_PAY.name().equals(payWay)){//模拟网关支付
            tradePaymentOrder.setPayTypeCode(PayTypeEnum.TEST_PAY.name());//支付类型
            tradePaymentOrder.setPayTypeName(PayTypeEnum.TEST_PAY.getDesc());//支付方式
        }
        tradePaymentOrder.setFundIntoType(fundIntoType);//资金流入方向

        tradePaymentOrder.setRemark(remark);//支付备注
        tradePaymentOrder.setField1(field1);//扩展字段1
        tradePaymentOrder.setField2(field2);//扩展字段2
        tradePaymentOrder.setField3(field3);//扩展字段3
        tradePaymentOrder.setField4(field4);//扩展字段4
        tradePaymentOrder.setField5(field5);//扩展字段5
        LOG.info("sealRpTradePaymentOrder封装数据结束");
        return tradePaymentOrder;
    }


    /**
     * 封装支付流水记录实体
     * @param merchantNo    商户编号
     * @param merchantName  商户名称
     * @param productName   产品名称
     * @param orderNo   商户订单号
     * @param orderPrice    订单金额
     * @param payWay    支付方式编码
     * @param payWayName    支付方式名称
     * @param fundIntoType  资金流入方向
     * @param feeRate   支付费率
     * @param orderIp   订单IP
     * @param returnUrl 页面通知地址
     * @param notifyUrl 后台通知地址
     * @param remark    备注
     * @param field1    扩展字段1
     * @param field2    扩展字段2
     * @param field3    扩展字段3
     * @param field4    扩展字段4
     * @param field5    扩展字段5
     * @return
     */
    private TradePaymentRecord sealRpTradePaymentRecord(String merchantNo, String merchantName , String productName, String orderNo, BigDecimal orderPrice , String payWay , String payWayName , String fundIntoType , BigDecimal feeRate ,
                                                        String orderIp , String returnUrl, String notifyUrl, String remark, String field1, String field2, String field3, String field4, String field5){

        LOG.info("sealRpTradePaymentRecord封装数据开始");
        TradePaymentRecord tradePaymentRecord = new TradePaymentRecord();
        tradePaymentRecord.setProductName(productName);//产品名称
        tradePaymentRecord.setMerchantOrderNo(orderNo);//产品编号

        String trxNo = rpTradePaymentRecordDao.buildTrxNo();
        tradePaymentRecord.setTrxNo(trxNo);//支付流水号

        String bankOrderNo = rpTradePaymentRecordDao.buildBankOrderNo();

        if(PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWay)){//模拟httpclient支付
            bankOrderNo = orderNo;//如果是通过httpclient模拟的支付，那么就让银行订单号与商户订单号相同，方便做处理
        }
        tradePaymentRecord.setBankOrderNo(bankOrderNo);//银行订单号
        tradePaymentRecord.setMerchantName(merchantName);
        tradePaymentRecord.setMerchantNo(merchantNo);//商户编号
        tradePaymentRecord.setOrderIp(orderIp);//下单IP
        tradePaymentRecord.setOrderRefererUrl("");//下单前页面
        tradePaymentRecord.setReturnUrl(returnUrl);//页面通知地址
        tradePaymentRecord.setNotifyUrl(notifyUrl);//后台通知地址
        tradePaymentRecord.setPayWayCode(payWay);//支付通道编码
        tradePaymentRecord.setPayWayName(payWayName);//支付通道名称
        tradePaymentRecord.setTrxType(TrxTypeEnum.EXPENSE.name());//交易类型
        tradePaymentRecord.setOrderFrom(OrderFromEnum.USER_EXPENSE.name());//订单来源
        tradePaymentRecord.setOrderAmount(orderPrice);//订单金额
        tradePaymentRecord.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());//订单状态 等待支付

        if (PayWayEnum.WEIXIN.name().equals(payWay)){
            tradePaymentRecord.setPayTypeCode(PayTypeEnum.SCANPAY.name());//支付类型
            tradePaymentRecord.setPayTypeName(PayTypeEnum.SCANPAY.getDesc());//支付方式
        }else if(PayWayEnum.ALIPAY.name().equals(payWay)){
            tradePaymentRecord.setPayTypeCode(PayTypeEnum.DIRECT_PAY.name());//支付类型
            tradePaymentRecord.setPayTypeName(PayTypeEnum.DIRECT_PAY.getDesc());//支付方式
        }else if(PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWay)){//模拟httpclient支付
            tradePaymentRecord.setPayTypeCode(PayTypeEnum.TEST_PAY_HTTP_CLIENT.name());//支付类型
            tradePaymentRecord.setPayTypeName(PayTypeEnum.TEST_PAY_HTTP_CLIENT.getDesc());//支付方式
        }else if(PayWayEnum.TEST_PAY.name().equals(payWay)){//模拟支付
            tradePaymentRecord.setPayTypeCode(PayTypeEnum.TEST_PAY.name());//支付类型
            tradePaymentRecord.setPayTypeName(PayTypeEnum.TEST_PAY.getDesc());//支付方式
        }
        tradePaymentRecord.setFundIntoType(fundIntoType);//资金流入方向

        if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)){//平台收款 需要修改费率 成本 利润 收入 以及修改商户账户信息
            BigDecimal orderAmount = tradePaymentRecord.getOrderAmount();//订单金额
            BigDecimal platIncome = orderAmount.multiply(feeRate).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);  //平台收入 = 订单金额 * 支付费率(设置的费率除以100为真实费率)
            BigDecimal platCost = orderAmount.multiply(BigDecimal.valueOf(Double.valueOf(WeixinConfigUtil.readConfig("pay_rate")))).divide(BigDecimal.valueOf(100),2,BigDecimal.ROUND_HALF_UP);//平台成本 = 订单金额 * 微信费率(设置的费率除以100为真实费率)
            BigDecimal platProfit = platIncome.subtract(platCost);//平台利润 = 平台收入 - 平台成本

            tradePaymentRecord.setFeeRate(feeRate);//费率
            tradePaymentRecord.setPlatCost(platCost);//平台成本
            tradePaymentRecord.setPlatIncome(platIncome);//平台收入
            tradePaymentRecord.setPlatProfit(platProfit);//平台利润

        }

        tradePaymentRecord.setRemark(remark);//支付备注
        tradePaymentRecord.setField1(field1);//扩展字段1
        tradePaymentRecord.setField2(field2);//扩展字段2
        tradePaymentRecord.setField3(field3);//扩展字段3
        tradePaymentRecord.setField4(field4);//扩展字段4
        tradePaymentRecord.setField5(field5);//扩展字段5

        LOG.info("sealRpTradePaymentRecord封装数据结束");
        return tradePaymentRecord;
    }


    /**
     * 封装预支付实体
     * @param appId 公众号ID
     * @param mchId    商户号
     * @param productName   商品描述
     * @param remark  支付备注
     * @param bankOrderNo   银行订单号
     * @param orderPrice    订单价格
     * @param orderTime 订单下单时间
     * @param orderPeriod   订单有效期
     * @param weiXinTradeTypeEnum   微信支付方式
     * @param productId 商品ID
     * @param openId    用户标识
     * @param orderIp   下单IP
     * @return
     */
    private WeiXinPrePay sealWeixinPerPay(String appId ,String mchId ,String productName ,String remark ,String bankOrderNo ,BigDecimal orderPrice , Date orderTime , Integer orderPeriod ,WeiXinTradeTypeEnum weiXinTradeTypeEnum ,
                                          String productId ,String openId ,String orderIp){
        WeiXinPrePay weiXinPrePay = new WeiXinPrePay();

        weiXinPrePay.setAppid(appId);
        weiXinPrePay.setMchId(mchId);
        weiXinPrePay.setBody(productName);//商品描述
        weiXinPrePay.setAttach(remark);//支付备注
        weiXinPrePay.setOutTradeNo(bankOrderNo);//银行订单号

        Integer totalFee = orderPrice.multiply(BigDecimal.valueOf(100d)).intValue();
        weiXinPrePay.setTotalFee(totalFee);//订单金额
        weiXinPrePay.setTimeStart(DateUtils.formatDate(orderTime, "yyyyMMddHHmmss"));//订单开始时间
        weiXinPrePay.setTimeExpire(DateUtils.formatDate(DateUtils.addMinute(orderTime, orderPeriod), "yyyyMMddHHmmss"));//订单到期时间
        weiXinPrePay.setNotifyUrl(WeixinConfigUtil.readConfig("notify_url"));//通知地址
        weiXinPrePay.setTradeType(weiXinTradeTypeEnum);//交易类型
        weiXinPrePay.setProductId(productId);//商品ID
        weiXinPrePay.setOpenid(openId);//用户标识
        weiXinPrePay.setSpbillCreateIp(orderIp);//下单IP

        return weiXinPrePay;
    }

    /**
     * 回调通知验证签名
     * @param payWayCode
     * @param notifyMap
     * @return
     */
    @Override
    public void verifyNotify(String payWayCode ,Map<String, String> notifyMap) {

        String bankOrderNo = notifyMap.get("out_trade_no");

        LOG.info("订单号{}",bankOrderNo);
        //根据银行订单号获取支付信息
        TradePaymentRecord tradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (tradePaymentRecord == null){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
        }

        if (TradeStatusEnum.SUCCESS.name().equals(tradePaymentRecord.getStatus())){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,"订单为成功状态");
        }
        String merchantNo = tradePaymentRecord.getMerchantNo();//商户编号

        //根据支付订单获取配置信息
        String fundIntoType = tradePaymentRecord.getFundIntoType();//获取资金流入类型
        String partnerKey = "";

        if (FundInfoTypeEnum.MERCHANT_RECEIVES.name().equals(fundIntoType)){//商户收款
            //根据资金流向获取配置信息
            UserPayInfo userPayInfo = userPayInfoService.getByUserNo(merchantNo,PayWayEnum.WEIXIN.name());
            partnerKey = userPayInfo.getPartnerKey();

        }else if (FundInfoTypeEnum.PLAT_RECEIVES.name().equals(fundIntoType)){//平台收款
            partnerKey = WeixinConfigUtil.readConfig("partnerKey");
        }


        if(PayWayEnum.WEIXIN.name().equals(payWayCode)){
            String sign = notifyMap.remove("sign");
            if (WeiXinPayUtils.notifySign(notifyMap, sign, partnerKey)){//根据配置信息验证签名

            }else{
                throw new TradeBizException(TradeBizException.TRADE_WEIXIN_ERROR,"微信签名失败");
            }

        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            if(AlipayNotify.verify(notifyMap)){//验证成功

            }else{//验证失败
                throw new TradeBizException(TradeBizException.TRADE_ALIPAY_ERROR,"支付宝签名异常");
            }
        }else{
            throw new TradeBizException(TradeBizException.TRADE_PAY_WAY_ERROR,"错误的支付方式");
        }
    }

    @Override
    public String getMerchantNotifyMessage(String payWayCode, Map<String, String> resultMap) {

        String resultUrl = null;

        String bankOrderNo = resultMap.get("out_trade_no");
        //根据银行订单号获取支付信息
        TradePaymentRecord tradePaymentRecord = rpTradePaymentRecordDao.getByBankOrderNo(bankOrderNo);
        if (tradePaymentRecord == null){
            throw new TradeBizException(TradeBizException.TRADE_ORDER_ERROR,",非法订单,订单不存在");
        }

        TradePaymentOrder tradePaymentOrder = rpTradePaymentOrderDao.selectByMerchantNoAndMerchantOrderNo(tradePaymentRecord.getMerchantNo(), tradePaymentRecord.getMerchantOrderNo());

        if(PayWayEnum.WEIXIN.name().equals(payWayCode)){
            if (WeixinTradeStateEnum.SUCCESS.name().equals(resultMap.get("result_code"))){//业务结果 成功
                resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
            }else{
                resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.FAILED);
            }

        }else if (PayWayEnum.ALIPAY.name().equals(payWayCode)){
            String trade_status = resultMap.get("trade_status");
            //验证签名
            if(trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")){
                resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
            }else{
                resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.FAILED);
            }
        }else if(PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWayCode)){
            if (WeixinTradeStateEnum.SUCCESS.name().equals(resultMap.get("result_code"))){//业务结果 成功
                resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.SUCCESS);
            }else{
                resultUrl = getMerchantNotifyUrl(tradePaymentRecord, tradePaymentOrder, tradePaymentRecord.getReturnUrl(), TradeStatusEnum.FAILED);
            }

        }

        return getNotifySendStr(resultUrl , tradePaymentRecord.getMerchantOrderNo() , tradePaymentRecord.getMerchantNo());

    }


    /**
     * 发送消息通知
     *
     * @param notifyUrl       通知地址
     * @param merchantOrderNo 商户订单号
     * @param merchantNo      商户编号
     */
    public String getNotifySendStr(String notifyUrl, String merchantOrderNo, String merchantNo) {

        NotifyRecord record = new NotifyRecord();
        record.setNotifyTimes(0);
        record.setLimitNotifyTimes(5);
        record.setStatus(NotifyStatusEnum.CREATED.name());
        record.setUrl(notifyUrl);
        record.setMerchantOrderNo(merchantOrderNo);
        record.setMerchantNo(merchantNo);
        record.setNotifyType(NotifyTypeEnum.MERCHANT.name());

        Object toJSON = JSONObject.toJSON(record);
        String jsonStr = toJSON.toString();

        return jsonStr;
    }


}
