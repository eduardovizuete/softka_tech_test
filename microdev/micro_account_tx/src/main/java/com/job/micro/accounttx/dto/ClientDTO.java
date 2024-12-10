package com.job.micro.accounttx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Schema(description = "ClientDTO model information")
public class ClientDTO extends PersonDTO {

    @Schema(description = "Client id")
    @NotNull
    private Long clientId;

    @Schema(description = "Client password")
    @NotEmpty
    private String password;

    @Schema(description = "Client status")
    @NotEmpty
    private String status;

}
