package com.media.notifier.telegram.impl.domain;

import com.media.notifier.common.lock.LeaderElectionService;
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
    private final LeaderElectionService leaderElectionService;

    @Scheduled(
            fixedDelayString = "${telegram.send.notification.delay.between-jobs}",
            initialDelayString = "${telegram.send.notification.delay.initial}"
    )
    public void deliverNotification() {
        if (!leaderElectionService.isCurrentLeader()) {
            log.debug("Current node is not a leader. Do noting for reading report results.");
            return;
        }

        try {
            telegramNotificationRepository.findNotificationForSend()
                    .ifPresent(telegramMessageSender::sendMessage);
        } catch (Exception e) {
            log.error("Unable to deliver telegram message: ", e);
        }
    }
}
