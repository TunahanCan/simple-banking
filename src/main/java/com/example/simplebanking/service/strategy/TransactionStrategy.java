package com.example.simplebanking.service.strategy;

import com.example.simplebanking.model.dto.BankAccountDTO;
import com.example.simplebanking.model.dto.TransactionRequestDTO;

public interface TransactionStrategy {
    BankAccountDTO executeTransaction(BankAccountDTO accountDTO, TransactionRequestDTO transactionRequestDTO);
}
