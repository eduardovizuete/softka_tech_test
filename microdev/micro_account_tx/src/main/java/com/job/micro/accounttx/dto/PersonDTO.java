package com.job.micro.accounttx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "PersonDTO model information")
public class PersonDTO implements Serializable {

    @Schema(description = "Person id")
    private Long id;

    @Schema(description = "Person name")
    @NotEmpty
    private String name;

    @Schema(description = "Person gender")
    @NotEmpty
    private String gender;

    @Schema(description = "Person age")
    @Min(1)
    private int age;

    @Schema(description = "Person identification")
    @NotEmpty
    private String identification;

    @Schema(description = "Person address")
    @NotEmpty
    private String address;

    @Schema(description = "Person telephone")
    @NotEmpty
    private String telephone;

}
