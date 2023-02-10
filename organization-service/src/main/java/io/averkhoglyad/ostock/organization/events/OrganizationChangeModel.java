package io.averkhoglyad.ostock.organization.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationChangeModel {

    private String type;
    private String action;
    private String organizationId;
    private String trackingId;

}
