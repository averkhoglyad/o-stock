package io.averkhoglyad.ostock.licensing.config;

import io.averkhoglyad.ostock.licensing.events.EventsChannel;
import org.springframework.cloud.stream.annotation.EnableBinding;

@EnableBinding(EventsChannel.class)
public class EventConfig {

    public static final String INPUT = "inboundOrgChanges";

}
