package io.verkhoglyad.ostock.licensing.service.client;

import io.verkhoglyad.ostock.licensing.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("organization-service")
public interface FeignOrganizationClient extends OrganizationClient {

    @Override
    @RequestMapping(method = RequestMethod.GET, path = "/v1/organization/{organizationId}")
    Organization loadOrganization(@PathVariable String organizationId);

}
