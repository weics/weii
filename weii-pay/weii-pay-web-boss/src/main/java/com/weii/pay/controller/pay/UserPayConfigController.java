package com.weii.pay.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.RpUserInfoService;
import com.weii.pay.service.user.api.RpUserPayConfigService;
import com.weii.pay.service.user.api.RpUserPayInfoService;
import com.weii.pay.service.user.entity.RpUserPayConfig;
import com.weii.pay.service.user.entity.RpUserPayInfo;
import com.weii.pay.service.user.enums.FundInfoTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: weics
 * @Date: Created in 22:14 2018/5/12
 * @Description:
 * @Modified By:
 */
@RestController
public class UserPayConfigController {
    @Reference(version = "1.0.0")
    private RpUserPayConfigService rpUserPayConfigService;
    @Reference(version = "1.0.0")
    private RpUserInfoService rpUserInfoService;
    @Reference(version = "1.0.0")
    private RpUserPayInfoService rpUserPayInfoService;


    /**
     * 函数功能说明 ： 查询分页数据
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
    public String list(RpUserPayConfig rpUserPayConfig, PageParam pageParam, Model model) {
        PageBean pageBean = rpUserPayConfigService.listPage(pageParam, rpUserPayConfig);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpUserPayConfig", rpUserPayConfig);
        return "pay/config/list";
    }

    /**
     * 函数功能说明 ：跳转添加
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
    public String addUI(Model model) {
        model.addAttribute("FundInfoTypeEnums", FundInfoTypeEnum.toList());
        return "pay/config/add";
    }

    /**
     * 函数功能说明 ： 保存
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request, Model model, RpUserPayConfig rpUserPayConfig) {
        String userNo = request.getParameter("user.userNo");
        String userName = request.getParameter("user.userName");
        String productCode = request.getParameter("product.productCode");
        String productName = request.getParameter("product.productName");
        String we_appId = request.getParameter("we_appId");
        String we_merchantId = request.getParameter("we_merchantId");
        String we_partnerKey = request.getParameter("we_partnerKey");
        String ali_partner = request.getParameter("ali_partner");
        String ali_key = request.getParameter("ali_key");
        String ali_sellerId = request.getParameter("ali_sellerId");
        rpUserPayConfigService.createUserPayConfig(userNo, userName, productCode, productName,
                rpUserPayConfig.getRiskDay(), rpUserPayConfig.getFundIntoType(), rpUserPayConfig.getIsAutoSett(), we_appId
                , we_merchantId, we_partnerKey, ali_partner, ali_sellerId, ali_key);
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
    public String editUI(Model model,@RequestParam("userNo") String userNo) {
        RpUserPayConfig rpUserPayConfig = rpUserPayConfigService.getByUserNo(userNo,null);
        RpUserPayInfo wxUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
        RpUserPayInfo aliUserPayInfo = rpUserPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
        model.addAttribute("FundInfoTypeEnums", FundInfoTypeEnum.toList());
        model.addAttribute("rpUserPayConfig", rpUserPayConfig);
        model.addAttribute("wxUserPayInfo", wxUserPayInfo);
        model.addAttribute("aliUserPayInfo", aliUserPayInfo);
        return "pay/config/edit";
    }

    /**
     * 函数功能说明 ： 更新
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(HttpServletRequest request, Model model, RpUserPayConfig rpUserPayConfig) {
        String productCode = request.getParameter("product.productCode");
        String productName = request.getParameter("product.productName");
        String we_appId = request.getParameter("we_appId");
        String we_merchantId = request.getParameter("we_merchantId");
        String we_partnerKey = request.getParameter("we_partnerKey");
        String ali_partner = request.getParameter("ali_partner");
        String ali_key = request.getParameter("ali_key");
        String ali_sellerId = request.getParameter("ali_sellerId");
        rpUserPayConfigService.updateUserPayConfig(rpUserPayConfig.getUserNo(), productCode, productName,
                rpUserPayConfig.getRiskDay(), rpUserPayConfig.getFundIntoType(), rpUserPayConfig.getIsAutoSett(),
                we_appId, we_merchantId, we_partnerKey, ali_partner, ali_sellerId, ali_key);
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
    public String delete(Model model, @RequestParam("userNo") String userNo) {
        rpUserPayConfigService.deleteUserPayConfig(userNo);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }

    /**
     * 函数功能说明 ： 审核
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/audit", method ={RequestMethod.POST,RequestMethod.GET})
    public String audit(Model model, @RequestParam("userNo") String userNo
            , @RequestParam("auditStatus") String auditStatus) {
        rpUserPayConfigService.audit(userNo, auditStatus);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }

}
