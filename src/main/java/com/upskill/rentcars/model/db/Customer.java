package com.upskill.rentcars.model.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Customer {

    public Customer(String firstName, String lastName, String driverLicense, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.driverLicense = driverLicense;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String driverLicense;
    private String email;
}
