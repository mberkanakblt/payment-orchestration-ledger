package com.mehmetberkan.paycore.api.controller;

import com.mehmetberkan.paycore.api.dto.command.CreateAccountCommand;
import com.mehmetberkan.paycore.api.dto.request.CreateAccountRequest;
import com.mehmetberkan.paycore.api.dto.response.AccountDto;
import com.mehmetberkan.paycore.api.dto.response.AccountResponseDto;
import com.mehmetberkan.paycore.api.mapper.AccountRestMapper;
import com.mehmetberkan.paycore.application.port.in.AccountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dev/v1/accounts")
@CrossOrigin("*")
public class AccountController {
    private final AccountUseCase accountUseCase;
    private final AccountRestMapper restMapper;

    public AccountController(AccountUseCase accountUseCase,
                             AccountRestMapper restMapper) {
        this.accountUseCase = accountUseCase;
        this.restMapper = restMapper;
    }

    @PostMapping("/create-account")
    public ResponseEntity<AccountResponseDto> createAccount(@Validated @RequestBody CreateAccountRequest dto) {
        CreateAccountCommand command = restMapper.toCommand(dto);
        AccountDto accountDto = accountUseCase.createAccount(command);
        AccountResponseDto response = restMapper.toResponse(accountDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/get-account" + "/{id}")
    public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountUseCase.getAccountById(id);
        AccountResponseDto response = restMapper.toResponse(accountDto);
        return ResponseEntity.ok(response);
    }

}
