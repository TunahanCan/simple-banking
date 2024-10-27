package com.example.simplebanking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SimpleBankingApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
    @Test
    @DisplayName("i implemented the base application")
    public void testTransactions() {
        BankAccount account = new BankAccount("Jim", "12345", 0, new ArrayList<>());
        account.post(new DepositTransaction(1000));
        account.post(new WithdrawalTransaction(200));
        account.post(new PhoneBillPaymentTransaction("Vodafone", "5423345566", 96.50));
        Assertions.assertEquals(703.50, account.getBalance(), 0.0001);
    }*/

}
