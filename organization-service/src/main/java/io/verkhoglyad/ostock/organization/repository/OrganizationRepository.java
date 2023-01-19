package io.verkhoglyad.ostock.organization.repository;

import io.verkhoglyad.ostock.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
