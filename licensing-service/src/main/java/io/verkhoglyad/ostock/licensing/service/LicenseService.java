package io.verkhoglyad.ostock.licensing.service;

import io.verkhoglyad.ostock.licensing.config.ServiceConfig;
import io.verkhoglyad.ostock.licensing.model.License;
import io.verkhoglyad.ostock.licensing.model.Message;
import io.verkhoglyad.ostock.licensing.model.Organization;
import io.verkhoglyad.ostock.licensing.repository.LicenseRepository;
import io.verkhoglyad.ostock.licensing.service.client.DiscoveryClientAwareOrganizationClient;
import io.verkhoglyad.ostock.licensing.service.client.FeignOrganizationClient;
import io.verkhoglyad.ostock.licensing.service.client.RestTemplateOrganizationClient;
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

    private final FeignOrganizationClient organizationFeignClient;
    private final RestTemplateOrganizationClient organizationRestClient;
    private final DiscoveryClientAwareOrganizationClient organizationDiscoveryClient;


    public List<License> getLicensesByOrganization(String organizationId) {
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String organizationId, String licenseId, String clientType) {
        var license = licenseRepository
                .findByOrganizationIdAndLicenseId(organizationId, licenseId);
        if (null == license) {
            throw new IllegalArgumentException(
                    String.format(messages.getMessage("license.search.error.message", null, null),
                            licenseId, organizationId)
            );
        }

        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if (null != organization) {
            license.setOrganizationName(organization.getName());
            license.setContactName(organization.getContactName());
            license.setContactEmail(organization.getContactEmail());
            license.setContactPhone(organization.getContactPhone());
        }

        return license.withComment(config.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        return switch (clientType) {
            case "feign" -> {
                System.out.println("I am using the feign client");
                yield organizationFeignClient.loadOrganization(organizationId);
            }
            case "rest" -> {
                System.out.println("I am using the rest client");
                yield organizationRestClient.loadOrganization(organizationId);
            }
            case "discovery" -> {
                System.out.println("I am using the discovery client");
                yield organizationDiscoveryClient.loadOrganization(organizationId);
            }
            default -> organizationRestClient.loadOrganization(organizationId);
        };
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

    public Message deleteLicense(String licenseId) {
        var license = new License();
        license.setLicenseId(licenseId);
        licenseRepository.delete(license);
        return new Message("license.delete.message", licenseId);
    }
}
