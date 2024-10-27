package com.example.simplebanking.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;

    @Column(unique = true)
    private String accountNumber;
    private double balance;
    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TransactionEntity> transactions = new ArrayList<>();
    private LocalDateTime createDate;
}
