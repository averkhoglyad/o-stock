package io.averkhoglyad.ostock.common.context;

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
        headers.add(Headers.USER_ID, context.getUserId());
        headers.add(Headers.AUTH_TOKEN, context.getAuthToken());
        headers.add(Headers.ORGANIZATION_ID, context.getOrganizationId());
        return execution.execute(request, body);
    }
}
