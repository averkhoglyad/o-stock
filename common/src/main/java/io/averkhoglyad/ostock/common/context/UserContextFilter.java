package io.averkhoglyad.ostock.common.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class UserContextFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;

        var context = UserContextHolder.getContext();
        context.setTrackingId(httpServletRequest.getHeader(Headers.TRACKING_ID));
        context.setUserId(httpServletRequest.getHeader(Headers.USER_ID));
        context.setAuthToken(httpServletRequest.getHeader(Headers.AUTH_TOKEN));
        context.setOrganizationId(httpServletRequest.getHeader(Headers.ORGANIZATION_ID));

        logger.debug("UserContextFilter tracking id: {}", context.getTrackingId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

}
