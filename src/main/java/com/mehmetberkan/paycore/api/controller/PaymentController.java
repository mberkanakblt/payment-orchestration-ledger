package com.mehmetberkan.paycore.api.controller;

import com.mehmetberkan.paycore.api.dto.command.AuthorizePaymentCommand;
import com.mehmetberkan.paycore.api.dto.request.AuthorizePaymentRequest;
import com.mehmetberkan.paycore.api.dto.response.PaymentDto;
import com.mehmetberkan.paycore.api.dto.response.PaymentResponseDto;
import com.mehmetberkan.paycore.api.mapper.PaymentRestMapper;
import com.mehmetberkan.paycore.application.port.in.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dev/v1/payments")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PaymentController {
    private final PaymentUseCase paymentUseCase;
    private final PaymentRestMapper restMapper;

    @PostMapping("/authorize")
    public ResponseEntity<PaymentResponseDto> authorize(
            @RequestHeader(value = "Idempotency-Key", required = true) String idempotencyKey,
            @RequestBody AuthorizePaymentRequest request
    ) {
        String authCode = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        AuthorizePaymentCommand command = restMapper.toCommand(request, idempotencyKey, authCode);
        PaymentDto paymentDto = paymentUseCase.authorize(command);
        PaymentResponseDto response = restMapper.toResponse(paymentDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Idempotency-Key", idempotencyKey)
                .body(response);
    }

    @PostMapping("/{paymentId}/capture")
    public ResponseEntity<PaymentResponseDto> capture(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentUseCase.capture(paymentId);
        PaymentResponseDto response = restMapper.toResponse(paymentDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{paymentId}/void")
    public ResponseEntity<PaymentResponseDto> voidPayment(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentUseCase.voidPayment(paymentId);
        PaymentResponseDto response = restMapper.toResponse(paymentDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponseDto> refund(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentUseCase.refund(paymentId);
        PaymentResponseDto response = restMapper.toResponse(paymentDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long paymentId) {
        PaymentDto paymentDto = paymentUseCase.getPaymentById(paymentId);
        PaymentResponseDto response = restMapper.toResponse(paymentDto);
        return ResponseEntity.ok(response);
    }
}
