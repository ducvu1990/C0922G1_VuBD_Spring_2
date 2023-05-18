package com.example.kami_spa_be.controller;

import com.example.kami_spa_be.model.Images;
import com.example.kami_spa_be.model.Product;
import com.example.kami_spa_be.service.IImagesService;
import com.example.kami_spa_be.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/public/1")
public class ProductRestController {
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IImagesService iImagesService;
    @GetMapping("/list-product")
    public ResponseEntity<Page<Product>> getAllByDataSearch(@RequestParam(defaultValue = "", required = false) String brands,
                                                            @RequestParam(defaultValue = "", required = false) String category,
                                                            @RequestParam(defaultValue = "", required = false) String name,
                                                            @RequestParam(defaultValue = "0", required = false) Double minPrice,
                                                            @RequestParam(defaultValue = "0", required = false) Double maxPrice,
                                                            @RequestParam(defaultValue = "0", required = false) int page,
                                                            @RequestParam(defaultValue = "0", required = false) int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = this.iProductService.findAllProduct(category, brands, name, minPrice, maxPrice, pageable);
        if (productPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }
    @GetMapping("/images")
    public ResponseEntity<List<Images>> getAllImage(){
        List<Images> images = this.iImagesService.findAll();
        if (images.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    @GetMapping("/detail")
    public ResponseEntity<Product> getProduct(@RequestParam(defaultValue = "0") Long id){
        Product product = this.iProductService.findProduct(id);
        if (product == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
