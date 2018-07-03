package com.weii.admin.web.exception;

import com.weii.common.pojo.WeiiResult;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * @Author: weics
 * @Date: Created in 23:39 2018/06/26
 * @Description: 统一异常处理
 * @Modified By:
 */
@RestControllerAdvice
public class ExceptionResolver {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public WeiiResult handle400(final ConstraintViolationException e) {
        final String msg = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
        return WeiiResult.ok(msg);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class, ServletException.class})
    public WeiiResult handle400(final Throwable e) {
        return WeiiResult.ok(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public WeiiResult handle400() {
        return WeiiResult.ok("database error");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
    public WeiiResult handle401(final Throwable e) {
        return WeiiResult.ok(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class, UsernameNotFoundException.class})
    public WeiiResult handle403(final Throwable e) {
        return WeiiResult.ok(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public WeiiResult handle404(final HttpServletRequest request) {
        return WeiiResult.ok("API [" + request.getRequestURI() + "] not existed");
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public WeiiResult handle405() {
        return WeiiResult.ok();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public WeiiResult globalException(final HttpServletRequest request, final Throwable e) {
        return WeiiResult.ok(String.format("%s => %s", request.getRequestURI(), e.getMessage()));
    }
}
