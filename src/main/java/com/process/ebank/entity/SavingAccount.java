package com.process.ebank.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Entity
@DiscriminatorValue("CA") // cette valeur sera mise dans TYPE
public class SavingAccount extends BankAccount{
    private double interestRate;
}
