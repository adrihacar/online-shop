package com.product.api.service;

import com.product.api.repository.Product;

public interface ProductService {

    void addProduct(int seller, String name, int category, String description, byte[] image, double price);
    void editProduct(int id, int seller, String name, int category, String description, byte[] image, double price);
    void setSold(int id);

}
