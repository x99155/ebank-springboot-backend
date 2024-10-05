package com.process.ebank.service.impl;

import com.process.ebank.dto.BankAccountDto;
import com.process.ebank.dto.CurrentAccountDto;
import com.process.ebank.dto.SavingAccountDto;
import com.process.ebank.entity.BankAccount;
import com.process.ebank.entity.CurrentAccount;
import com.process.ebank.entity.Customer;
import com.process.ebank.entity.SavingAccount;
import com.process.ebank.exception.BankAccountNotFoundException;
import com.process.ebank.exception.CustomerNotFoundException;
import com.process.ebank.mapper.CurrentAccountMapper;
import com.process.ebank.mapper.SavingAccountMapper;
import com.process.ebank.repository.BankAccountRepository;
import com.process.ebank.repository.CustomerRepository;
import com.process.ebank.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final CurrentAccountMapper currentAccountMapper;
    private final SavingAccountMapper savingAccountMapper;

    @Override
    public CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) {
        CurrentAccount currentAccount = new CurrentAccount();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        CurrentAccount savedCurrentAccount = bankAccountRepository.save(currentAccount);

        return currentAccountMapper.toCurrentAccount(savedCurrentAccount);
    }

    @Override
    public SavingAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) {
        SavingAccount savingAccount = new SavingAccount();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));


        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        SavingAccount savedSavingAccount = bankAccountRepository.save(savingAccount);

        return savingAccountMapper.toSavingAccount(savedSavingAccount);
    }

    @Override
    public BankAccountDto getBankAccount(String accountId) {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank Account not found"));

        if (bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return savingAccountMapper.toSavingAccount(savingAccount);
        } else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return currentAccountMapper.toCurrentAccount(currentAccount);
        }
        //return bankAccount;
    }

    @Override
    public List<BankAccountDto> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDto> bankAccountDtos = bankAccounts.stream()
                .map(bankAccount -> {
                    if (bankAccount instanceof SavingAccount) {
                        SavingAccount savingAccount = (SavingAccount) bankAccount;
                        return savingAccountMapper.toSavingAccount(savingAccount);
                    } else {
                        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                        return currentAccountMapper.toCurrentAccount(currentAccount);
                    }
                }).collect(Collectors.toList());

        return bankAccountDtos;
    }
}
