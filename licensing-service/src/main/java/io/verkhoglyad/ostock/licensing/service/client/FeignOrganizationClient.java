package io.verkhoglyad.ostock.licensing.service.client;

import io.verkhoglyad.ostock.licensing.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient("organization-service")
public interface FeignOrganizationClient extends OrganizationClient {

    @Override
    @GetMapping("/v1/organization/{organizationId}")
    Optional<Organization> loadOrganization(@PathVariable String organizationId);

}
