package com.media.notifier.air.alarm.impl.integration.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "destination")
@Accessors(chain = true)
@ToString(onlyExplicitlyIncluded = true)
public class DestinationEntity {
    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    private String name;

    @ToString.Include
    @Enumerated(EnumType.STRING)
    private DestinationType type;

    @ToString.Exclude
    @Column(name = "destination_id")
    private String destinationId;

    @ToString.Exclude
    @Column(name = "destination_token")
    private String destinationToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinationEntity that = (DestinationEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
