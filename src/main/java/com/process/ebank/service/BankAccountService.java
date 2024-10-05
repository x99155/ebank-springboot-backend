package com.process.ebank.service;

import com.process.ebank.dto.BankAccountDto;
import com.process.ebank.dto.CurrentAccountDto;
import com.process.ebank.dto.SavingAccountDto;

import java.util.List;

public interface BankAccountService {

    // Définition des besoins fonctionnels

    /*
    Pour créé un compte bancaire, il faut connaitre le montant initiale, le type de compte
    et le client qui souhaite avoir le compte
     */
    CurrentAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId);
    SavingAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId);

    BankAccountDto getBankAccount(String accountId);

    List<BankAccountDto> bankAccountList();
}
