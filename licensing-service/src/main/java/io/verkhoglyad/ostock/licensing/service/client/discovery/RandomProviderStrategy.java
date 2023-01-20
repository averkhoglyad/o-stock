package io.verkhoglyad.ostock.licensing.service.client.discovery;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.data.util.Predicates.negate;

@RequiredArgsConstructor
public class RandomProviderStrategy implements ProviderStrategy {

    private final Random rnd = ThreadLocalRandom.current();

    @Override
    public ServiceInstance selectInstance(List<ServiceInstance> instances) {
        return instances.get(rnd.nextInt(instances.size()));
    }
}
