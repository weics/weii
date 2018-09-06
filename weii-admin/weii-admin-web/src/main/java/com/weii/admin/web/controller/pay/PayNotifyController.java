package com.weii.admin.web.controller.pay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @Author: weics
 * @Date: Created in 22:39 2018/9/6
 * @Description:
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/payNotify")
public class PayNotifyController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(PayNotifyController.class);

    @RequestMapping("/notify")
    public String scanPay(HttpServletRequest httpServletRequest , HttpServletResponse httpServletResponse ){
        Map requestMap = getParamMap_NullStr(httpServletRequest.getParameterMap());
        LOG.info("银行返回结果：{}",requestMap);

        try {
            httpServletResponse.getWriter().print("success");
        } catch (IOException e) {
            LOG.error("回写失败：",e);
        }
        return null ;
    }
}
