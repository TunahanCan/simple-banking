package com.example.simplebanking.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    protected double amount;
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccountEntity bankAccount;

    private String approvalCode;
    private LocalDateTime date;
    private String operator;
    private String phoneNumber;

    public TransactionEntity(double amount, BankAccountEntity bankAccount, String approvalCode) {
        this.amount = amount;
        this.bankAccount = bankAccount;
        this.date = LocalDateTime.now();
        this.approvalCode = approvalCode;
    }

    public TransactionEntity(double amount, BankAccountEntity bankAccount, String approvalCode, String operator, String phoneNumber) {
        this.amount = amount;
        this.bankAccount = bankAccount;
        this.approvalCode = approvalCode;
        this.date = LocalDateTime.now();
        this.operator = operator;
        this.phoneNumber = phoneNumber;
    }
}
