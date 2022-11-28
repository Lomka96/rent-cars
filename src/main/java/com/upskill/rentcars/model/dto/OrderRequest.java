package com.upskill.rentcars.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private String firstName;
    private String lastName;
    private String driverLicense;
    private String email;
    private Date startDate;
    private Date finishDate;
}
