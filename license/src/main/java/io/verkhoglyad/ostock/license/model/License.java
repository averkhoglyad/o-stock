package io.verkhoglyad.ostock.license.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }
}
