package io.averkhoglyad.ostock.licensing.service.client;

import io.averkhoglyad.ostock.licensing.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
public class RestTemplateOrganizationClient implements OrganizationClient {

    private static final String ENDPOINT_URL = "http://organization-service/v1/organization/{organizationId}";

    private final RestTemplate restTemplate;

    @Override
    public Optional<Organization> loadOrganization(String organizationId) {
        ResponseEntity<Organization> response = restTemplate.getForEntity(ENDPOINT_URL, Organization.class, organizationId);
        return Optional.ofNullable(response.getBody());
    }
}
