package io.averkhoglyad.ostock.organization.events;

import io.averkhoglyad.ostock.common.context.UserContext;
import io.averkhoglyad.ostock.common.context.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);

    private final Source source;

    public SimpleSourceBean(Source source) {
        this.source = source;
    }

    public void publishOrganizationChange(ActionEnum action, String organizationId) {
        logger.debug("Sending Kafka message {} for Organization Id: {}", action, organizationId);
        OrganizationChangeModel change = new OrganizationChangeModel(
                OrganizationChangeModel.class.getTypeName(),
                action.toString(),
                organizationId,
                UserContextHolder.getContext().getTrackingId());
        source.output().send(MessageBuilder.withPayload(change).build());
    }

}
