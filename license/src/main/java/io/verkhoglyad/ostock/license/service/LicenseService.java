package io.verkhoglyad.ostock.license.service;

import io.verkhoglyad.ostock.license.model.License;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private static final AtomicInteger ID_HOLDER = new AtomicInteger(1000);

    private final MessageSource messages;

    public License getLicense(String licenseId, String organizationId) {
        var license = new License();
        license.setId(ID_HOLDER.incrementAndGet());
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setDescription("Software product");
        license.setProductName("O-Stock");
        license.setLicenseType("full");

        return license;
    }

    public String createLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = messages.getMessage("license.create.message", null, locale).formatted(license.toString());
        }
        return responseMessage;
    }

    public String updateLicense(License license, String organizationId, Locale locale) {
        String responseMessage = null;
        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = messages.getMessage("license.update.message", null, locale).formatted(license.toString());
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organizationId, Locale locale) {
        String responseMessage = messages.getMessage("license.delete.message", null, locale).formatted(licenseId, organizationId);
        return responseMessage;
    }
}
