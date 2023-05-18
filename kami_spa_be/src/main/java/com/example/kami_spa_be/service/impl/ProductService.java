package com.example.kami_spa_be.service.impl;

import com.example.kami_spa_be.model.Product;
import com.example.kami_spa_be.repository.IProductRepository;
import com.example.kami_spa_be.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository iProductRepository;

    @Override
    public Page<Product> findAllProduct(String category,
                                            String brands,
                                            String name,
                                            Double minPrice,
                                            Double maxPrice,
                                            Pageable pageable) {
        if (maxPrice == 0 && minPrice == 0) {
            return this.iProductRepository.findAllByProductNameAndBrandsAndCategory(category, brands, name, pageable);
        }
        if (maxPrice == 0) {
            return this.iProductRepository.findByMinPrice(category, brands, name, minPrice, pageable);
        }
        if (minPrice == 0) {
            return this.iProductRepository.findByMaxPrice(category, brands, name, maxPrice, pageable);
        }
        return this.iProductRepository.findByMinPriceToMaxPrice(category, brands, name, minPrice, maxPrice, pageable);
    }

    @Override
    public Product findProduct(Long id) {
        return this.iProductRepository.findById(id).get();
    }

    @Override
    public boolean update(Long id, int quantity) {
        Product product = this.iProductRepository.findById(id).get();
        if (product == null){
            return false;
        }
        int quantitySet = product.getProductQuantity() - quantity;
        if (quantitySet <0){
            return false;
        }
        product.setProductQuantity(quantitySet);
        this.iProductRepository.save(product);
        return true;
    }
}
