package com.media.notifier.telegram.impl.integration.db.repository;

import com.media.notifier.common.database.repository.BaseRepository;
import com.media.notifier.telegram.impl.integration.db.entity.TelegramNotificationEntity;

import java.util.Optional;

public interface TelegramNotificationRepository extends BaseRepository<TelegramNotificationEntity, Long> {
    Optional<TelegramNotificationEntity> findNotificationForSend();
}
