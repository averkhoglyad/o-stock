package io.verkhoglyad.ostock.licensing.service.client;

import io.verkhoglyad.ostock.licensing.model.Organization;
import io.verkhoglyad.ostock.licensing.service.client.discovery.ServiceInstanceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
public class DiscoveryClientAwareOrganizationClient implements OrganizationClient {

    private static final String ENDPOINT_PATH = "/v1/organization/{organizationId}";

    private final ServiceInstanceProvider serviceProvider;
    private final RestTemplate restTemplate;

    @Override
    public Optional<Organization> loadOrganization(String organizationId) {
        return serviceProvider.provide()
                .map(service -> buildEndpointUri(organizationId, service))
                .map(uri -> restTemplate.getForEntity(uri, Organization.class))
                .map(HttpEntity::getBody);
    }

    private URI buildEndpointUri(String organizationId, ServiceInstance service) {
        return UriComponentsBuilder.fromUri(service.getUri())
                .path(ENDPOINT_PATH)
                .build()
                .expand(organizationId)
                .toUri();
    }
}
