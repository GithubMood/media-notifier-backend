package com.media.notifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.media.notifier.air.alarm.impl.domain.model.AirAlarmInfo;
import com.media.notifier.annotation.slices.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ApplicationTest
class AirAlarmUserJourneyIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DataSet("dataset/air_alarm.xml")
    void airAlarmUserJourney() throws Exception {
        var initStatusJson = mockMvc.perform(get("/air_alarm/status"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var initStatusInfo = jsonToObject(initStatusJson, AirAlarmInfo.class);
        assertThat(initStatusInfo.getStatus()).isEqualTo(AirAlarmInfo.Status.STOPPED);

        mockMvc.perform(put("/air_alarm/start"))
                .andExpect(status().isOk());

        Thread.sleep(1100);

        var startedStatusJson = mockMvc.perform(get("/air_alarm/status"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var startedStatusInfo = jsonToObject(startedStatusJson, AirAlarmInfo.class);
        assertThat(startedStatusInfo.getStatus()).isEqualTo(AirAlarmInfo.Status.STARTED);
        assertThat(startedStatusInfo.getAlarmChangedAt()).isNotNull();
        assertThat(startedStatusInfo.getTelegramPublished()).isTrue();
        assertThat(startedStatusInfo.getTelegramPublishedAt()).isNotNull();

        mockMvc.perform(put("/air_alarm/stop"))
                .andExpect(status().isOk());

        Thread.sleep(1100);

        var stoppedStatusJson = mockMvc.perform(get("/air_alarm/status"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var stoppedStatusInfo = jsonToObject(stoppedStatusJson, AirAlarmInfo.class);
        assertThat(stoppedStatusInfo.getStatus()).isEqualTo(AirAlarmInfo.Status.STOPPED);
        assertThat(stoppedStatusInfo.getAlarmChangedAt()).isNotNull();
        assertThat(startedStatusInfo.getTelegramPublished()).isTrue();
        assertThat(startedStatusInfo.getTelegramPublishedAt()).isNotNull();
    }

    public <T> T jsonToObject(String json, Class<T> type) {
        try {
            return objectMapper
                    .readValue(json.getBytes(StandardCharsets.UTF_8), type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse json: " + json, e);
        }
    }
}
