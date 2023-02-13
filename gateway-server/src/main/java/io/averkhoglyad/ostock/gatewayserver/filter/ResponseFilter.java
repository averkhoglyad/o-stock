package io.averkhoglyad.ostock.gatewayserver.filter;

import io.averkhoglyad.ostock.common.context.Headers;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.sleuth.Span;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class ResponseFilter  {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) -> chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    var traceId = exchange.<Span>getAttribute(Span.class.getName()).context().traceId();
                    logger.debug("Adding the trace id to the outbound headers. {}", traceId);
                    exchange.getResponse().getHeaders().add(Headers.TRACE_ID, traceId);
                    logger.debug("Completing outgoing request for {}.", exchange.getRequest().getURI());
                }));
    }
}
