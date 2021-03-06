package com.media.notifier.unit.test.extenstion;

import com.example.util.time.Time;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;

public class MockTimeExtension implements BeforeEachCallback, AfterEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        LocalDateTime currentDateTime = LocalDateTime.of(2020, Month.APRIL, 1, 12, 45);
        ZoneId zoneId = ZoneId.of("Europe/Kiev");

        Time.useMockTime(currentDateTime, zoneId);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        Time.useSystemDefaultZoneClock();
    }
}
