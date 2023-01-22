package io.averkhoglyad.ostock.licensing.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "licenses")
public class License {
    @Id
    @Column(name = "license_id", nullable = false)
    private String licenseId;
    private String description;
    @Column(nullable = false)
    private String organizationId;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String licenseType;
    private String comment;

    @Transient
    private String organizationName;
    @Transient
    private String contactName;
    @Transient
    private String contactPhone;
    @Transient
    private String contactEmail;

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }
}
