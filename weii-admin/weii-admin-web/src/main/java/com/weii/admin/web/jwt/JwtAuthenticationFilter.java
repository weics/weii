package com.weii.admin.web.jwt;


/**
 * 身份认证过滤器
 *
 * @author Zoctan
 * @date 2018/06/09
 */

public class JwtAuthenticationFilter {
//    private final static Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
//    @Resource
//    private JwtUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(@Nonnull final HttpServletRequest request, @Nonnull final HttpServletResponse response, @Nonnull final FilterChain filterChain)
//            throws ServletException, IOException {
//
//        // 解决跨域问题
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With");
//        // 明确允许通过的方法，不建议使用*
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Expose-Headers", "*");
//        // 预请求后，直接返回
//        // 返回码必须为 200 否则视为请求失败
//
//        if ("OPTIONS".equals(request.getMethod())) {
//            return;
//        }
//
//        final String token = this.jwtUtil.getTokenFromRequest(request);
//        if (token == null) {
//            log.info("JwtFilter => Anonymous<> request URL<{}> Method<{}>", "1232131", request.getRequestURL(), request.getMethod());
//        } else {
//            final String username = this.jwtUtil.getUsername(token);
//            log.info("JwtFilter => user<{}> token : {}", username, token);
//            log.info("JwtFilter => request URL<{}> Method<{}>", request.getRequestURL(), request.getMethod());
//
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                final UsernamePasswordAuthenticationToken authentication = this.jwtUtil.getAuthentication(username, token);
//
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                log.info("JwtFilter => user<{}> is authorized, set security context", username);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
}
