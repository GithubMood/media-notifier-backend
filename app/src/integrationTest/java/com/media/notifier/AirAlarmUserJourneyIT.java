package com.media.notifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.media.notifier.air.alarm.impl.domain.model.AirAlarmInfo;
import com.media.notifier.air.alarm.impl.integration.db.entity.AirAlarmStatus;
import com.media.notifier.air.alarm.impl.integration.db.entity.MessageStatus;
import com.media.notifier.annotation.slices.ApplicationTest;
import com.media.notifier.gateway.web.dto.LoginRequest;
import com.media.notifier.gateway.web.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ApplicationTest
class AirAlarmUserJourneyIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DataSet("dataset/user_journey.xml")
    void airAlarmUserJourney() throws Exception {
        var loginRequest = LoginRequest.builder()
                .login("admin")
                .password("admin")
                .build();
        var loginRequestJson = convertToJson(loginRequest);
        var loginResponseJson = mockMvc.perform(post("/authentication/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var loginResponse = jsonToObject(loginResponseJson, LoginResponse.class);
        var jwtToken = loginResponse.getJwtToken();

        var initStatusJson = mockMvc.perform(get("/air_alarm/status")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var initStatusInfo = jsonToObject(initStatusJson, AirAlarmInfo.class);
        assertThat(initStatusInfo.getStatus()).isEqualTo(AirAlarmStatus.STOPPED);
        assertThat(initStatusInfo.getMessage()).isEmpty();

        mockMvc.perform(put("/air_alarm/start")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk());

        Thread.sleep(3000);

        var startedStatusJson = mockMvc.perform(get("/air_alarm/status")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var startedStatusInfo = jsonToObject(startedStatusJson, AirAlarmInfo.class);
        assertThat(startedStatusInfo.getStatus()).isEqualTo(AirAlarmStatus.STARTED);
        assertThat(startedStatusInfo.getAlarmChangedAt()).isNotNull();
        assertThat(startedStatusInfo.getMessage()).hasSize(2);
        assertThat(startedStatusInfo.getMessage().get(0).getStatus()).isEqualTo(MessageStatus.DELIVERED);
        assertThat(startedStatusInfo.getMessage().get(1).getStatus()).isEqualTo(MessageStatus.DELIVERED);

        mockMvc.perform(put("/air_alarm/stop")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk());

        Thread.sleep(3000);

        var stoppedStatusJson = mockMvc.perform(get("/air_alarm/status")
                        .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var stoppedStatusInfo = jsonToObject(stoppedStatusJson, AirAlarmInfo.class);
        assertThat(stoppedStatusInfo.getStatus()).isEqualTo(AirAlarmStatus.STOPPED);
        assertThat(stoppedStatusInfo.getAlarmChangedAt()).isNotNull();
        assertThat(stoppedStatusInfo.getMessage()).hasSize(2);
        assertThat(stoppedStatusInfo.getMessage().get(0).getStatus()).isEqualTo(MessageStatus.DELIVERED);
        assertThat(stoppedStatusInfo.getMessage().get(1).getStatus()).isEqualTo(MessageStatus.DELIVERED);
    }

    public <T> T jsonToObject(String json, Class<T> type) {
        try {
            return objectMapper
                    .readValue(json.getBytes(StandardCharsets.UTF_8), type);
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to parse json: " + json, e);
        }
    }

    public String convertToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to convert object [%s] to json".formatted(object), e);
        }
    }
}
