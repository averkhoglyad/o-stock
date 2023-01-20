package io.verkhoglyad.ostock.licensing.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Organization {

    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

}
