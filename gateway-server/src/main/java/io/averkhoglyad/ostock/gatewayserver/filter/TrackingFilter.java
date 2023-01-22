package io.averkhoglyad.ostock.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(1)
public class TrackingFilter implements GlobalFilter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (isTrackingIdPresent(exchange)) {
            logger.debug("Tracking id header is found: {}", detectTrackingId(exchange));
        } else {
            String trackingId = generateTrackingId();
            exchange = populateTrackingId(exchange, trackingId);
            logger.debug("Tracking id is generated: {}", trackingId);
        }
        return chain.filter(exchange);
    }

    private ServerWebExchange populateTrackingId(ServerWebExchange exchange, String trackingId) {
        return FilterHelper.setRequestHeader(exchange, FilterHelper.TRACKING_ID, trackingId);
    }

    private boolean isTrackingIdPresent(ServerWebExchange exchange) {
        return detectTrackingId(exchange) != null;
    }

    private String detectTrackingId(ServerWebExchange exchange) {
        return FilterHelper.getRequestHeader(exchange, FilterHelper.TRACKING_ID);
    }

    private String generateTrackingId() {
        return UUID.randomUUID().toString();
    }
}
