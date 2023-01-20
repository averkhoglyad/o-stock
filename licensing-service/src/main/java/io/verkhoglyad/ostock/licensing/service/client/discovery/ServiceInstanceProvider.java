package io.verkhoglyad.ostock.licensing.service.client.discovery;

import org.springframework.cloud.client.ServiceInstance;

import java.util.Optional;

public interface ServiceInstanceProvider {

    Optional<ServiceInstance> provide();

}
