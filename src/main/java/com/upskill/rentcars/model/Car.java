package com.upskill.rentcars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Car {
    private int id;
    private String model;
    private String color;
    private int year;
    private String costPerDay;
}
