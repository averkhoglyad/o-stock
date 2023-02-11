package io.averkhoglyad.ostock.licensing.config;

import io.averkhoglyad.ostock.licensing.events.OrganizationChangeHandler;
import io.averkhoglyad.ostock.licensing.events.OrganizationChangeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Configuration
public class EventConfig {

    @Bean
    public Consumer<Message<OrganizationChangeModel>> orgChange(OrganizationChangeHandler handler) {
        return handler::handleOrgChange;
    }

}
