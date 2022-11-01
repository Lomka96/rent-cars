package com.upskill.rentcars.repository;

import com.upskill.rentcars.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

}
