package com.mehmetberkan.paycore.api.controller;

import com.mehmetberkan.paycore.api.dto.command.TransferCommand;
import com.mehmetberkan.paycore.api.dto.request.TransferRequestDto;
import com.mehmetberkan.paycore.api.dto.response.TransferDto;
import com.mehmetberkan.paycore.api.dto.response.TransferResponseDto;
import com.mehmetberkan.paycore.api.mapper.TransferRestMapper;
import com.mehmetberkan.paycore.application.port.in.TransferUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev/v1/transfers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TransferController {
    private final TransferUseCase useCase;
    private final TransferRestMapper restMapper;

    @PostMapping
    public ResponseEntity<TransferResponseDto> transfer(
            @RequestHeader(value = "Idempotency-Key", required = true) String key,
            @RequestBody TransferRequestDto dto
    ) {
        TransferCommand command = restMapper.toCommand(dto, key);
        TransferDto transferDto = useCase.transfer(command);
        TransferResponseDto responseDto = restMapper.toResponse(transferDto);

        return ResponseEntity.status(HttpStatus.CREATED).header("Idempotency-Key",key)
                .body(responseDto);
    }

    @GetMapping( "/get"+"/{id}")
    public ResponseEntity<TransferResponseDto> getTransferById(@PathVariable Long id) {
        TransferDto transferDto = useCase.getTransferById(id);
        TransferResponseDto response = restMapper.toResponse(transferDto);
        return  ResponseEntity.ok(response);
    }
}
