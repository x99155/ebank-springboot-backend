package com.process.ebank.service.impl;

import com.process.ebank.dto.AccountHistoryDto;
import com.process.ebank.dto.AccountOperationDto;
import com.process.ebank.entity.AccountOperation;
import com.process.ebank.entity.BankAccount;
import com.process.ebank.enums.OperationType;
import com.process.ebank.exception.BalanceNotSufficentException;
import com.process.ebank.exception.BankAccountNotFoundException;
import com.process.ebank.mapper.AccountOperationMapper;
import com.process.ebank.repository.AccountOperationRepository;
import com.process.ebank.repository.BankAccountRepository;
import com.process.ebank.service.AccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AccountOperationServiceImpl implements AccountOperationService {

    private final AccountOperationRepository accountOperationRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationMapper accountOperationMapper;

    @Override
    public void debit(String accountId, double amount, String description) {
        // On récupère le compte à débiter
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        // On vérifie que le solde est bien suffisant
        if (bankAccount.getBalance() < amount) {
            throw new BalanceNotSufficentException("Solde insuffisant");
        }

        // On crée une opération de débit
        AccountOperation operation = new AccountOperation();
        operation.setType(OperationType.DEBIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);

        // Enregistre l'opération
        accountOperationRepository.save(operation);

        // Mets à jour le solde
        bankAccount.setBalance(bankAccount.getBalance() - amount);

        // Enregistre l'etat du compte
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) {
        // On récupère le compte à créditer
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));


        // On crée une opération de crédit
        AccountOperation operation = new AccountOperation();
        operation.setType(OperationType.CREDIT);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setOperationDate(new Date());
        operation.setBankAccount(bankAccount);

        // Enregistre l'opération
        accountOperationRepository.save(operation);

        // Mets à jour le solde
        bankAccount.setBalance(bankAccount.getBalance() + amount);

        // Enregistre l'etat du compte
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {
        // On récupère les comptes source et de destination
        BankAccount sourceBankAccount = bankAccountRepository.findById(accountIdSource)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        BankAccount destinationBankAccount = bankAccountRepository.findById(accountIdDestination)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        try {
            debit(accountIdSource, amount, "Transfer to " + destinationBankAccount.getCustomer().getName());
        } catch (BalanceNotSufficentException e) {
            throw new BalanceNotSufficentException("Solde insuffisant");
        }

        credit(accountIdDestination, amount, "Transfer from " + sourceBankAccount.getCustomer().getName());

    }

    @Override
    public List<AccountOperationDto> accountHistory(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);

        return accountOperations.stream()
                .map(op -> accountOperationMapper.toAccountOperation(op))
                .collect(Collectors.toList());
    }

    /*
    @Override
    public AccountHistoryDto getAccountHistory(String accountId, int page, int size) {
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();

        List<AccountOperationDto> accountOperationDtos = accountOperations.getContent()
                .stream()
                .map()
        return null;
    }
     */
}
