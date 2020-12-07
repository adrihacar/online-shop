package com.onlineshop.product.api.service;

import com.onlineshop.product.api.repository.Product;
import com.onlineshop.product.api.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    @Override
    public List<Product> searchProductCategory(String q, Integer category) {
        List<Product> productAux;
        if(category==null) productAux = productDAO.findAll();
        else productAux = productDAO.findAllByCategory(category);
        List<Product> productsToReturn = new ArrayList<>();
        for (Product aux : productAux) {
            if (aux.getName().toLowerCase(Locale.ROOT).contains(q.toLowerCase(Locale.ROOT)) ||
                    aux.getDescription().toLowerCase(Locale.ROOT).contains(q.toLowerCase(Locale.ROOT))) {
                productsToReturn.add(aux);
            }
        }
        return productsToReturn;
    }
}
