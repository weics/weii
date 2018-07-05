package com.weii.admin.web.jwt;

import com.weii.common.pojo.WeiiResult;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * Json web token 入口点
 *
 * @author Zoctan
 * @date 2018/06/09
 */
@Component
public class JwtAuthenticationEntryPoint  {

//    /**
//     * 当访问的资源没有权限时被调用
//     */
//    @Override
//    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException)
//            throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
//        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
//        response.getWriter().print(WeiiResult.ok().toString());
//        response.getWriter().close();
//    }
}
