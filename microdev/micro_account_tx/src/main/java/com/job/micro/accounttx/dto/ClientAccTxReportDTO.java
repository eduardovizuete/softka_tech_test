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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Client Report model information")
public class ClientAccTxReportDTO {

    @Schema(description = "Report date")
    private Instant date;

    @Schema(description = "Report client name")
    private String name;

    @Schema(description = "Report account number")
    private String numberAcc;

    @Schema(description = "Report type account")
    private String typeAcc;

    @Schema(description = "Report balance before transaction")
    private Double balanceBeforeTx;

    @Schema(description = "Report type transaction")
    private TypeTx typeTx;

    @Schema(description = "Report amount transaction")
    private Double amount;

    @Schema(description = "Report current balance")
    private Double balanceTx;

    @Schema(description = "Report account status")
    private String status;

}
