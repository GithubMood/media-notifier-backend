package com.media.notifier.facebook.impl.domain;

import com.media.notifier.common.lock.LeaderElectionService;
import com.media.notifier.facebook.impl.integration.db.repository.FacebookNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FacebookNotificationDeliveryTimer {
    private final FacebookNotificationRepository facebookNotificationRepository;
    private final FacebookMessageSender facebookMessageSender;
    private final LeaderElectionService leaderElectionService;

    @Scheduled(
            fixedDelayString = "${facebook.send.notification.delay.between-jobs}",
            initialDelayString = "${facebook.send.notification.delay.initial}"
    )
    public void deliverNotification() {
        if (!leaderElectionService.isCurrentLeader()) {
            log.debug("Current node is not a leader. Do noting for reading report results.");
            return;
        }

        try {
            facebookNotificationRepository.findNotificationForSend()
                    .ifPresent(facebookMessageSender::sendMessage);
        } catch (Exception e) {
            log.error("Unable to deliver facebook message: ", e);
        }
    }
}
