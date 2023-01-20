package io.verkhoglyad.ostock.licensing.service.client.discovery;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.util.Predicates.negate;

@RequiredArgsConstructor
public class ServiceInstanceProviderImpl implements ServiceInstanceProvider {

    private final String serviceName;
    private final DiscoveryClient discoveryClient;
    private final ProviderStrategy strategy;

    @Override
    public Optional<ServiceInstance> provide() {
        return Optional.ofNullable(discoveryClient.getInstances(serviceName))
                .filter(negate(List::isEmpty))
                .map(strategy::selectInstance);
    }

}
