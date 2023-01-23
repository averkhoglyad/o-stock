package io.averkhoglyad.ostock.organization.config;

import io.averkhoglyad.ostock.common.context.UserContextFilter;
import io.averkhoglyad.ostock.common.context.UserContextInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserContextConfig {

    @Bean
    public UserContextFilter userContextFilter() {
        return new UserContextFilter();
    }

    @Bean
    public UserContextInterceptor userContextInterceptor() {
        return new UserContextInterceptor();
    }
}
