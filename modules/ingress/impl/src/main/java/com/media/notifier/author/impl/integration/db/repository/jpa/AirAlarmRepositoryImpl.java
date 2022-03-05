package com.media.notifier.author.impl.integration.db.repository.jpa;

import com.media.notifier.author.impl.integration.db.entity.AirAlarmEntity;
import com.media.notifier.author.impl.integration.db.repository.AirAlarmRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AirAlarmRepositoryImpl extends BaseRepositoryImpl<AirAlarmEntity, Long> implements AirAlarmRepository {
    public AirAlarmRepositoryImpl(EntityManager em) {
        super(AirAlarmEntity.class, em);
    }
}
