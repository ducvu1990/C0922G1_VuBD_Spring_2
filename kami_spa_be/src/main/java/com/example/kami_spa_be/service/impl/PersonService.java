package com.example.kami_spa_be.service.impl;

import com.example.kami_spa_be.model.Person;
import com.example.kami_spa_be.repository.IPersonRepository;
import com.example.kami_spa_be.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements IPersonService {
    @Autowired
    private IPersonRepository iPersonRepository;
    @Override
    public Person findByEmail(String email) {
        return iPersonRepository.findByEmail(email);
    }
}
