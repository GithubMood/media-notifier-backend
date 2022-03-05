package com.media.notifier.air.alarm.impl.integration.db.repository;

import com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmEntity;

public interface AirAlarmRepository extends BaseRepository<AirAlarmEntity, Long> {
    AirAlarmEntity findAirAlarm();
}
