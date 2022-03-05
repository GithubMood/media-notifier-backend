package com.media.notifier.author.impl.integration.db.repository.jpa;

import com.media.notifier.author.impl.integration.db.entity.AirAlarmEntity;
import com.media.notifier.author.impl.integration.db.repository.AirAlarmRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AirAlarmRepositoryImpl extends BaseRepositoryImpl<AirAlarmEntity, Long> implements AirAlarmRepository {
    private static final long AIR_ALARM_ID = 1L;

    public AirAlarmRepositoryImpl(EntityManager em) {
        super(AirAlarmEntity.class, em);
    }

    @Override
    public AirAlarmEntity findAirAlarm() {
        return queryFactory
                .from(airAlarm)
                .select(airAlarm)
                .where(airAlarm.id.eq(AIR_ALARM_ID))
                .fetchOne();
    }
}
