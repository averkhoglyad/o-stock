package io.averkhoglyad.ostock.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class ResponseFilter  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    var trackingId = FilterHelper.getRequestHeader(exchange, FilterHelper.TRACKING_ID);
                    logger.debug("Adding the tracking id to the outbound headers. {}", trackingId);
                    exchange.getResponse().getHeaders().add(FilterHelper.TRACKING_ID, trackingId);
                    logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
                }));
    }
}
