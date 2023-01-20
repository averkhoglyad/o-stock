package io.verkhoglyad.ostock.licensing.service.client.discovery;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface ProviderStrategy {

    ServiceInstance selectInstance(List<ServiceInstance> instances);

}
