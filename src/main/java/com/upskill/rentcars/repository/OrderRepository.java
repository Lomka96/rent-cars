package com.upskill.rentcars.repository;

import com.upskill.rentcars.model.db.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    void deleteByCarId(Long carId);
    void deleteByCustomerId(Long id);

}
