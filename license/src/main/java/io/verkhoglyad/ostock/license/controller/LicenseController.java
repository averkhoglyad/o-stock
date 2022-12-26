package io.verkhoglyad.ostock.license.controller;

import io.verkhoglyad.ostock.license.model.License;
import io.verkhoglyad.ostock.license.model.Message;
import io.verkhoglyad.ostock.license.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService service;
    private final MessageSource messageSource;

    @GetMapping("/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId,
                                              @PathVariable("licenseId") String licenseId) {
        var license = service.getLicense(licenseId, organizationId);
        return ResponseEntity.ok(license);
    }

    @PostMapping
    public ResponseEntity<String> createLicense(@PathVariable("organizationId") String organizationId,
                                                @RequestBody License license,
                                                @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        var message = service.createLicense(license, organizationId);
        return ResponseEntity.ok(messageSource.getMessage(message.message(), message.args(), locale));
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(@PathVariable("organizationId") String organizationId,
                                                @RequestBody License license,
                                                @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        var message = service.updateLicense(license, organizationId);
        return ResponseEntity.ok(messageSource.getMessage(message.message(), message.args(), locale));
    }

    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
                                                @PathVariable("licenseId") String licenseId,
                                                @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        var message = service.deleteLicense(licenseId, organizationId);
        return ResponseEntity.ok(messageSource.getMessage(message.message(), message.args(), locale));
    }
}
