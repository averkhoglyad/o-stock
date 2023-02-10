package io.averkhoglyad.ostock.licensing.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class Handler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @StreamListener(Sink.INPUT)
    public void loggerSink(Message<OrganizationChangeModel> message) {
        OrganizationChangeModel orgChange = message.getPayload();
        logger.debug("Received an {} event for organization id {}",
                orgChange.getAction(), orgChange.getOrganizationId());
    }

}
