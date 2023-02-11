package io.averkhoglyad.ostock.organization.service;

import io.averkhoglyad.ostock.organization.events.ActionEnum;
import io.averkhoglyad.ostock.organization.events.EventPublisher;
import io.averkhoglyad.ostock.organization.model.Organization;
import io.averkhoglyad.ostock.organization.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static io.averkhoglyad.ostock.organization.util.TransactionSynchronizationHelper.synchronizeTransaction;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository repository;
    private final EventPublisher eventPublisher;

    @Transactional(readOnly = true)
    public Organization findById(String organizationId) {
        synchronizeTransaction(sync -> sync.afterCommit(() -> eventPublisher.publish(ActionEnum.GET, organizationId)));
        return repository.findById(organizationId)
                .orElse(null);
    }

    @Transactional
    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = repository.save(organization);
        var organizationId = organization.getId();
        synchronizeTransaction(sync ->
                sync.afterCommit(() -> eventPublisher.publish(ActionEnum.CREATE, organizationId)));
        return organization;
    }

    @Transactional
    public Organization update(Organization organization) {
        synchronizeTransaction(sync ->
                sync.afterCommit(() -> eventPublisher.publish(ActionEnum.UPDATE, organization.getId())));
        return repository.save(organization);
    }

    @Transactional
    public void delete(Organization organization) {
        repository.deleteById(organization.getId());
        synchronizeTransaction(sync ->
                sync.afterCommit(() -> eventPublisher.publish(ActionEnum.DELETE, organization.getId())));
    }
}
