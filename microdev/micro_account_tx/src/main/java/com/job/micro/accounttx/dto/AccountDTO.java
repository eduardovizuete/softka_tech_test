package com.job.micro.accounttx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AccountDTO model information")
public class AccountDTO {

    @Schema(description = "Account id")
    private Long id;

    @Schema(description = "Account number")
    private String number;

    @Schema(description = "Account type")
    private String type;

    @Schema(description = "Account balance")
    private Double balance;

    @Schema(description = "Account status")
    private String status;

    @Schema(description = "Account client id")
    private Long clientId;

}
