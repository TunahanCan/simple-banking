package com.example.simplebanking.service.strategy;


import com.example.simplebanking.exceptions.InsufficientBalanceException;
import com.example.simplebanking.model.dto.BankAccountDTO;
import com.example.simplebanking.model.dto.TransactionDTO;
import com.example.simplebanking.model.dto.TransactionRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class WithdrawalTransactionStrategy implements TransactionStrategy {

    private String generateApprovalCode() {
        String code = UUID.randomUUID().toString();
        System.out.println("Generated Approval Code: " + code);
        return code;
    }

    @Override
    public BankAccountDTO executeTransaction(BankAccountDTO accountDTO, TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO.getAmount() > accountDTO.balance()) {
            throw new InsufficientBalanceException("Insufficient tBalance Exception");
        }
        double newBalance = accountDTO.balance() - transactionRequestDTO.getAmount();
        List<TransactionDTO> newTransactions = new ArrayList<>(accountDTO.transactions());

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .date(LocalDateTime.now())
                .amount(transactionRequestDTO.getAmount())
                .type("WithdrawalTransaction")
                .approvalCode(generateApprovalCode())
                .build();

        newTransactions.add(transactionDTO);
        return new BankAccountDTO(
                accountDTO.accountNumber(),
                accountDTO.owner(),
                newBalance,
                accountDTO.createDate(),
                newTransactions
        );
    }
}