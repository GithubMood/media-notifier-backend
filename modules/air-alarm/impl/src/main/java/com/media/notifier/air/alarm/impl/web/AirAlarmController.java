package com.media.notifier.air.alarm.impl.web;

import com.media.notifier.air.alarm.impl.domain.model.AirAlarmInfo;
import com.media.notifier.air.alarm.impl.domain.service.AirAlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/air_alarm")
@RequiredArgsConstructor
public class AirAlarmController {
    private final AirAlarmService airAlarmService;

    @PutMapping(value = "/start")
    public void startAlarm() {
        log.info("Starting alarm");
        airAlarmService.startAirAlarm();
        log.info("Alarm started");
    }

    @PutMapping(value = "/stop")
    public void stopAlarm() {
        log.info("Stopping alarm");
        airAlarmService.stopAirAlarm();
        log.info("Alarm stopped");
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public AirAlarmInfo getAlarmStatus() {
        log.info("Get Alarm STARTED");
        var alarmInfo = airAlarmService.getAlarmInfo();
        log.info("Get Alarm FINISHED. Result: " + alarmInfo);
        return alarmInfo;
    }
}
