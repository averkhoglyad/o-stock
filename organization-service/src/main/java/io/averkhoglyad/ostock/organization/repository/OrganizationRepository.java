package io.averkhoglyad.ostock.organization.repository;

import io.averkhoglyad.ostock.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, String> {
}
