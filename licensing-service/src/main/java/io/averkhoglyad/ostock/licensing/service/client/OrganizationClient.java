package io.averkhoglyad.ostock.licensing.service.client;

import io.averkhoglyad.ostock.licensing.model.Organization;

import java.util.Optional;

public interface OrganizationClient {

    Optional<Organization> loadOrganization(String organizationId);

}
