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
import java.time.LocalDateTime;

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

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTx type;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "balance_before_tx")
    private Double balanceBeforeTx;

    @Column(nullable = false)
    private Double balance;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @NotNull
    private Account account;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column
    private LocalDateTime deletedAt;

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

    public Transaction copyTx() {
        Transaction newTx = new Transaction();
        newTx.setAccount(this.getAccount());
        newTx.setAmount(this.getAmount());
        newTx.setType(this.getType());
        newTx.setBalanceBeforeTx(this.getBalanceBeforeTx());
        newTx.setBalance(this.getBalance());
        newTx.setDate(LocalDateTime.now());
        return newTx;
    }

}
