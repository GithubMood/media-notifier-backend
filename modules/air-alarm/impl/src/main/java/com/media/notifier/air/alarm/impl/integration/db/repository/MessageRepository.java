package com.media.notifier.air.alarm.impl.integration.db.repository;

import com.media.notifier.air.alarm.impl.integration.db.entity.MessageEntity;
import com.media.notifier.common.database.repository.BaseRepository;

import java.util.List;

public interface MessageRepository extends BaseRepository<MessageEntity, Long> {
    List<MessageEntity> finaAllForSending();
}
