package io.verkhoglyad.ostock.licensing.service.client;

import io.verkhoglyad.ostock.licensing.model.Organization;

public interface OrganizationClient {

    Organization loadOrganization(String organizationId);

}
