package com.example.simplebanking.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@DiscriminatorValue("DepositTransaction")
@NoArgsConstructor
public class DepositTransactionEntity extends TransactionEntity {
    public DepositTransactionEntity(double amount, BankAccountEntity account , String approvalCode) {
        super(amount, account, approvalCode);
    }

}