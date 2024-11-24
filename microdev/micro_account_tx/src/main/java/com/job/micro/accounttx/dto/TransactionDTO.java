package com.job.micro.accounttx.dto;

import com.job.micro.accounttx.entity.enumeration.TypeTx;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "TransactionDTO model information")
public class TransactionDTO {

    @Schema(description = "Transaction id")
    private Long id;

    @Schema(description = "Transaction date")
    private Instant date;

    @Schema(description = "Transaction type")
    private TypeTx type;

    @Schema(description = "Transaction amount")
    private Double amount;

    @Schema(description = "Transaction before balance")
    private Double balanceBeforeTx;

    @Schema(description = "Transaction current balance")
    private Double balance;

    @Schema(description = "Transaction account id")
    private Long accountId;

}
