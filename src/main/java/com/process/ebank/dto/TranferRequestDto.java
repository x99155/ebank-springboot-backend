package com.process.ebank.dto;

import lombok.Getter;
import lombok.Setter;

/*
...RequestDto  -> les données recues par le client
...ResponseDto -> les données retournees au client
 */
@Getter @Setter
public class TranferRequestDto {
    private String accountIdSource;
    private String accountIdDestination;
    private double amount;
}
