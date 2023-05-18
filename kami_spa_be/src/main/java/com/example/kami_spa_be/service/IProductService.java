package com.example.kami_spa_be.service;

import com.example.kami_spa_be.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<Product> findAllProduct(String brands,
                                 String category,
                                 String name,
                                 Double MinPrice,
                                 Double MaxPrice,
                                 Pageable pageable);

    Product findProduct(Long id);

    boolean update(Long id, int quantity);
}
