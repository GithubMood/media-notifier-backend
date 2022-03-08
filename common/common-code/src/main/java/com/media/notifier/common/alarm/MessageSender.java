package com.media.notifier.common.alarm;

public interface MessageSender {
    void sendMessage(String destinationId,
                     String destinationToken,
                     String message);
}
