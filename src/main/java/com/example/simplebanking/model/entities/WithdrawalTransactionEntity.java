package com.example.simplebanking.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("WithdrawalTransaction")
@NoArgsConstructor
public class WithdrawalTransactionEntity extends TransactionEntity {
    public WithdrawalTransactionEntity(double amount, BankAccountEntity account, String approvalCode) {
        super(amount, account, approvalCode);
    }
}
