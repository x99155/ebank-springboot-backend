package com.process.ebank.entity;

import com.process.ebank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
public class BankAccount {
    @Id
    private String id;
    private double balance;
    @Column(name = "created_at")
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne // plusieurs comptes peuvent appartenir à un client
    private Customer customer;
    // un compte peut avoir plusieurs opérations
    @OneToMany(mappedBy = "bankAccount") // dans la classe AccountOperation, j'ai un attribut qui s'appelle 'bankAccount'
    private List<AccountOperation> accountOperations;
}
