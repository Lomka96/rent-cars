package com.upskill.rentcars.repository;

import com.upskill.rentcars.model.db.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByDriverLicense(String driverLicense);
}
