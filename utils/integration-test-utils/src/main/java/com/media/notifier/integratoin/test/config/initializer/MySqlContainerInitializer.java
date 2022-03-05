package com.media.notifier.integratoin.test.config.initializer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;

public class MySqlContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static MySQLContainer mySQLContainer = new MySQLContainer<>("mysql:8.0.22")
            .withUsername("admin")
            .withPassword("admin")
            .withDatabaseName("media-notification")
            .withLabel("group", "media-notification-db");

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        mySQLContainer.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                "spring.datasource.password=" + mySQLContainer.getPassword(),
                "spring.datasource.username=" + mySQLContainer.getUsername()
        ).applyTo(configurableApplicationContext.getEnvironment());
    }
}
