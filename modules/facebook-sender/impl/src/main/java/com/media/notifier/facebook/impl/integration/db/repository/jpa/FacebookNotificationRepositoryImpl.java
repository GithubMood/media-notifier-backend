package com.media.notifier.facebook.impl.integration.db.repository.jpa;

import com.media.notifier.common.database.repository.BaseRepositoryImpl;
import com.media.notifier.facebook.impl.integration.db.entity.FacebookNotificationEntity;
import com.media.notifier.facebook.impl.integration.db.repository.FacebookNotificationRepository;
import com.media.notifier.facebook.impl.integration.db.entity.NotificationStatus;
import com.media.notifier.facebook.impl.integration.db.entity.QFacebookNotificationEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class FacebookNotificationRepositoryImpl extends BaseRepositoryImpl<FacebookNotificationEntity, Long> implements FacebookNotificationRepository {
    private final QFacebookNotificationEntity facebookNotification = QFacebookNotificationEntity.facebookNotificationEntity;

    public FacebookNotificationRepositoryImpl(EntityManager em) {
        super(FacebookNotificationEntity.class, em);
    }

    @Override
    public Optional<FacebookNotificationEntity> findNotificationForSend() {
        return Optional.ofNullable(queryFactory
                .from(facebookNotification)
                .select(facebookNotification)
                .where(facebookNotification.status.eq(NotificationStatus.WAITING_FOR_DELIVERY))
                .fetchFirst());
    }
}
