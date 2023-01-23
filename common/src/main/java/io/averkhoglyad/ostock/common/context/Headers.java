package io.averkhoglyad.ostock.common.context;

public abstract class Headers {

    private Headers() {}

    public static final String TRACKING_ID = "X-Tracking-Id";
    public static final String AUTH_TOKEN = "X-Auth-Token";
    public static final String USER_ID = "X-User-Id";
    public static final String ORGANIZATION_ID = "X-Organization-Id";

}
