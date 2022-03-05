package com.media.notifier.telegram.impl.integration.db.repository.jpa;

import com.media.notifier.common.database.repository.BaseRepositoryImpl;
import com.media.notifier.telegram.impl.integration.db.entity.TelegramNotificationEntity;
import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class TelegramNotificationRepositoryImpl extends BaseRepositoryImpl<TelegramNotificationEntity, Long> implements TelegramNotificationRepository {
    public TelegramNotificationRepositoryImpl(EntityManager em) {
        super(TelegramNotificationEntity.class, em);
    }
}
