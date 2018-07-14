package com.weii.admin.web.exception;

import org.springframework.stereotype.Component;

/**
 * @Author: weics
 * @Date: Created in 23:42 2018/06/26
 * @Description:
 * @Modified By:
 */
//@Component
public class ServiceException  extends RuntimeException {
    public ServiceException(final String message) {
        super(message);
    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}