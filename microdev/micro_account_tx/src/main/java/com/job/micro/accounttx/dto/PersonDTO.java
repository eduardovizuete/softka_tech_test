package com.job.micro.accounttx.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "PersonDTO model information")
public class PersonDTO implements Serializable {

    @Schema(description = "Person id")
    private Long id;

    @Schema(description = "Person name")
    private String name;

    @Schema(description = "Person gender")
    private String gender;

    @Schema(description = "Person age")
    private int age;

    @Schema(description = "Person identification")
    private String identification;

    @Schema(description = "Person address")
    private String address;

    @Schema(description = "Person telephone")
    private String telephone;

}
