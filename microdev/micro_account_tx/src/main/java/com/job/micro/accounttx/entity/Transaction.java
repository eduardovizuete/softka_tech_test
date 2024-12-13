package com.job.micro.accounttx.entity;

import com.job.micro.accounttx.entity.enumeration.TypeTx;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    private TypeTx type;

    private Double amount;

    @Column(name = "balance_before_tx")
    private Double balanceBeforeTx;

    private Double balance;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @NotNull
    private Account account;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "date = " + date + ", " +
                "type = " + type + ", " +
                "amount = " + amount + ", " +
                "balanceBeforeTx = " + balanceBeforeTx + ", " +
                "balance = " + balance + ")";
    }

}
