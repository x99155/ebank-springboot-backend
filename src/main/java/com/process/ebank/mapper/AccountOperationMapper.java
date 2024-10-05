package com.process.ebank.mapper;

import com.process.ebank.dto.AccountOperationDto;
import com.process.ebank.entity.AccountOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountOperationMapper {

    // sera retourné au client
    public AccountOperationDto toAccountOperation(AccountOperation accountOperation) {
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation, accountOperationDto);

        return accountOperationDto;
    }

    // Provient du client
    public AccountOperation toAccountOperationDto(AccountOperationDto accountOperationDto) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDto, accountOperation);

        return  accountOperation;
    }
}
