package com.media.notifier.telegram.impl.integration.db.repository.jpa;

import com.media.notifier.common.database.repository.BaseRepositoryImpl;
import com.media.notifier.telegram.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.telegram.impl.integration.db.entity.QTelegramNotificationEntity;
import com.media.notifier.telegram.impl.integration.db.entity.TelegramNotificationEntity;
import com.media.notifier.telegram.impl.integration.db.repository.TelegramNotificationRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class TelegramNotificationRepositoryImpl extends BaseRepositoryImpl<TelegramNotificationEntity, Long> implements TelegramNotificationRepository {
    private final QTelegramNotificationEntity telegramNotification = QTelegramNotificationEntity.telegramNotificationEntity;

    public TelegramNotificationRepositoryImpl(EntityManager em) {
        super(TelegramNotificationEntity.class, em);
    }

    @Override
    public Optional<TelegramNotificationEntity> findNotificationForSend() {
        return Optional.ofNullable(queryFactory
                .from(telegramNotification)
                .select(telegramNotification)
                .where(telegramNotification.status.eq(NotificationStatus.WAITING_FOR_DELIVERY))
                .fetchFirst());
    }
}
