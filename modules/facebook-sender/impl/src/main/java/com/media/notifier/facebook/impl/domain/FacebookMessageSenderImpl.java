package com.media.notifier.facebook.impl.domain;

import com.media.notifier.facebook.api.FacebookMessageSender;
import com.media.notifier.facebook.impl.integration.http.FacebookClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookMessageSenderImpl implements FacebookMessageSender {
    private final FacebookClient facebookClient;

    @Override
    @Retryable(value = Exception.class,
            maxAttempts = 10,
            backoff = @Backoff(delay = 1000))
    public void sendMessage(String destinationId, String destinationToken, String message) {
        var result = facebookClient.publishPost(destinationId, message, destinationToken);

        log.info("Facebook response: " + result);
    }
}
