package com.upskill.rentcars.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEditRequest {

    private long id;
    private String firstName;
    private String lastName;
    private String driverLicense;
    private String email;
    private String model;
    private String color;
    private int year;
    private String vinId;
    private int costPerDay;
    private Date startDate;
    private Date finishDate;
}
