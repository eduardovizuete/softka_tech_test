package com.job.micro.personclient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ClientDTO model information")
public class ClientDTO extends PersonDTO {

    @Schema(description = "Client id")
    private Long clientId;

    @Schema(description = "Client password")
    private String password;

    @Schema(description = "Client status")
    private String status;

}
