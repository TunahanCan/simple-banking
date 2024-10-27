package com.example.simplebanking.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PhoneBillPaymentTransactionRequestDTO extends TransactionRequestDTO {
    private final String phoneNumber;
    private final String operator;

    public PhoneBillPaymentTransactionRequestDTO(double amount, String phoneNumber, String operator) {
        super(amount);
        this.phoneNumber = phoneNumber;
        this.operator = operator;
    }
}
