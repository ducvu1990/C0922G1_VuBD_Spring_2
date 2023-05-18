package com.example.kami_spa_be.repository;

import com.example.kami_spa_be.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT " +
            "product.id, product.description, product.name, product.price, product.product_quantity, product.release_date, product.brands_id, product.category_id " +
            "FROM `product` " +
            "JOIN `category` ON product.category_id = category.id " +
            "JOIN `brands` ON product.brands_id = brands.id " +
            "WHERE category.name like %?1% " +
            "AND brands.name like %?2% " +
            "AND product.name like %?3% " +
            "ORDER BY release_date desc", nativeQuery = true)
    Page<Product> findAllByProductNameAndBrandsAndCategory(String category, String brands, String name, Pageable pageable);
    @Query(value = "SELECT " +
            "product.id, product.description, product.name, product.price, product.product_quantity, product.release_date, product.brands_id, product.category_id " +
            "FROM `product` " +
            "JOIN `category` ON product.category_id = category.id " +
            "JOIN `brands` ON product.brands_id = brands.id " +
            "WHERE category.name like %?1% " +
            "AND brands.name like %?2% " +
            "AND product.name like %?3% " +
            "AND product.price >= ?4 " +
            "ORDER BY price desc", nativeQuery = true)
    Page<Product> findByMinPrice(String category, String brands, String name, Double MinPrice,Pageable pageable);
    @Query(value = "SELECT " +
            "product.id, product.description, product.name, product.price, product.product_quantity, product.release_date, product.brands_id, product.category_id " +
            "FROM `product` " +
            "JOIN `category` ON product.category_id = category.id " +
            "JOIN `brands` ON product.brands_id = brands.id " +
            "WHERE category.name like %?1% " +
            "AND brands.name like %?2% " +
            "AND product.name like %?3% " +
            "AND product.price <= ?4 " +
            "ORDER BY price asc", nativeQuery = true)
    Page<Product> findByMaxPrice(String category, String brands, String name, Double MaxPrice,Pageable pageable);
    @Query(value = "SELECT " +
            "product.id, product.description, product.name, product.price, product.product_quantity, product.release_date, product.brands_id, product.category_id " +
            "FROM `product` " +
            "JOIN `category` ON product.category_id = category.id " +
            "JOIN `brands` ON product.brands_id = brands.id " +
            "WHERE category.name like %?1% " +
            "AND brands.name like %?2% " +
            "AND product.name like %?3% " +
            "AND product.price BETWEEN ?4 AND ?5 " +
            "ORDER BY price desc", nativeQuery = true)
    Page<Product> findByMinPriceToMaxPrice(String category, String brands, String name, Double MinPrice, Double MaxPrice,Pageable pageable);
}
