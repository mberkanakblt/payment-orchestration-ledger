package com.mehmetberkan.paycore.infrastructure.repository;

import com.mehmetberkan.paycore.domain.model.IdempotencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotencyRepository extends JpaRepository<IdempotencyRecord,String> {


}
