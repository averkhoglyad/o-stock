package io.averkhoglyad.ostock.licensing.util.usercontext;

import lombok.Data;

@Data
public class UserContext {

    public static final String TRACKING_ID = "X-Tracking-Id";
    public static final String AUTH_TOKEN = "X-Auth-Token";
    public static final String USER_ID = "X-User-Id";
    public static final String ORGANIZATION_ID = "X-Organization-Id";

    private String trackingId = new String();
    private String authToken = new String();
    private String userId = new String();
    private String organizationId = new String();

}
