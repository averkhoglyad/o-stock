package io.averkhoglyad.ostock.licensing.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@ToString
@RedisHash("organization")
public class Organization {

    @Id
    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

}
