package com.upskill.rentcars.model.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegistrationRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String login;

    @NotEmpty
    private String password;

}
