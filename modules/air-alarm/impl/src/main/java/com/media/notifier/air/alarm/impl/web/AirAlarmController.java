package com.media.notifier.air.alarm.impl.web;

import com.media.notifier.air.alarm.impl.domain.model.AirAlarmStatus;
import com.media.notifier.air.alarm.impl.domain.service.AirAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/air_alarm")
@RequiredArgsConstructor
public class AirAlarmController {
    private final AirAlarmService airAlarmService;

    @PutMapping(value = "/start")
    public void startAlarm() {
        airAlarmService.startAirAlarm();
    }

    @PutMapping(value = "/stop")
    public void stopAlarm() {
        airAlarmService.stopAirAlarm();
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public AirAlarmStatus getAlarmStatus() {
        return airAlarmService.getAlarmStatus();
    }
}
