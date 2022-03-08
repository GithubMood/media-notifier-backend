package com.media.notifier.facebook.impl.domain;

import com.media.notifier.facebook.impl.integration.db.repository.FacebookNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(
        value = "facebook.send.notification.timer.enabled",
        havingValue = "true",
        matchIfMissing = true)
@RequiredArgsConstructor
public class FacebookNotificationDeliveryTimer {
    private final FacebookNotificationRepository facebookNotificationRepository;
    private final FacebookMessageSender facebookMessageSender;

    @Scheduled(
            fixedDelayString = "${facebook.send.notification.delay.between-jobs}",
            initialDelayString = "${facebook.send.notification.delay.initial}"
    )
    public void deliverNotification() {
        try {
            facebookNotificationRepository.findNotificationForSend()
                    .ifPresent(facebookMessageSender::sendMessage);
        } catch (Exception e) {
            log.error("Unable to deliver facebook message: ", e);
        }
    }
}
