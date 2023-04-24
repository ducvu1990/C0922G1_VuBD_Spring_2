package com.example.kami_spa_be.repository;

import com.example.kami_spa_be.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
}
