package io.verkhoglyad.ostock.license.service;

import io.verkhoglyad.ostock.license.model.License;
import io.verkhoglyad.ostock.license.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class LicenseService {

    private static final AtomicInteger ID_HOLDER = new AtomicInteger(1000);

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

    public Message createLicense(License license, String organizationId) {
        if (license == null) {
            return null;
        }
        license.setOrganizationId(organizationId);
        return new Message("license.create.message", license.toString());
    }

    public Message updateLicense(License license, String organizationId) {
        if (license == null) {
            return null;
        }
        license.setOrganizationId(organizationId);
        return new Message("license.update.message", license.toString());
    }

    public Message deleteLicense(String licenseId, String organizationId) {
        return new Message("license.delete.message", licenseId, organizationId);
    }
}
