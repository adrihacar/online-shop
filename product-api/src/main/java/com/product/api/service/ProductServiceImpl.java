package com.product.api.service;

import com.product.api.repository.Product;
import com.product.api.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    public final int SOLD = 1;

    @Autowired
    ProductDAO productDAO;

    @Override
    public void addProduct(int seller, String name, int category, String description, byte[] image, double price) {
        Product product = new Product();
        product.setName(name);
        product.setSeller(seller);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);
        product.setImage(image);

        productDAO.save(product);
    }

    @Override
    public void editProduct(int id, int seller, String name, int category, String description, byte[] image, double price) {
        Product product = productDAO.findById(id);

        product.setName(name);
        product.setSeller(seller);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategory(category);
        product.setImage(image);

        productDAO.save(product);
    }

    @Override
    public void setSold(int id) {
        Product product = productDAO.findById(id);
        product.setStatus(SOLD);
        productDAO.save(product);
    }
}
