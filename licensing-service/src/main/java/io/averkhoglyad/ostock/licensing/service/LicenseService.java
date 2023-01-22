package io.averkhoglyad.ostock.licensing.service;

import io.averkhoglyad.ostock.licensing.config.ServiceConfig;
import io.averkhoglyad.ostock.licensing.exception.AppException;
import io.averkhoglyad.ostock.licensing.model.License;
import io.averkhoglyad.ostock.licensing.repository.LicenseRepository;
import io.averkhoglyad.ostock.licensing.service.client.OrganizationClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;
    private final OrganizationClient organizationClient;

    @CircuitBreaker(name = "licenseService", fallbackMethod = "getLicensesByOrganizationFallback")
    @Retry(name = "retryLicenseService", fallbackMethod="getLicensesByOrganizationFallback")
    @Bulkhead(name= "bulkheadLicenseService", fallbackMethod= "getLicensesByOrganizationFallback")
    @RateLimiter(name = "licenseService", fallbackMethod = "getLicensesByOrganizationFallback")
    public List<License> getLicensesByOrganization(String organizationId) throws TimeoutException {
        randomlyRunLong();
        return licenseRepository.findByOrganizationId(organizationId);
    }

    private void randomlyRunLong() throws TimeoutException {
        var rand = ThreadLocalRandom.current();
        int randomNum = rand.nextInt(0, 3);
        if (randomNum == 0) {
            sleep();
        }
    }

    private void sleep() throws TimeoutException {
        try {
            Thread.sleep(5000);
            throw new TimeoutException();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    public List<License> getLicensesByOrganizationFallback(String organizationId, Throwable e) {
        logger.error("LicenseService.getLicensesByOrganization error", e);
        var license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        return List.of(license);
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
