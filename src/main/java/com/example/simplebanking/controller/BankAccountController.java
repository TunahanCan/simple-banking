package com.example.simplebanking.controller;

import com.example.simplebanking.model.dto.BankAccountDTO;
import com.example.simplebanking.model.dto.TransactionRequestDTO;
import com.example.simplebanking.model.dto.TransactionResponse;
import com.example.simplebanking.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PostMapping("/credit/{accountNumber}")
    public ResponseEntity<TransactionResponse> credit(@PathVariable String accountNumber, @RequestBody TransactionRequestDTO request) {
        TransactionResponse response = bankAccountService.credit(accountNumber, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit/{accountNumber}")
    public ResponseEntity<TransactionResponse> debit(@PathVariable String accountNumber, @RequestBody TransactionRequestDTO request) {
        TransactionResponse response = bankAccountService.debit(accountNumber, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<BankAccountDTO> getAccountDetails(@PathVariable String accountNumber) {
        BankAccountDTO bankAccountDTO = bankAccountService.getAccountDetails(accountNumber);
        return ResponseEntity.ok(bankAccountDTO);
    }
}