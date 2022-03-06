package com.media.notifier.integratoin.test.config.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.GenericContainer;

public class RedisContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String PASSWORD = "admin";

    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis:6.2.1")
            .withCommand("redis-server", "--requirepass", PASSWORD)
            .withExposedPorts(6379)
            .withReuse(true)
            .withLabel("group", "smartomica");

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        redisContainer.start();
        configurableApplicationContext.getEnvironment().getSystemProperties()
                .put("REDIS_ADDRESS", buildRedisAddress());
    }

    private String buildRedisAddress() {
        return "redis://%s:%d".formatted(redisContainer.getContainerIpAddress(), redisContainer.getMappedPort(6379));
    }
}
