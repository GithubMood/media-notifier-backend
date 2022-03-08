package com.media.notifier.config;

import com.media.notifier.facebook.api.FacebookMessageSender;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockBeansConfiguration {
    @Bean
    @Primary
    public FacebookMessageSender mockJobsApi() {
        return Mockito.mock(FacebookMessageSender.class);
    }
}
