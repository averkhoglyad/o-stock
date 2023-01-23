package io.averkhoglyad.ostock.common.context;

import lombok.Data;

@Data
public class UserContext {

    private String trackingId = new String();
    private String authToken = new String();
    private String userId = new String();
    private String organizationId = new String();

}
