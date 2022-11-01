package com.upskill.rentcars.repository;

import com.upskill.rentcars.model.db.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByVinId(String vinId);
}
