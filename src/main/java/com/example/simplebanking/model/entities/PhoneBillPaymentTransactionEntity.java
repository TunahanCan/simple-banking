package com.example.simplebanking.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PhoneBillPaymentTransaction")
@Getter
@Setter
@NoArgsConstructor
public class PhoneBillPaymentTransactionEntity extends TransactionEntity {
    private String operator;
    private String phoneNumber;

    public PhoneBillPaymentTransactionEntity(double amount, BankAccountEntity bankAccount, String approvalCode, String operator, String phoneNumber) {
        super(amount, bankAccount, approvalCode, operator, phoneNumber);
        this.operator = operator;
        this.phoneNumber = phoneNumber;
    }

}
