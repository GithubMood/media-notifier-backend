package com.media.notifier.air.alarm.impl.domain.service;

import com.media.notifier.air.alarm.impl.integration.db.entity.MessageEntity;
import com.media.notifier.air.alarm.impl.integration.db.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@ConditionalOnProperty(
        value = "message.delivery.timer.enabled",
        havingValue = "true",
        matchIfMissing = true)
@RequiredArgsConstructor
public class MessageDeliveryTimer {
    private static final int NUMBER_OF_THREADS = 4;
    private static final int SEND_MESSAGE_TIMEOUT = 15;

    private final MessageRepository messageRepository;
    private final MessageSender messageSender;

    @Scheduled(
            fixedDelayString = "${message.delivery.timer.delay.between-jobs}",
            initialDelayString = "${message.delivery.timer.delay.initial}"
    )
    @Transactional(readOnly = true)
    public void deliverNotification() {
        var messages = messageRepository.finaAllForSending();

        if (!messages.isEmpty()) {
            log.info("Found {} messages for sending", messages.size());

            var executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
            for (MessageEntity message : messages) {
                Runnable task = () -> messageSender.sendMessage(message.getId());
                executorService.submit(task);
            }

            log.info("Waiting for execution of all message to be sent");
            waitForAllTasks(executorService);
        }
    }

    private void waitForAllTasks(ExecutorService executorService) {
        //shutdown means execute all submitted task and do not accept the new ones
        executorService.shutdown();
        try {
            var terminatedSuccessfully = executorService.awaitTermination(SEND_MESSAGE_TIMEOUT, TimeUnit.MINUTES);

            if (!terminatedSuccessfully) {
                executorService.shutdownNow();
                throw new IllegalStateException("Timeout occurred during sending messages");
            }
        } catch (InterruptedException e) {
            log.warn("Download assets execution is interrupted", e);
            Thread.currentThread().interrupt();
            executorService.shutdownNow();
            throw new IllegalStateException("The execution of the starting projects is interrupted", e);
        }
    }
}
