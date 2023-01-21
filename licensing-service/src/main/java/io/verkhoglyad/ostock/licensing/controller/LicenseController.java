package io.verkhoglyad.ostock.licensing.controller;

import io.verkhoglyad.ostock.licensing.data.Message;
import io.verkhoglyad.ostock.licensing.model.License;
import io.verkhoglyad.ostock.licensing.service.LicenseService;
import io.verkhoglyad.ostock.licensing.util.MessageConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
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
    private final MessageConverter messageSource;

    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return service.getLicensesByOrganization(organizationId);
    }

    @GetMapping("/{licenseId}")
    public EntityModel<License> getLicense(@PathVariable("organizationId") String organizationId,
                                           @PathVariable("licenseId") String licenseId) {
        var license = service.getLicense(organizationId, licenseId);
        return licenseToRepresentationModel(license);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<License> createLicense(@PathVariable("organizationId") String organizationId,
                                              @RequestBody License license) {
        return licenseToRepresentationModel(service.createLicense(license));
    }

    @PutMapping
    public EntityModel<License> updateLicense(@PathVariable("organizationId") String organizationId,
                                              @RequestBody License license) {
        return licenseToRepresentationModel(service.updateLicense(license));
    }

    @DeleteMapping("/{licenseId}")
    public ResponseEntity<String> deleteLicense(@PathVariable("organizationId") String organizationId,
                                @PathVariable("licenseId") String licenseId,
                                @RequestHeader(name = "Accept-Language", required = false) Locale locale) {
        service.deleteLicense(licenseId);
        return ResponseEntity.ok(messageSource.message(new Message("license.delete.message", licenseId), locale));
    }

    private EntityModel<License> licenseToRepresentationModel(License license) {
        return EntityModel.of(license)
                .add(linkTo(methodOn(LicenseController.class)
                                .getLicense(license.getOrganizationId(), license.getLicenseId()))
                                .withSelfRel(),
                        linkTo(methodOn(LicenseController.class)
                                .createLicense(license.getOrganizationId(), license))
                                .withRel("create"),
                        linkTo(methodOn(LicenseController.class)
                                .updateLicense(license.getOrganizationId(), license))
                                .withRel("update"),
                        linkTo(methodOn(LicenseController.class)
                                .deleteLicense(license.getOrganizationId(), license.getLicenseId(), null))
                                .withRel("delete"));
    }
}
