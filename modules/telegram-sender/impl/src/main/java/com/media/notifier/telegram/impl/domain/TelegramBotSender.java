package com.media.notifier.telegram.impl.domain;

import com.media.notifier.telegram.api.TelegramMessageSender;
import com.media.notifier.telegram.impl.integration.http.TelegramClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotSender implements TelegramMessageSender {
    private final TelegramClient telegramClient;

    @Override
    @Retryable(value = Exception.class,
            maxAttempts = 10,
            backoff = @Backoff(delay = 1000))
    public void sendMessage(String destinationId, String destinationToken, String message) {
        var response = telegramClient.publishMessage(destinationToken, destinationId, message);

        log.info("Successfully received telegram response: " + response);
    }
}
