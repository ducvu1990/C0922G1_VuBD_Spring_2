package com.example.kami_spa_be.service.impl;

import com.example.kami_spa_be.model.Images;
import com.example.kami_spa_be.repository.IImagesRepository;
import com.example.kami_spa_be.service.IImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService implements IImagesService {
    @Autowired
    private IImagesRepository iImagesRepository;
    @Override
    public List<Images> findAll() {
        return this.iImagesRepository.findAll();
    }
}
