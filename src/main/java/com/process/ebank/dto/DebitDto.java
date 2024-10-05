package com.process.ebank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DebitDto {
    private String accountId;
    private double amount;
    private String description;
}
