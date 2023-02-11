package io.averkhoglyad.ostock.licensing.events;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface EventsChannel {

    @Input("inboundOrgChanges")
    SubscribableChannel orgs();

}
