package com.onlineshop.user.api.controller;

import com.onlineshop.user.api.repository.User;
import com.onlineshop.user.api.repository.UserDAO;
import com.onlineshop.user.api.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    public UserDAO userDAO;

    @Autowired
    public UserServiceImpl userService;

    @RequestMapping(value="/users/register", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> registerUser(
            @RequestBody User user){
        if(userDAO.existsByEmail(user.getEmail())) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.registerUser(user.getName(), user.getSurname(), user.getEmail(), user.getLocation(), user.getPassword());
        return new ResponseEntity<>(true, HttpStatus.CREATED);
    }

    @RequestMapping(value="/users/login", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Boolean> logInUser(
            @RequestBody User user){
        if(!userDAO.existsByEmail(user.getEmail())) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.logInUser(user.getEmail(), user.getPassword());
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value="/users/{idUser}", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Boolean> deleteUser(
            @PathVariable int idUser){
        if (!userDAO.existsById(idUser)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userDAO.deleteById(idUser);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/users/{idUser}", method = RequestMethod.PUT)
    public @ResponseBody ResponseEntity<Boolean> editUser(
            @PathVariable int idUser,
            @RequestBody User user){
        if (!userDAO.existsById(idUser)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        userService.editUser(idUser, user.getName(), user.getSurname(), user.getLocation(), user.getPassword());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/users", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<User>> listUsers(){
        return new ResponseEntity<>(userDAO.findAll(), HttpStatus.OK);
    }



}
