package io.averkhoglyad.ostock.licensing.events;

import io.averkhoglyad.ostock.licensing.config.EventConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrganizationChangeHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @StreamListener(EventConfig.INPUT)
    public void loggerSink(Message<OrganizationChangeModel> message) {
        var orgChange = message.getPayload();
        logger.debug("Received an {} event for organization id {}", orgChange.getAction(), orgChange.getOrganizationId());
        switch(orgChange.getAction()){
            case "GET":
                logger.debug("Received a GET event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "CREATE":
                logger.debug("Received a CREATE event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "UPDATE":
                logger.debug("Received a UPDATE event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "DELETE":
                logger.debug("Received a DELETE event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            default:
                logger.error("Received an UNKNOWN event from the organization service of type {}", orgChange.getType());
                break;
        }
    }

}
