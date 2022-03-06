package com.media.notifier.config;

import com.media.notifier.facebook.impl.domain.FacebookSender;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockBeansConfiguration {
    @Bean
    @Primary
    public FacebookSender mockJobsApi() {
        return Mockito.mock(FacebookSender.class);
    }
}
