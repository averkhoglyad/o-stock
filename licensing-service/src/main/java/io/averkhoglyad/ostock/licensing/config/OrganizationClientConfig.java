package io.averkhoglyad.ostock.licensing.config;

import io.averkhoglyad.ostock.common.context.UserContextInterceptor;
import io.averkhoglyad.ostock.licensing.service.client.DiscoveryClientAwareOrganizationClient;
import io.averkhoglyad.ostock.licensing.service.client.OrganizationClient;
import io.averkhoglyad.ostock.licensing.service.client.RestTemplateOrganizationClient;
import io.averkhoglyad.ostock.licensing.service.client.discovery.ProviderStrategy;
import io.averkhoglyad.ostock.licensing.service.client.discovery.RoundRobinStrategy;
import io.averkhoglyad.ostock.licensing.service.client.discovery.ServiceInstanceProvider;
import io.averkhoglyad.ostock.licensing.service.client.discovery.ServiceInstanceProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

public class OrganizationClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationClientConfig.class);

    @Configuration
    @EnableFeignClients("io.averkhoglyad.ostock.licensing")
    @ConditionalOnProperty(name = "application.organization-client-type", havingValue = "feign")
    static class FeignClientConfig {
        @PostConstruct
        void init() {
            LOGGER.info("Using the feign client");
        }
    }

    @Configuration
    @ConditionalOnProperty(name = "application.organization-client-type", havingValue = "rest")
    static class RestClientConfig {
        @PostConstruct
        void init() {
            LOGGER.info("Using the rest client");
        }

        @Bean
        @LoadBalanced
        public RestTemplate getRestTemplate() {
            return createRestTemplate();
        }

        @Bean
        public OrganizationClient organizationClient() {
            return new RestTemplateOrganizationClient(getRestTemplate());
        }
    }

    @Configuration
    @ConditionalOnProperty(name = "application.organization-client-type", havingValue = "discovery")
    static class DiscoveryClientConfig {
        @PostConstruct
        void init() {
            LOGGER.info("Using the discovery client");
        }

        @Bean
        public RestTemplate getRestTemplate() {
            return createRestTemplate();
        }

        @Bean
        public OrganizationClient organizationClient(DiscoveryClient discoveryClient) {
            return new DiscoveryClientAwareOrganizationClient(serviceInstanceProvider("organization-service", discoveryClient), getRestTemplate());
        }

        private ServiceInstanceProvider serviceInstanceProvider(String name, DiscoveryClient discoveryClient) {
            return new ServiceInstanceProviderImpl(name, discoveryClient, providerStrategy());
        }

        private ProviderStrategy providerStrategy() {
            return new RoundRobinStrategy();
        }
    }

    private static RestTemplate createRestTemplate() {
        var restTemplate = new RestTemplate();
        var interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            interceptors = new ArrayList<>();
            restTemplate.setInterceptors(interceptors);
        }
        interceptors.add(new UserContextInterceptor());
        return restTemplate;
    }
}
