package com.example.simplebanking.model.dto;

import lombok.Data;


@Data
public class TransactionRequestDTO {
    private final double amount;
}