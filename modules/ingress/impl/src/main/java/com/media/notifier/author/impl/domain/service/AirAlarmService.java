package com.media.notifier.author.impl.domain.service;

import com.media.notifier.author.impl.domain.model.AirAlarmStatus;
import com.media.notifier.author.impl.integration.db.repository.AirAlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirAlarmService {
    private final AirAlarmRepository airAlarmRepository;

    public void startAirAlarm() {


    }

    public void stopAirAlarm() {

    }

    public AirAlarmStatus getAlarmStatus() {
        return null;
    }
}
