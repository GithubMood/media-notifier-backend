package com.media.notifier.air.alarm.impl.integration.db.entity;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "message")
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
public class MessageEntity {
    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @ToString.Include
    private String message;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private DestinationEntity destination;

    @ToString.Include
    @JoinColumn(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEntity that = (MessageEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
