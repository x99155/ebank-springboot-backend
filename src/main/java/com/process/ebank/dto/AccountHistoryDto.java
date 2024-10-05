package com.process.ebank.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountHistoryDto {
    private String accountId;
    private double balance;
    private List<AccountOperationDto> accountOperationDtos;
    // pour la pagination
    private int currentPage;
    private int totalPages;
    private int pageSize;
}
