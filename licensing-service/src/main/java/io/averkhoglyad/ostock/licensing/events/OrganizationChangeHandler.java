package io.averkhoglyad.ostock.licensing.events;

import io.averkhoglyad.ostock.licensing.repository.OrganizationRedisRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganizationChangeHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OrganizationRedisRepository organizationRepository;

    public void handleOrgChange(Message<OrganizationChangeModel> message) {
        var orgChange = message.getPayload();
        logger.debug("Received an {} event for organization id {}", orgChange.getAction(), orgChange.getOrganizationId());
        switch (orgChange.getAction()) {
            case "UPDATE":
            case "DELETE": {
                organizationRepository.deleteById(orgChange.getOrganizationId());
            }
        }
    }

}
