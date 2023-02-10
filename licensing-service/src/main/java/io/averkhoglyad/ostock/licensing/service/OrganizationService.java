package io.averkhoglyad.ostock.licensing.service;

import io.averkhoglyad.ostock.common.context.UserContext;
import io.averkhoglyad.ostock.common.context.UserContextHolder;
import io.averkhoglyad.ostock.licensing.model.Organization;
import io.averkhoglyad.ostock.licensing.repository.OrganizationRedisRepository;
import io.averkhoglyad.ostock.licensing.service.client.OrganizationClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final OrganizationClient organizationClient;
    private final OrganizationRedisRepository redisRepository;

    public Optional<Organization> findOrganization(String organizationId) {
        logger.debug("In Licensing Service.getOrganization: {}", UserContextHolder.getContext().getTrackingId());

        var organization = checkRedisCache(organizationId);
        if (organization.isPresent()) {
            logger.debug("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, organization);
            return organization;
        }
        logger.debug("Unable to locate organization from the redis cache: {}.", organizationId);
        organization = organizationClient.loadOrganization(organizationId);
        organization.ifPresent(this::cacheOrganizationObject);
        return organization;
    }

    private Optional<Organization> checkRedisCache(String organizationId) {
        try {
            return redisRepository.findById(organizationId);
        } catch (Exception ex) {
            logger.error("Error encountered while trying to retrieve organization {} check Redis Cache. Exception {}", organizationId, ex);
            return Optional.empty();
        }
    }

    private void cacheOrganizationObject(Organization organization) {
        try {
            redisRepository.save(organization);
        } catch (Exception ex) {
            logger.error("Unable to cache organization {} in Redis. Exception {}", organization.getId(), ex);
        }
    }
}
