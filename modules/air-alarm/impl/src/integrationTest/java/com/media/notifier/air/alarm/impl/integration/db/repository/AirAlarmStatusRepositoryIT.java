package com.media.notifier.air.alarm.impl.integration.db.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.media.notifier.integratoin.test.config.initializer.MySqlContainerInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

//@PersistenceTest
@DataJpaTest(showSql = false)
@DBRider
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(initializers = {MySqlContainerInitializer.class})
class AirAlarmStatusRepositoryIT {
    @Autowired
    private AirAlarmRepository airAlarmRepository;

    @Test
    @DataSet("dataset/air_alarm.xml")
    void findAirAlarm() {
        //GIVEN
        var id = 1L;

        //WHEN
        var airAlarm = airAlarmRepository.findAirAlarm();

        //THEN
        assertThat(airAlarm).isNotNull();
        assertThat(airAlarm.getStatus()).isEqualTo("STOPPED");
    }
}
