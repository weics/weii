package com.weii.pay.controller.pay;

import com.alibaba.dubbo.config.annotation.Reference;
import com.weii.pay.common.core.enums.PublicEnum;
import com.weii.pay.common.core.page.PageBean;
import com.weii.pay.common.core.page.PageParam;
import com.weii.pay.service.user.api.PayProductService;
import com.weii.pay.service.user.entity.PayProduct;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: weics
 * @Date: Created in 22:10 2018/5/12
 * @Description:
 * @Modified By:
 */
//@RestController
//@RequestMapping("/pay/product")
public class PayProductController {

    @Reference(version = "1.0.0")
    private PayProductService payProductService;



    /**
     * 函数功能说明 ： 查询分页
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/list", method ={RequestMethod.POST,RequestMethod.GET})
    public String list(PayProduct payProduct, PageParam pageParam, Model model) {
        PageBean pageBean = payProductService.listPage(pageParam, payProduct);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpPayProduct", payProduct);
        return "pay/product/list";
    }

    /**
     * 函数功能说明 ：跳转添加
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
    public String addUI() {

        return "pay/product/add";
    }

    /**
     * 函数功能说明 ： 保存
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, PayProduct payProduct) {
        payProductService.createPayProduct(payProduct.getProductCode(), payProduct.getProductName());
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
    public String delete(Model model, @RequestParam("productCode") String productCode) {
        payProductService.deletePayProduct(productCode);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }


    /**
     * 函数功能说明 ： 查找带回
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/lookupList", method ={RequestMethod.POST,RequestMethod.GET})
    public String lookupList(PayProduct payProduct, PageParam pageParam, Model model) {
        //查询已生效数据
        payProduct.setAuditStatus(PublicEnum.YES.name());
        PageBean pageBean = payProductService.listPage(pageParam, payProduct);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("rpPayProduct", payProduct);
        return "pay/product/lookupList";
    }

    /**
     * 函数功能说明 ： 审核
     *
     * @参数： @return
     * @return String
     * @throws
     */
    @RequestMapping(value = "/audit", method ={RequestMethod.POST,RequestMethod.GET})
    public String audit(Model model, @RequestParam("productCode") String productCode
            , @RequestParam("auditStatus") String auditStatus) {
        payProductService.audit(productCode, auditStatus);
//        dwz.setStatusCode(DWZ.SUCCESS);
//        dwz.setMessage(DWZ.SUCCESS_MSG);
//        model.addAttribute("dwz", dwz);
//        return DWZ.AJAX_DONE;
        return null;
    }
}
