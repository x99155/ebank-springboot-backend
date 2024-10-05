package com.process.ebank.mapper;

import com.process.ebank.dto.CustomerDto;
import com.process.ebank.dto.SavingAccountDto;
import com.process.ebank.entity.SavingAccount;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SavingAccountMapper {

    /*
        On utilise BeanUtils.copyProperties pour copier les propriétés entre CurrentAccount et CurrentAccountDto,
        mais cela ne gère pas automatiquement la conversion des objets liés, comme Customer.

        Raison pour laquelle je le fait par la suite en mappant l'objet Customer associé au CurrentAccount
        vers un objet CustomerDto à l'aide de votre CustomerMapper.
     */

    private CustomerMapper customerMapper;

    public SavingAccountDto toSavingAccount(SavingAccount savingAccount) {
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount, savingAccountDto);
        savingAccountDto.setCustomerDto(customerMapper.toCustomer(savingAccount.getCustomer()));
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());

        return savingAccountDto;
    }

    public SavingAccount toSavingAccountDto(SavingAccountDto savingAccountDto) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto, savingAccount);
        savingAccount.setCustomer(customerMapper.toCustomerDto(savingAccountDto.getCustomerDto()));

        return  savingAccount;
    }
}
