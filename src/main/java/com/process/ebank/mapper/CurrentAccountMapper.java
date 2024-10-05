package com.process.ebank.mapper;

import com.process.ebank.dto.CurrentAccountDto;
import com.process.ebank.entity.CurrentAccount;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CurrentAccountMapper {

    /*
        On utilise BeanUtils.copyProperties pour copier les propriétés entre CurrentAccount et CurrentAccountDto,
        mais cela ne gère pas automatiquement la conversion des objets liés, comme Customer.

        Raison pour laquelle je le fait par la suite en mappant l'objet Customer associé au CurrentAccount
        vers un objet CustomerDto à l'aide de votre CustomerMapper.
     */

    private CustomerMapper customerMapper;

    public CurrentAccountDto toCurrentAccount(CurrentAccount currentAccount) {
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount, currentAccountDto);
        currentAccountDto.setCustomerDto(customerMapper.toCustomer(currentAccount.getCustomer())); // ici
        currentAccountDto.setType(currentAccount.getClass().getSimpleName());

        return currentAccountDto;
    }

    public CurrentAccount toCurrentAccountDto(CurrentAccountDto currentAccountDto) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto, currentAccount);
        currentAccount.setCustomer(customerMapper.toCustomerDto(currentAccountDto.getCustomerDto()));

        return  currentAccount;
    }
}
