package com.product.api.controller;

import com.product.api.repository.Product;
import com.product.api.repository.ProductDAO;
import com.product.api.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class productController {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductServiceImpl productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Product>> getProductsByStatus(
            @RequestParam(value="status", required = false) Integer status){
        if(status != null)
            return new ResponseEntity<>(productDAO.findAllByStatus(status), HttpStatus.OK);
        else
            return new ResponseEntity<>(productDAO.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/search", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Product>> searchByNameAndCategory(
            @RequestParam(value="q", required = false) String q,
            @RequestParam(value="category", required = false) Integer category){
            return new ResponseEntity<>(productService.searchProductCategory(q, category), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{idSeller}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Product>> getProductsSeller(
            @PathVariable int idSeller, @RequestParam(value="status") int status){
        return new ResponseEntity<>(productDAO.findAllBySellerAndStatus(idSeller, status), HttpStatus.OK);
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<List<Product>> createProduct(@RequestBody Product product){
        productService.addProduct(
                product.getSeller(), product.getName(), product.getCategory(),
                product.getDescription(), product.getImage(), product.getPrice()
        );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products/{idProduct}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<List<Product>> editProduct(@PathVariable int idProduct,
                                                                   @RequestBody Product product){
        productService.editProduct(
                idProduct, product.getSeller(), product.getName(), product.getCategory(),
                product.getDescription(), product.getImage(), product.getPrice()
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{idProduct}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<List<Product>> editProduct(
            @PathVariable int idProduct){
        productDAO.delete(productDAO.findById(idProduct));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
