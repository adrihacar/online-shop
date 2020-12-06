package com.product.api.service;

import com.product.api.repository.Product;

import java.util.List;

public interface ProductService {

    void addProduct(int seller, String name, int category, String description, byte[] image, double price);
    void editProduct(int id, int seller, String name, int category, String description, byte[] image, double price);
    void setSold(int id);
    List<Product> searchProductCategory(String q, Integer category);

}
