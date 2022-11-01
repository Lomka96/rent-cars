package com.upskill.rentcars.model.dto;

import com.upskill.rentcars.model.db.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthData {

    private String token;
    private Role role;
}
