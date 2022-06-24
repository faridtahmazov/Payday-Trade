package com.expressbank.model;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Data
@Getter
@Builder
public class Credential {
    private String email;
    @NotBlank(message = "Password is mandatory!")
    private String password;
}
