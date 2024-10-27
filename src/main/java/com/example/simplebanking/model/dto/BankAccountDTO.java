package com.example.simplebanking.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BankAccountDTO(
        String accountNumber,
        String owner,
        double balance,
        LocalDateTime createDate,
        List<TransactionDTO> transactions
) {}
