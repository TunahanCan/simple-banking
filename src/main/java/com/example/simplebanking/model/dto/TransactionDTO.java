package com.example.simplebanking.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private LocalDateTime date;
    private double amount;
    private String type;
    private String approvalCode;
    private String operator;
    private String phoneNumber;
}
