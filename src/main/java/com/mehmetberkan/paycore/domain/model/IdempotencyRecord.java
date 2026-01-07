package com.mehmetberkan.paycore.domain.model;

import com.mehmetberkan.paycore.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class IdempotencyRecord {
    @Id
    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String requestHash;

    @Column
    private String responseHash;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
