package com.mehmetberkan.paycore.domain.model;

import com.mehmetberkan.paycore.domain.enums.Status;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Transfer {
    private Long id;
    private Long fromAccountId;
    private Long toAccountId;
    private Money amount;
    private Status status;
    private String idempotencyKey;
    private Instant createdAt;

    public void complete() {
        this.status = Status.COMPLETED;
    }

    public void fail() {
        this.status = Status.FAILED;
    }

    public boolean isCompleted() {
        return this.status == Status.COMPLETED;
    }

    public boolean isFailed() {
        return this.status == Status.FAILED;
    }

    public boolean isPending() {
        return this.status == Status. PROCESSING;
    }
}
