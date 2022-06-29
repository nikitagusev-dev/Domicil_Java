package com.nsu.domicil.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@ToString
@NoArgsConstructor
@Entity(name = "transactions")
public class TransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionTypeId;

    @Column(name = "transaction_type")
    private String transactionType;

    public TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
