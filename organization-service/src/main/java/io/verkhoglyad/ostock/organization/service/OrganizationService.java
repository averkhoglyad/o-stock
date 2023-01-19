package io.verkhoglyad.ostock.organization.service;

import io.verkhoglyad.ostock.organization.model.Organization;
import io.verkhoglyad.ostock.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;

    public Organization findById(String organizationId) {
        return repository.findById(organizationId)
                .orElse(null);
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        return organization;

    }

    public Organization update(Organization organization) {
        return repository.save(organization);
    }

    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
    }
}
