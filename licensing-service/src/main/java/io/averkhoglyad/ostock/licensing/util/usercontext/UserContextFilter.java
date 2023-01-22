package io.averkhoglyad.ostock.licensing.util.usercontext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class UserContextFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpServletRequest = (HttpServletRequest) servletRequest;

        var context = UserContextHolder.getContext();
        context.setTrackingId(httpServletRequest.getHeader(UserContext.TRACKING_ID));
        context.setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        context.setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        context.setOrganizationId(httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));

        logger.debug("UserContextFilter Correlation id: {}", context.getTrackingId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

}
