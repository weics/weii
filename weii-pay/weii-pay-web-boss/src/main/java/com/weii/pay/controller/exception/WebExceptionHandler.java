package com.weii.pay.controller.exception;

import com.weii.pay.common.core.exception.BizException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: weics
 * @Date: Created in 22:09 2018/5/12
 * @Description:
 * @Modified By:
 */
@ControllerAdvice
public class WebExceptionHandler {
    private static final Log LOG = LogFactory.getLog(WebExceptionHandler.class);

    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     */
    @ExceptionHandler({ BizException.class })
    @ResponseStatus(HttpStatus.OK)
    public String processBizException(HttpServletRequest request, BizException e) {
        LOG.error("BizException", e);
//        DwzAjax dwz = new DwzAjax();
//        dwz.setStatusCode(DWZ.ERROR);
//        dwz.setMessage(e.getMsg());
//        request.setAttribute("dwz", dwz);
        return "common/ajaxDone";
    }


    /**
     * 总异常
     */
    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.OK)
    public String processException(Exception e, HttpServletRequest request) {
        LOG.error("Exception", e);
//        DwzAjax dwz = new DwzAjax();
//        dwz.setStatusCode(DWZ.ERROR);
//        dwz.setMessage("系统异常");
//        request.setAttribute("dwz", dwz);
        return "common/ajaxDone";
    }
}
