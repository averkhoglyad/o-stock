package io.verkhoglyad.ostock.licensing.service.client;

import io.verkhoglyad.ostock.licensing.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class DiscoveryClientAwareOrganizationClient implements OrganizationClient {

    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    @Override
    public Organization loadOrganization(String organizationId) {
        var instances = discoveryClient.getInstances("organization-service");
        if (instances.isEmpty()) {
            return null;
        }
        var organizationService = instances.get(0); // must be decided what service instance to use here
        var endpointUri = "%s/v1/organization/%s".formatted(organizationService.getUri().toString(), organizationId);
        ResponseEntity<Organization> response = restTemplate.exchange(endpointUri, HttpMethod.GET, null, Organization.class, organizationId);
        return response.getBody();
    }
}
