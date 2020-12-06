package com.product.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO extends CrudRepository<Product, Integer>{

    Product findById(int id);
    List<Product> findAll();
    List<Product> findAllBySeller(int seller);
    List<Product> findAllByStatus(int status);
    List<Product> findAllBySellerAndStatus(int seller, int status);
    List<Product> findAllByCategory(int category);

}
