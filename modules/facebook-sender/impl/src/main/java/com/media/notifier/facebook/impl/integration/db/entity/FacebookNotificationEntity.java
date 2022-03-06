package com.media.notifier.facebook.impl.integration.db.entity;

import com.media.notifier.common.alarm.dto.AlarmType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "facebook_air_alarm")
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
public class FacebookNotificationEntity {
    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    private AlarmType type;

    @ToString.Include
    private LocalDateTime deliveredAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacebookNotificationEntity that = (FacebookNotificationEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
