package io.verkhoglyad.ostock.licensing.controller;

import io.verkhoglyad.ostock.licensing.model.License;
import io.verkhoglyad.ostock.licensing.service.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("v1/organization/{organizationId}/license")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService service;
    private final MessageSource messageSource;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return service.getLicensesByOrganization(organizationId);
    }

    @GetMapping("/{licenseId}")
    public ResponseEntity<RepresentationModel<?>> getLicense(@PathVariable("organizationId") String organizationId,
                                                             @PathVariable("licenseId") String licenseId) {
        var license = service.getLicense(organizationId, licenseId);
        RepresentationModel<?> representationModel = RepresentationModel.of(license);
        representationModel.add(linkTo(methodOn(LicenseController.class)
                        .getLicense(organizationId, license.getLicenseId()))
                        .withSelfRel(),
                linkTo(methodOn(LicenseController.class)
                        .createLicense(license))
                        .withRel("create"),
                linkTo(methodOn(LicenseController.class)
                        .updateLicense(license))
                        .withRel("update"),
                linkTo(methodOn(LicenseController.class)
                        .deleteLicense(license.getLicenseId(), null))
                        .withRel("delete"));
        return ResponseEntity.ok(representationModel);
    }

    @PostMapping
    public ResponseEntity<License> createLicense(@RequestBody License license) {
        return ResponseEntity.ok(service.createLicense(license));
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(@RequestBody License license) {
        return ResponseEntity.ok(service.updateLicense(license));
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("licenseId") String licenseId,
                                                @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        var message = service.deleteLicense(licenseId);
        return ResponseEntity.ok(messageSource.getMessage(message.message(), message.args(), locale));
    }
}
