package com.expressbank.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
@JsonInclude(Include.NON_NULL)
public class RegistrationRequestDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;
}