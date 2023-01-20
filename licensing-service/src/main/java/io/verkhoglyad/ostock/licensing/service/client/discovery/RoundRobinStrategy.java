package io.verkhoglyad.ostock.licensing.service.client.discovery;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class RoundRobinStrategy implements ProviderStrategy {

    private final AtomicInteger inc = new AtomicInteger();

    @Override
    public ServiceInstance selectInstance(List<ServiceInstance> instances) {
        int i = inc.getAndIncrement();
        return instances.get(Math.abs(i % instances.size()));
    }
}
