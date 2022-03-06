package com.media.notifier.facebook.impl.domain;

import com.media.notifier.facebook.impl.integration.http.FacebookClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookSender {
    @Value("${facebook.application.page_id}")
    private final String pageId;
    @Value("${facebook.application.page_access_token}")
    private final String pageAccessToken;

    private final FacebookClient facebookClient;

    public void sendMessage(String message) {
        try {
            var result = facebookClient.publishPost(pageId, message, pageAccessToken);

            log.info("Facebook response: " + result);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to send facebook message", e);
        }
    }
}
