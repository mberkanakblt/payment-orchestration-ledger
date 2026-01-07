package com.mehmetberkan.paycore.application.service;

import com.mehmetberkan.paycore.domain.enums.Status;
import com.mehmetberkan.paycore.domain.model.IdempotencyRecord;
import com.mehmetberkan.paycore.infrastructure.repository.IdempotencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyRepository idempotencyRepository;

    public Optional<IdempotencyRecord> findByKey(String key){
        return idempotencyRepository.findById(key);
    }

    public IdempotencyRecord createProcessing(String key, String requestHash){
        if(idempotencyRepository.findById(key).isPresent()){
            throw new RuntimeException("Idempotency key already exists: " + key);
        }
        IdempotencyRecord idempotencyRecord = IdempotencyRecord.builder()
                .key(key)
                .requestHash(requestHash)
                .status(Status.PROCESSING)
                .build();
       return idempotencyRepository.save(idempotencyRecord);
    }

    public void markCompleted(String key, String responseData){
        Optional<IdempotencyRecord> record = findByKey(key);
        if(record.isEmpty()){
            throw new RuntimeException("Idempotency record not found");
        }
        if(record.get().getStatus() == Status.COMPLETED){
            throw new RuntimeException("Idempotency record already completed");
        }
        record.get().setStatus(Status.COMPLETED);
        record.get().setResponseHash(responseData);
        idempotencyRepository.save(record.get());
    }

    public void markFailed(String key){
        Optional<IdempotencyRecord> k = findByKey(key);
       if(k.isPresent()){
        k.get().setStatus(Status.FAILED);
        idempotencyRepository.save(k.get());
       }else{
        throw new RuntimeException("Idempotency record not found");
       }
    }
    

}
