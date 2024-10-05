package com.process.ebank.dto;

import com.process.ebank.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CurrentAccountDto extends BankAccountDto {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private double overDraft;
    private CustomerDto customerDto;
}
