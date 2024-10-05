package com.process.ebank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Utilisé pour renvoyer les informations d'un client avec ses comptes bancaires.
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
}
