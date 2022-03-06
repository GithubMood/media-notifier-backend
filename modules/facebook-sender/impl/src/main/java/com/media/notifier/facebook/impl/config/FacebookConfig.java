package com.media.notifier.facebook.impl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FacebookConfig {
    @Value("${facebook.application.page_id}")
    private String pageId;
    @Value("${facebook.application.page_access_token}")
    private String pageAccessToken;

//    @Bean
//    public FacebookClient facebookClient(){
//        return new DefaultFacebookClient();
//    }
}
