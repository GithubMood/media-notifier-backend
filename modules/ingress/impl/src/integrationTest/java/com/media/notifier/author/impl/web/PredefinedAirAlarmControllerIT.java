package com.media.notifier.author.impl.web;

import com.media.notifier.author.impl.config.annotation.slices.RestControllerTest;
import com.media.notifier.author.impl.domain.model.AirAlarmStatus;
import com.media.notifier.author.impl.domain.service.AirAlarmService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.Month;

import static com.media.notifier.integratoin.test.utils.RestDocUtils.createRestDoc;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RestControllerTest(AirAlarmController.class)
class PredefinedAirAlarmControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AirAlarmService airAlarmService;

    @Test
    void getAirAlarmStatus() throws Exception {
        //GIVEN
        var airAlarmStatus = AirAlarmStatus.builder()
                .status(AirAlarmStatus.Status.STARTED)
                .alarmStarted(LocalDateTime.of(1998, Month.APRIL, 1, 12, 45))
                .build();
        given(airAlarmService.getAlarmStatus()).willReturn(airAlarmStatus);

        //WHEN
        mockMvc.perform(get("/air_alarm/status"))
                //THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is("STARTED")))
                .andExpect(jsonPath("alarm_started", is("1998-04-01T12:45:00")))
                .andDo(createRestDoc("air_alarm_get_status"));
    }
}
