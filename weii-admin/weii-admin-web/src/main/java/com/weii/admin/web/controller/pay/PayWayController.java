package com.weii.admin.web.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weii.pay.common.core.enums.PayTypeEnum;
import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.enums.PublicStatusEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.common.core.utils.StringUtil;
import com.weii.pay.service.user.api.PayProductService;
import com.weii.pay.service.user.api.PayWayService;
import com.weii.pay.service.user.entity.PayProduct;
import com.weii.pay.service.user.entity.PayWay;
import com.weii.pay.service.user.exceptions.PayBizException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Author: weics
 * @Date: Created in 22:12 2018/5/12
 * @Description:
 * @Modified By:
 */
//@RestController
//@RequestMapping("/pay/way")
public class PayWayController {
    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private PayWayService payWayService;

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private PayProductService payProductService;

    /**
     * 函数功能说明 ： 查询分页数据
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
    public String list(PayWay payWay, PageParam pageParam, Model model) {
        // payProductCode 每次添加或编辑后 会变成以“,”分隔的重复数据
        if(!StringUtil.isEmpty(payWay.getPayProductCode())&& payWay.getPayProductCode().contains(",")){
            String[] payProductCodes = payWay.getPayProductCode().split(",");
            payWay.setPayProductCode(payProductCodes[0]);
        }
        PayProduct payProduct = payProductService.getByProductCode(payWay.getPayProductCode(), null);
        PageBean pageBean = payWayService.listPage(pageParam, payWay);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpPayWay", payWay);
        model.addAttribute("rpPayProduct", payProduct);
        return "pay/way/list";
    }

    /**
     * 函数功能说明 ：跳转添加
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
    public String addUI(Model model, @RequestParam("payProductCode") String payProductCode) {
        model.addAttribute("PayWayEnums", PayWayEnum.toList());
        model.addAttribute("PayTypeEnums", PayTypeEnum.toList());
        model.addAttribute("payProductCode", payProductCode);
        return "pay/way/add";
    }

    /**
     * 函数功能说明 ： 保存
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, PayWay payWay) {
        payWayService.createPayWay(payWay.getPayProductCode(), payWay.getPayWayCode(), payWay.getPayTypeCode(), payWay.getPayRate());
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }

    /**
     * 函数功能说明 ：跳转编辑
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/editUI", method = RequestMethod.GET)
    public String editUI(Model model,@RequestParam("id") String id) {
        PayWay payWay = payWayService.getDataById(id);
        model.addAttribute("PayWayEnums", PayWayEnum.toList());
        model.addAttribute("PayTypeEnums", PayTypeEnum.toList());
        model.addAttribute("rpPayWay", payWay);
        return "pay/way/edit";
    }

    /**
     * 函数功能说明 ： 更新
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model, PayWay payWay) {
        PayWay payWayOld = payWayService.getDataById(payWay.getId());
        payWayOld.setEditTime(new Date());
        payWayOld.setPayRate(payWay.getPayRate());
        PayProduct payProduct = payProductService.getByProductCode(payWay.getPayProductCode(), null);
        if(payProduct.getAuditStatus().equals(PublicEnum.YES.name())){
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE,"支付产品已生效，无法删除！");
        }
        payWayService.updateData(payWayOld);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }

    /**
     * 函数功能说明 ： 删除
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/delete", method ={RequestMethod.POST,RequestMethod.GET})
    public String delete(Model model, @RequestParam("id") String id) {
        PayWay payWay = payWayService.getDataById(id);
        PayProduct payProduct = payProductService.getByProductCode(payWay.getPayProductCode(), null);
        if(payProduct.getAuditStatus().equals(PublicEnum.YES.name())){
            throw new PayBizException(PayBizException.PAY_PRODUCT_IS_EFFECTIVE,"支付产品已生效，无法删除！");
        }
        payWay.setStatus(PublicStatusEnum.UNACTIVE.name());
        payWayService.updateData(payWay);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }

    /**
     * 函数功能说明 ：根据支付方式获取支付类型
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/getPayType", method = RequestMethod.GET)
    @ResponseBody
    public List getPayType(@RequestParam("payWayCode") String payWayCode) {
        return PayTypeEnum.getWayList(payWayCode);
    }

    /**
     * 函数功能说明 ：根据支付产品获取支付方式
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/getPayWay", method = RequestMethod.GET)
    @ResponseBody
    public List getPayWay(@RequestParam("productCode") String productCode) {
        List<PayWay> payWayList = payWayService.listByProductCode(productCode);

        Map<String, String> map = new HashMap<String, String>();
        //过滤重复数据
        for(PayWay payWay : payWayList){
            map.put(payWay.getPayWayCode(), payWay.getPayWayName());
        }

        //转换json
        List list = new ArrayList();
        for (String key : map.keySet()) {
            Map<String, String> mapJson = new HashMap<String, String>();
            mapJson.put("desc", map.get(key));
            mapJson.put("name", key);
            list.add(mapJson);
        }
        return list;
    }
}
