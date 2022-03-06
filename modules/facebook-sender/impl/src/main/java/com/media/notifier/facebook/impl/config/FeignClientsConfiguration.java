package com.media.notifier.facebook.impl.config;

import com.media.notifier.facebook.impl.integration.http.FacebookClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = FacebookClient.class)
public class FeignClientsConfiguration {
}
