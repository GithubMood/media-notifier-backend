package com.media.notifier.facebook.impl.integration.db.repository;

import com.media.notifier.common.database.repository.BaseRepository;
import com.media.notifier.facebook.impl.integration.db.entity.FacebookNotificationEntity;

import java.util.Optional;

public interface FacebookNotificationRepository extends BaseRepository<FacebookNotificationEntity, Long> {
    Optional<FacebookNotificationEntity> findNotificationForSend();
}
