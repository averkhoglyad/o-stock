package io.averkhoglyad.ostock.common.context;

import lombok.Data;

@Data
public class UserContext {

    private String authToken = "";
    private String userId = "";
    private String organizationId = "";

}
