package com.example.simplebanking;

import com.example.simplebanking.exceptions.InsufficientBalanceException;
import com.example.simplebanking.model.dto.BankAccountDTO;
import com.example.simplebanking.model.entities.BankAccountEntity;
import com.example.simplebanking.repo.BankAccountRepository;
import com.example.simplebanking.service.BankAccountService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;


@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
public class ModelTest {

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    BankAccountRepository accountRepository;

    @AfterEach
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test
    public void testCreateAccountAndSetBalance0() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO(
                "17892",
                "Kerem Karaca",
                500.0,
                LocalDateTime.now(),
                new ArrayList<>()
        );
        assertEquals("Kerem Karaca", bankAccountDTO.owner());
        assertEquals("17892", bankAccountDTO.accountNumber());
        assertEquals(500, bankAccountDTO.balance());
    }

    @Test
    public void testDepositIntoBankAccount() {

        BankAccountEntity account = new BankAccountEntity();
        account.setOwner("Demet Demircan");
        account.setAccountNumber("9834");
        account.setBalance(500.0);
        account.setTransactions(new ArrayList<>());
        account.setCreateDate(LocalDateTime.now());
        accountRepository.save(account);

       BankAccountDTO accountDTO =
               bankAccountService.deposit("9834", 100);

       assertEquals("Demet Demircan", accountDTO.owner());
       assertEquals("9834", accountDTO.accountNumber());
       assertEquals(600.0, accountDTO.balance());
    }


    @Test
    public void testWithdrawFromBankAccount() throws InsufficientBalanceException {
        BankAccountEntity account = new BankAccountEntity();
        account.setOwner("Demet Demircan");
        account.setAccountNumber("9834");
        account.setBalance(0);
        account.setTransactions(new ArrayList<>());
        account.setCreateDate(LocalDateTime.now());
        accountRepository.save(account);

        BankAccountDTO accountDTO = bankAccountService.deposit("9834", 100);
        assertTrue(accountDTO.balance() == 100);

        accountDTO = bankAccountService.withdraw("9834", 50);
        assertTrue(accountDTO.balance() == 50);
    }


    @Test
    public void testWithdrawException() {
        BankAccountEntity account = new BankAccountEntity();
        account.setOwner("Demet Demircan");
        account.setAccountNumber("9834");
        account.setBalance(100.0);
        account.setTransactions(new ArrayList<>());
        account.setCreateDate(LocalDateTime.now());
        accountRepository.save(account);
        Assertions.assertThrows( InsufficientBalanceException.class, () -> {
            bankAccountService.deposit("9834", 500);
            bankAccountService.withdraw("9834", 1000);
        });
    }



}
