package com.example.simplebanking.service;

import com.example.simplebanking.model.dto.*;
import com.example.simplebanking.model.entities.*;
import com.example.simplebanking.model.enums.TransactionType;
import com.example.simplebanking.repo.BankAccountRepository;
import com.example.simplebanking.service.strategy.DepositTransactionStrategy;
import com.example.simplebanking.service.strategy.PhoneBillPaymentTransactionStrategy;
import com.example.simplebanking.service.strategy.TransactionStrategy;
import com.example.simplebanking.service.strategy.WithdrawalTransactionStrategy;
import lombok.RequiredArgsConstructor;
import org.hibernate.internal.util.StringHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final DepositTransactionStrategy depositTransactionStrategy;
    private final WithdrawalTransactionStrategy withdrawalTransactionStrategy;
    private final PhoneBillPaymentTransactionStrategy phoneBillPaymentTransactionStrategy;

    public TransactionResponse credit(String accountNumber, TransactionRequestDTO requestDto) {
        return processTransaction(accountNumber, requestDto, TransactionType.CREDIT);
    }

    public TransactionResponse debit(String accountNumber, TransactionRequestDTO requestDto) {
        return processTransaction(accountNumber, requestDto, TransactionType.DEBIT);
    }

    public BankAccountDTO deposit(String accountNumber, double amount) {
        return processTransactionWithDto(accountNumber, new TransactionRequestDTO(amount), TransactionType.CREDIT);
    }

    public BankAccountDTO withdraw(String accountNumber, double amount) {
        return processTransactionWithDto(accountNumber, new TransactionRequestDTO(amount), TransactionType.DEBIT);
    }

    public TransactionResponse payPhoneBill(String accountNumber, double amount, String phoneNumber, String operator) {
        PhoneBillPaymentTransactionRequestDTO request = new PhoneBillPaymentTransactionRequestDTO(amount, phoneNumber, operator);
        return processTransaction(accountNumber, request, TransactionType.PHONE_BILL_PAYMENT);
    }

    private BankAccountDTO processTransactionWithDto(String accountNumber, TransactionRequestDTO transactionRequestDTO, TransactionType type) {
        BankAccountEntity account = findAccountByNumber(accountNumber);
        TransactionStrategy strategy = getTransactionStrategy(type);
        BankAccountDTO updatedAccountDTO = strategy.executeTransaction(convertToDTO(account), transactionRequestDTO);
        updateAccountTransactionsAndBalance(account, updatedAccountDTO);
        return updatedAccountDTO;
    }

    private TransactionResponse processTransaction(String accountNumber, TransactionRequestDTO transactionRequestDTO, TransactionType type) {
        BankAccountEntity account = findAccountByNumber(accountNumber);
        TransactionStrategy strategy = getTransactionStrategy(type);
        BankAccountDTO updatedAccountDTO = strategy.executeTransaction(convertToDTO(account), transactionRequestDTO);
        updateAccountTransactionsAndBalance(account, updatedAccountDTO);
        return new TransactionResponse("OK", updatedAccountDTO.transactions().getLast().getApprovalCode());
    }

    private TransactionStrategy getTransactionStrategy(TransactionType type) {
        return switch (type) {
            case CREDIT -> depositTransactionStrategy;
            case DEBIT -> withdrawalTransactionStrategy;
            case PHONE_BILL_PAYMENT -> phoneBillPaymentTransactionStrategy;
            default -> throw new IllegalArgumentException("Invalid transaction type: " + type);
        };
    }

    @Transactional
    public void updateAccountTransactionsAndBalance(BankAccountEntity account, BankAccountDTO updatedAccountDTO) {
        TransactionDTO newTransactionDTO = updatedAccountDTO.transactions().getLast();
        TransactionEntity newTransaction = createTransactionFromDTO(newTransactionDTO, account);
        account.getTransactions().add(newTransaction);
        account.setBalance(updatedAccountDTO.balance());
        bankAccountRepository.save(account);
    }

    private TransactionEntity createTransactionFromDTO(TransactionDTO transactionDTO, BankAccountEntity account) {
        return switch (transactionDTO.getType()) {
            case "DepositTransaction" -> new DepositTransactionEntity(transactionDTO.getAmount(), account, transactionDTO.getApprovalCode());
            case "WithdrawalTransaction" -> new WithdrawalTransactionEntity(transactionDTO.getAmount(), account, transactionDTO.getApprovalCode());
            case "PhoneBillPaymentTransaction" -> new PhoneBillPaymentTransactionEntity(
                    transactionDTO.getAmount(),
                    account,
                    transactionDTO.getApprovalCode(),
                    transactionDTO.getOperator(),
                    transactionDTO.getPhoneNumber());
            default -> throw new IllegalArgumentException("Invalid transaction type: " + transactionDTO.getType());
        };
    }

    public BankAccountDTO getAccountDetails(String accountNumber) {
        return convertToDTO(findAccountByNumber(accountNumber));
    }

    private BankAccountEntity findAccountByNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Hesap bulunamadı."));
    }

    private String findCorrectType(String value){
        return value.substring(0 , value.length() - 6);
    }

    private BankAccountDTO convertToDTO(BankAccountEntity entity) {
        List<TransactionDTO> transactionDTOs = entity.getTransactions().stream()
                .map(transaction -> new TransactionDTO(
                        transaction.getDate(),
                        transaction.getAmount(),
                        findCorrectType(transaction.getClass().getSimpleName()),
                        transaction.getApprovalCode(),
                        transaction.getOperator(),
                        transaction.getPhoneNumber()
                ))
                .collect(Collectors.toList());
        return new BankAccountDTO(
                entity.getAccountNumber(),
                entity.getOwner(),
                entity.getBalance(),
                entity.getCreateDate(),
                transactionDTOs
        );
    }
}