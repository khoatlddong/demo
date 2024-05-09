package com.wofe.backend.controller;


import com.wofe.backend.dao.ProductRepository;
import com.wofe.backend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/products")
    public String createNewProduct(@RequestBody Product product){
        productRepository.save(product);
        return "Product created";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> proList = new ArrayList<>();
        productRepository.findAll().forEach(proList::add);
        return new ResponseEntity<List<Product>>(proList, HttpStatus.OK);
    }

    @GetMapping("/products/{code}")
    public ResponseEntity<Product> getProductById(@PathVariable String code){
        Optional<Product> pro = productRepository.findById(code);
        if (pro.isPresent()){
            return new ResponseEntity<Product>(pro.get(), HttpStatus.FOUND);
        } else {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/products/{code}")
    public String updateProductById(@PathVariable String code, @RequestBody Product product){
        Optional<Product> pro = productRepository.findById(code);
        if (pro.isPresent()){
            Product existPro = pro.get();
            existPro.setCode(product.getCode());
            existPro.setName(product.getName());
            existPro.setCategory(product.getCategory());
            existPro.setBrand(product.getBrand());
            existPro.setType(product.getType());
            existPro.setDescription(product.getDescription());
            existPro.setDateCreated(product.getDateCreated());
            existPro.setDateUpdated(product.getDateUpdated());
            productRepository.save(existPro);
            return "Product Details against code " + code + " updated";
        } else {
            return "Product Details does not exist for code "  + code;
        }
    }

    @DeleteMapping("/products/{code}")
    public String deleteProductById(@PathVariable String code){
        productRepository.deleteById(code);
        return "Product deleted";
    }


}
