package com.example.kami_spa_be.repository;

import com.example.kami_spa_be.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImagesRepository extends JpaRepository<Images, Long> {
}
