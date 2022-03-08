package com.media.notifier.air.alarm.impl.integration.db.repository.jpa;

import com.media.notifier.air.alarm.impl.integration.db.entity.MessageEntity;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
import com.media.notifier.air.alarm.impl.integration.db.entity.QMessageEntity;
import com.media.notifier.air.alarm.impl.integration.db.repository.MessageRepository;
import com.media.notifier.common.database.repository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepositoryImpl extends BaseRepositoryImpl<MessageEntity, Long> implements MessageRepository {
    protected final QMessageEntity message = QMessageEntity.messageEntity;

    public MessageRepositoryImpl(EntityManager em) {
        super(MessageEntity.class, em);
    }

    @Override
    public List<MessageEntity> finaAllForSending() {
        return Optional.ofNullable(queryFactory.from(message)
                        .select(message)
                        .where(message.status.eq(MessageStatus.WAITING_FOR_DELIVERY))
                        .fetch())
                .orElse(new ArrayList<>());
    }
}
