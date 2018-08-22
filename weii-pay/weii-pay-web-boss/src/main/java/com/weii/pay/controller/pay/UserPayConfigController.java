package com.weii.pay.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weii.pay.common.core.enums.PayWayEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.UserInfoService;
import com.weii.pay.service.user.api.UserPayConfigService;
import com.weii.pay.service.user.api.UserPayInfoService;
import com.weii.pay.service.user.entity.UserPayConfig;
import com.weii.pay.service.user.entity.UserPayInfo;
import com.weii.pay.service.user.enums.FundInfoTypeEnum;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: weics
 * @Date: Created in 22:14 2018/5/12
 * @Description:
 * @Modified By:
 */
//@RestController
//@RequestMapping("/pay/config")
public class UserPayConfigController {
    @Reference(version = "1.0.0")
    private UserPayConfigService userPayConfigService;
    @Reference(version = "1.0.0")
    private UserInfoService userInfoService;
    @Reference(version = "1.0.0")
    private UserPayInfoService userPayInfoService;


    /**
     * 函数功能说明 ： 查询分页数据
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
    public String list(UserPayConfig userPayConfig, PageParam pageParam, Model model) {
        PageBean pageBean = userPayConfigService.listPage(pageParam, userPayConfig);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpUserPayConfig", userPayConfig);
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
    public String add(HttpServletRequest request, Model model, UserPayConfig userPayConfig) {
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
        userPayConfigService.createUserPayConfig(userNo, userName, productCode, productName,
                userPayConfig.getRiskDay(), userPayConfig.getFundIntoType(), userPayConfig.getIsAutoSett(), we_appId
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
        UserPayConfig userPayConfig = userPayConfigService.getByUserNo(userNo,null);
        UserPayInfo wxUserPayInfo = userPayInfoService.getByUserNo(userNo, PayWayEnum.WEIXIN.name());
        UserPayInfo aliUserPayInfo = userPayInfoService.getByUserNo(userNo, PayWayEnum.ALIPAY.name());
        model.addAttribute("FundInfoTypeEnums", FundInfoTypeEnum.toList());
        model.addAttribute("rpUserPayConfig", userPayConfig);
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
    public String edit(HttpServletRequest request, Model model, UserPayConfig userPayConfig) {
        String productCode = request.getParameter("product.productCode");
        String productName = request.getParameter("product.productName");
        String we_appId = request.getParameter("we_appId");
        String we_merchantId = request.getParameter("we_merchantId");
        String we_partnerKey = request.getParameter("we_partnerKey");
        String ali_partner = request.getParameter("ali_partner");
        String ali_key = request.getParameter("ali_key");
        String ali_sellerId = request.getParameter("ali_sellerId");
        userPayConfigService.updateUserPayConfig(userPayConfig.getUserNo(), productCode, productName,
                userPayConfig.getRiskDay(), userPayConfig.getFundIntoType(), userPayConfig.getIsAutoSett(),
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
        userPayConfigService.deleteUserPayConfig(userNo);
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
        userPayConfigService.audit(userNo, auditStatus);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }

}
