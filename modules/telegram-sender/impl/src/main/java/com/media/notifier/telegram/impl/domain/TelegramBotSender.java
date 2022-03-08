package com.media.notifier.telegram.impl.domain;

import com.media.notifier.telegram.impl.integration.http.TelegramClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramBotSender {
    @Value("${telegram.bot.token}")
    private final String telegramBotToken;
    @Value("${telegram.chat.id}")
    private final String telegramChatId;
    private final TelegramClient telegramClient;


    @Retryable(value = Exception.class,
            maxAttempts = 10,
            backoff = @Backoff(delay = 5000))
    public void sendMessage(String message) {
        try {
            var response = telegramClient.publishMessage(telegramBotToken, telegramChatId, message);

            log.info("Successfully received telegram response: " + response);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to send message", e);
        }
    }
}
