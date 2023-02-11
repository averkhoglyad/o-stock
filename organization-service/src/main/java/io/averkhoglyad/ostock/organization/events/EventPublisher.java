package io.averkhoglyad.ostock.organization.events;

import io.averkhoglyad.ostock.common.context.UserContextHolder;
import io.averkhoglyad.ostock.organization.config.EventConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final StreamBridge streamBridge;

    public void publish(ActionEnum action, String organizationId) {
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        var change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action.toString(),
                organizationId,
                UserContextHolder.getContext().getTrackingId());
        streamBridge.send(EventConfig.ORG_CHANGE_OUTPUT, change);
    }
}
