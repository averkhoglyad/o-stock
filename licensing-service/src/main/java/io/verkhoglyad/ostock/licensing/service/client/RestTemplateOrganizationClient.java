package io.verkhoglyad.ostock.licensing.service.client;

import io.verkhoglyad.ostock.licensing.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestTemplateOrganizationClient implements OrganizationClient {

    private final RestTemplate restTemplate;

    @Override
    public Organization loadOrganization(String organizationId) {
        var endpointUri = "http://organization-service/v1/organization/{organizationId}";
        ResponseEntity<Organization> response = restTemplate.exchange(endpointUri, HttpMethod.GET, null, Organization.class, organizationId);
        return response.getBody();
    }

}
