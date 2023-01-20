package io.verkhoglyad.ostock.licensing.service.client;

import io.verkhoglyad.ostock.licensing.model.Organization;

import java.util.Optional;

public interface OrganizationClient {

    Optional<Organization> loadOrganization(String organizationId);

}
