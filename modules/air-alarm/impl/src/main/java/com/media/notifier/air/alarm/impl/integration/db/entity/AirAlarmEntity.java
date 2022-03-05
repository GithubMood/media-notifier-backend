package com.media.notifier.air.alarm.impl.integration.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "air_alarm")
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
public class AirAlarmEntity {
    @Id
    @ToString.Include
    private Long id;

    @ToString.Include
    private String status;

    @ToString.Include
    private LocalDateTime alarmStarted;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirAlarmEntity that = (AirAlarmEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
