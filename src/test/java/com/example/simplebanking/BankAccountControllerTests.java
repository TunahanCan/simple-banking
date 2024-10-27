package com.example.simplebanking;


import com.example.simplebanking.controller.BankAccountController;
import com.example.simplebanking.exceptions.InsufficientBalanceException;
import com.example.simplebanking.model.dto.BankAccountDTO;
import com.example.simplebanking.model.entities.BankAccountEntity;
import com.example.simplebanking.model.dto.TransactionRequestDTO;
import com.example.simplebanking.model.dto.TransactionResponse;
import com.example.simplebanking.repo.BankAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;


@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class BankAccountControllerTests {

    @Autowired
    private BankAccountController bankAccountController;

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @BeforeEach
    public void setup() {
        BankAccountEntity account = new BankAccountEntity();
        account.setOwner("Kerem Karaca");
        account.setAccountNumber("17892");
        account.setBalance(500.0);
        account.setTransactions(new ArrayList<>());
        account.setCreateDate(LocalDateTime.now());
        bankAccountRepository.save(account);
    }

    @AfterEach
    public void tearDown() {
        bankAccountRepository.deleteAll();
    }

    @Test
    public void givenId_Credit_thenReturnJson()
            throws Exception {
        ResponseEntity<TransactionResponse> creditResult = bankAccountController.credit("17892", new TransactionRequestDTO(1000.0));
        Assertions.assertEquals("OK", Objects.requireNonNull(creditResult.getBody()).status());
        Assertions.assertNotNull(creditResult.getBody().approvalCode());
    }


    @Test
    public void givenId_CreditAndThenDebit_thenReturnJson() throws Exception {
        ResponseEntity<TransactionResponse> creditResult = bankAccountController.credit("17892", new TransactionRequestDTO(1000.0));
        Assertions.assertEquals("OK", Objects.requireNonNull(creditResult.getBody()).status());

        ResponseEntity<TransactionResponse> debitResult = bankAccountController.debit("17892", new TransactionRequestDTO(50.0));
        Assertions.assertEquals("OK", Objects.requireNonNull(debitResult.getBody()).status());

        BankAccountEntity updatedAccount = bankAccountRepository.findByAccountNumber("17892").orElseThrow();
        Assertions.assertEquals(1450.0, updatedAccount.getBalance(), 0.001);
    }

    @Test
    public void givenId_CreditAndThenDebitMoreGetException_thenReturnJson() {
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            ResponseEntity<TransactionResponse> creditResult = bankAccountController.credit("17892", new TransactionRequestDTO(1000.0));
            Assertions.assertEquals("OK", Objects.requireNonNull(creditResult.getBody()).status());

            ResponseEntity<TransactionResponse> debitResult = bankAccountController.debit("17892", new TransactionRequestDTO(50000.0));
            Assertions.assertEquals("OK", Objects.requireNonNull(debitResult.getBody()).status());
        });
    }


    @Test
    public void givenId_GetAccount_thenReturnJson() {

        ResponseEntity<TransactionResponse> creditResult = bankAccountController.credit("17892", new TransactionRequestDTO(1000.0));
        Assertions.assertEquals("OK", Objects.requireNonNull(creditResult.getBody()).status());

        ResponseEntity<TransactionResponse> debitResult = bankAccountController.debit("17892", new TransactionRequestDTO(50.0));
        Assertions.assertEquals("OK", Objects.requireNonNull(debitResult.getBody()).status());

        ResponseEntity<BankAccountDTO> accountResult = bankAccountController.getAccountDetails("17892");
        Assertions.assertEquals("OK", Objects.requireNonNull(debitResult.getBody()).status());
        Assertions.assertEquals(1450.0, accountResult.getBody().balance(), 0.001);
        Assertions.assertEquals("17892", accountResult.getBody().accountNumber());
        Assertions.assertEquals(2, accountResult.getBody().transactions().size());
    }


}
