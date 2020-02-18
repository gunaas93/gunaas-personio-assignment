package com.gunaas.rest.auth.filter;

import com.gunaas.rest.auth.CurrentUserDetails;
import com.gunaas.rest.entity.UserEntity;
import com.gunaas.rest.service.JwtService;
import com.gunaas.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    public static final String TOKEN_HEADER = "X-Authorization";
    public static final String AUTH_TYPE = "Bearer ";
    public static final String unauthorizedResponse = "" +
            "\n{" +
            "\n\"code\":\"UNAUTHORIZED\"," +
            "\n\"message\": \"The token is not valid.\"," +
            "\n\"errors\": []" +
            "\n}" +
            "\n";

    public void doFilter(@Nullable ServletRequest req, @Nullable ServletResponse res, @Nullable FilterChain chain)
            throws IOException {
        lazyInitService(req);
        String authToken = ((HttpServletRequest) req).getHeader(TOKEN_HEADER).replace(AUTH_TYPE, "");
        try {
            if (!jwtService.validateLoginToken(authToken)) {
                responseAuthError(res);
                return;
            }
            Long userId = jwtService.getUserIdFromLoginToken(authToken);
            UserEntity user = userService.getUserById(userId);
            CurrentUserDetails userDetail = new CurrentUserDetails(user);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    userDetail, null, userDetail.getAuthorities()));
            chain.doFilter(req, res);
        } catch (Exception e) {
            responseAuthError(res);
        }
    }

    private final void lazyInitService(ServletRequest request) {
        if (request != null) {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(request.getServletContext());
            if (jwtService == null) jwtService = webApplicationContext.getBean(JwtService.class);
            if (userService == null) userService = webApplicationContext.getBean(UserService.class);
        }
    }

    private final void responseAuthError(ServletResponse res) throws IOException {
        HttpServletResponse response = (HttpServletResponse) res;
        PrintWriter out = response.getWriter();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        out.print(unauthorizedResponse);
        out.flush();
    }
}
