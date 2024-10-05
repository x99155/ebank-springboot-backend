package com.process.ebank.dto;

import com.process.ebank.enums.OperationType;
import lombok.*;

import java.util.Date;

@Getter @Setter
public class AccountOperationDto {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;

}
