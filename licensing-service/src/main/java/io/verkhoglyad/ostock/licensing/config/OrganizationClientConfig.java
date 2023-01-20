package io.verkhoglyad.ostock.licensing.config;

import io.verkhoglyad.ostock.licensing.service.client.DiscoveryClientAwareOrganizationClient;
import io.verkhoglyad.ostock.licensing.service.client.OrganizationClient;
import io.verkhoglyad.ostock.licensing.service.client.RestTemplateOrganizationClient;
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

public class OrganizationClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationClientConfig.class);

    @Configuration
    @EnableFeignClients("io.verkhoglyad.ostock.licensing")
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
            return new RestTemplate();
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
            return new RestTemplate();
        }

        @Bean
        public OrganizationClient organizationClient(DiscoveryClient discoveryClient) {
            return new DiscoveryClientAwareOrganizationClient(discoveryClient, getRestTemplate());
        }
    }
}
