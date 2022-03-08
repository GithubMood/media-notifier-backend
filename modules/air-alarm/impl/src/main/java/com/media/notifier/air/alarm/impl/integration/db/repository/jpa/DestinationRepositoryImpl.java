package com.media.notifier.air.alarm.impl.integration.db.repository.jpa;

import com.media.notifier.air.alarm.impl.integration.db.entity.DestinationEntity;
import com.media.notifier.air.alarm.impl.integration.db.entity.QDestinationEntity;
import com.media.notifier.air.alarm.impl.integration.db.repository.DestinationRepository;
import com.media.notifier.common.database.repository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DestinationRepositoryImpl extends BaseRepositoryImpl<DestinationEntity, Long> implements DestinationRepository {
    protected final QDestinationEntity destination = QDestinationEntity.destinationEntity;

    public DestinationRepositoryImpl(EntityManager em) {
        super(DestinationEntity.class, em);
    }
}
