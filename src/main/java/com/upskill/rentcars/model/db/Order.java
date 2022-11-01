package com.upskill.rentcars.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Car car;
    private Date startDate;
    private Date finishDate;
    private double cost;
}
