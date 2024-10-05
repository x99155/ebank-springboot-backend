package com.process.ebank.entity;

import com.process.ebank.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
@Entity
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "operation_date")
    private Date operationDate;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;
}
