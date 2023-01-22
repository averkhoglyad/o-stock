package io.averkhoglyad.ostock.gatewayserver.filter;

import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

public abstract class FilterHelper {

    private FilterHelper() {}

    public static final String TRACKING_ID = "X-Tracking-Id";
    public static final String AUTH_TOKEN = "X-Auth-Token";
    public static final String USER_ID = "X-User-Id";
    public static final String ORGANIZATION_ID = "X-Organization-Id";

    public static String getRequestHeader(ServerWebExchange exchange, String name) {
        var headers = exchange.getRequest().getHeaders();
        var header = headers.get(name);
        if (CollectionUtils.isEmpty(header)) {
            return null;
        }
        return header.stream().findFirst().get();
    }

    public static ServerWebExchange setRequestHeader(ServerWebExchange exchange, String name, String value) {
        return exchange
                .mutate()
                .request(exchange.getRequest()
                        .mutate()
                        .header(name, value)
                        .build())
                .build();
    }

}
