package com.process.ebank.service;

import com.process.ebank.dto.AccountHistoryDto;
import com.process.ebank.dto.AccountOperationDto;
import com.process.ebank.entity.AccountOperation;
import com.process.ebank.exception.BalanceNotSufficentException;
import com.process.ebank.exception.BankAccountNotFoundException;

import java.util.List;

public interface AccountOperationService {

    /*
     pour débiter ou créditer un compte, je dois connaitre le compte à débiter ou créditer,
     le montant, et je dois renseigner une description
     */
    void debit(String accountId, double amount, String description);

    void credit(String accountId, double amount, String description);

    /*
    pour faire un transfert je dois, connaitre le compte qui envoit, le montant à envoyer
    et celui qui le compte qui receptionne
     */
    void transfer(String accountIdSource, String accountIdDestination, double amount);

    List<AccountOperationDto> accountHistory(String accountId);

    //AccountHistoryDto getAccountHistory(String accountId, int page, int size);
}
