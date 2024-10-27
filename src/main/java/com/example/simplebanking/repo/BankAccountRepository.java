package com.example.simplebanking.repo;


import com.example.simplebanking.model.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    Optional<BankAccountEntity> findByAccountNumber(String accountNumber);
}
