package com.media.notifier.air.alarm.impl.integration.db.repository.jpa;

import com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmEntity;
import com.media.notifier.air.alarm.impl.integration.db.entity.QAirAlarmEntity;
import com.media.notifier.air.alarm.impl.integration.db.repository.AirAlarmRepository;
import com.media.notifier.common.database.repository.BaseRepositoryImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class AirAlarmRepositoryImpl extends BaseRepositoryImpl<AirAlarmEntity, Long> implements AirAlarmRepository {
    protected final QAirAlarmEntity airAlarm = QAirAlarmEntity.airAlarmEntity;

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
