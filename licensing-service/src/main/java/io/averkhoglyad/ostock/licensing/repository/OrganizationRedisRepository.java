package io.averkhoglyad.ostock.licensing.repository;

import io.averkhoglyad.ostock.licensing.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRedisRepository extends CrudRepository<Organization, String> {
}
