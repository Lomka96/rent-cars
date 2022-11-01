package com.upskill.rentcars.repository;

import com.upskill.rentcars.model.db.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    void deleteByCarId(Long carId);
    void deleteByCustomerId(Long id);

}
