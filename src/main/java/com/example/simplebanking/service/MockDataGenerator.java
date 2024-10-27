package com.example.simplebanking.service;

import com.example.simplebanking.model.entities.BankAccountEntity;
import com.example.simplebanking.repo.BankAccountRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Component
@Order(100)
@RequiredArgsConstructor
public class MockDataGenerator {

    private final BankAccountRepository bankAccountRepository;

    @PostConstruct
    public void init() {
        Optional<BankAccountEntity> existingAccount = bankAccountRepository.findByAccountNumber("669-7788");
        if(existingAccount.isEmpty()){
            BankAccountEntity account = new BankAccountEntity();
            account.setOwner("Kerem Karaca");
            account.setAccountNumber("669-7788");
            account.setBalance(950.0);
            account.setTransactions(new ArrayList<>());
            account.setCreateDate(LocalDateTime.now());
            bankAccountRepository.save(account);
        }
    }

}
