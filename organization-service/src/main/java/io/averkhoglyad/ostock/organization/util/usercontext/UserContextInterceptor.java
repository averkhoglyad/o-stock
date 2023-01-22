package io.averkhoglyad.ostock.organization.util.usercontext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        UserContext context = UserContextHolder.getContext();
        headers.add(UserContext.TRACKING_ID, context.getTrackingId());
        headers.add(UserContext.AUTH_TOKEN, context.getAuthToken());
        return execution.execute(request, body);
    }
}
