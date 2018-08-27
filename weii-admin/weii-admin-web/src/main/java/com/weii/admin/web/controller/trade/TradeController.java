package com.weii.admin.web.controller.trade;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.weii.common.pojo.WeiiResult;
import com.weii.pay.common.core.enums.PayTypeEnum;
import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.enums.TrxTypeEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.trade.api.TradePaymentQueryService;
import com.weii.pay.service.trade.entity.TradePaymentOrder;
import com.weii.pay.service.trade.entity.TradePaymentRecord;
import com.weii.pay.service.trade.enums.TradeStatusEnum;
import com.weii.pay.service.trade.vo.PaymentOrderQueryVo;
import com.weii.pay.service.user.enums.FundInfoTypeEnum;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: weics
 * @Date: Created in 12:25 2018/8/26
 * @Description:
 * @Modified By:
 */
@RestController
@RequestMapping("trade")
public class TradeController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private TradePaymentQueryService tradePaymentQueryService;

    @RequestMapping(value = "/listPaymentOrder", method ={RequestMethod.POST,RequestMethod.GET})
    public Object listPaymentOrder(HttpServletRequest request, PaymentOrderQueryVo paymentOrderQueryVo, PageParam pageParam) {
        final PageInfo<TradePaymentOrder> tradePaymentOrderPageInfo = tradePaymentQueryService.listPaymentOrderPage(null, paymentOrderQueryVo);
        Map<String,Object> result = new HashMap<>(10);
        result.put("pageBean", tradePaymentOrderPageInfo);

//        result.put("tradePaymentOrder",paymentOrderQueryVo);//查询条件

        result.put("statusEnums", TradeStatusEnum.toList());//状态
        result.put("payWayNameEnums", PayWayEnum.toList());//支付方式
        result.put("payTypeNameEnums", PayTypeEnum.toList());//支付类型
        result.put("fundIntoTypeEnums", FundInfoTypeEnum.toList());//支付类型

        return WeiiResult.ok(result);
    }


    @RequestMapping(value = "/listPaymentRecord", method ={RequestMethod.POST,RequestMethod.GET})
    public Object listPaymentRecord(HttpServletRequest request,PaymentOrderQueryVo paymentOrderQueryVo) {
        final PageInfo<TradePaymentRecord> tradePaymentRecordPageInfo = tradePaymentQueryService.listPaymentRecordPage(null, paymentOrderQueryVo);
        Map<String,Object> result = new HashMap<>(10);
        result.put("pageBean", tradePaymentRecordPageInfo);

        result.put("statusEnums", TradeStatusEnum.toList());//状态
        result.put("payWayNameEnums", PayWayEnum.toList());//支付方式
        result.put("payTypeNameEnums", PayTypeEnum.toList());//支付类型
        result.put("fundIntoTypeEnums", FundInfoTypeEnum.toList());//支付类型
        result.put("trxTypeEnums", TrxTypeEnum.toList());//支付类型
        return  WeiiResult.ok(result);
    }

}
