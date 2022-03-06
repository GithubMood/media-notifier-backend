package com.media.notifier.telegram.impl.domain;

import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramNotificationDeliveryTimer {
    private final TelegramNotificationRepository telegramNotificationRepository;
    private final TelegramMessageSender telegramMessageSender;

    @Scheduled(
            fixedDelayString = "${telegram.send.notification.delay.between-jobs}",
            initialDelayString = "${telegram.send.notification.delay.initial}"
    )
    public void deliverNotification() {
        try {
            telegramNotificationRepository.findNotificationForSend()
                    .ifPresent(telegramMessageSender::sendMessage);
        } catch (Exception e) {
            log.error("Unable to deliver telegram message: ", e);
        }
    }
}
