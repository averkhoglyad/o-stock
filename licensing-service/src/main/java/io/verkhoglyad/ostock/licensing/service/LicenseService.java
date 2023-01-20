package io.verkhoglyad.ostock.licensing.service;

import io.verkhoglyad.ostock.licensing.config.ServiceConfig;
import io.verkhoglyad.ostock.licensing.exception.AppException;
import io.verkhoglyad.ostock.licensing.model.License;
import io.verkhoglyad.ostock.licensing.data.Message;
import io.verkhoglyad.ostock.licensing.repository.LicenseRepository;
import io.verkhoglyad.ostock.licensing.service.client.OrganizationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final LicenseRepository licenseRepository;
    private final MessageSource messages;
    private final ServiceConfig config;
    private final OrganizationClient organizationClient;

    public List<License> getLicensesByOrganization(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String organizationId, String licenseId) {
        var license = licenseRepository
                .findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .orElseThrow(() -> new AppException("license.search.error.message", licenseId, organizationId));

        organizationClient.loadOrganization(organizationId)
                .ifPresent(organization -> {
                    license.setOrganizationName(organization.getName());
                    license.setContactName(organization.getContactName());
                    license.setContactEmail(organization.getContactEmail());
                    license.setContactPhone(organization.getContactPhone());
                });

        return license.withComment(config.getProperty());
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);
        return license.withComment(config.getProperty());
    }

    public void deleteLicense(String licenseId) {
        licenseRepository.deleteById(licenseId);
    }
}
