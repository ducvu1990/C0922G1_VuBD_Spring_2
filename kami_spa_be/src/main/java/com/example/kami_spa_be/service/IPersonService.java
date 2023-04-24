package com.example.kami_spa_be.service;

import com.example.kami_spa_be.model.Person;

public interface IPersonService {
    Person findByEmail(String email);
}
