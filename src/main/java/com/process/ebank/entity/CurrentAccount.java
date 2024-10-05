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
@DiscriminatorValue("SA") // cette valeur sera mise dans TYPE
public class CurrentAccount extends BankAccount{
    private double overDraft;
}