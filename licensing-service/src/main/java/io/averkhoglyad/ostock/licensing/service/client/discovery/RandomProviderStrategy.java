package io.averkhoglyad.ostock.licensing.service.client.discovery;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class RandomProviderStrategy implements ProviderStrategy {

    private final Random rnd = ThreadLocalRandom.current();

    @Override
    public ServiceInstance selectInstance(List<ServiceInstance> instances) {
        return instances.get(rnd.nextInt(instances.size()));
    }
}
