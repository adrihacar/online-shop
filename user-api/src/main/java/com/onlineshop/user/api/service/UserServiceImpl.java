package com.onlineshop.user.api.service;

import com.onlineshop.user.api.repository.User;
import com.onlineshop.user.api.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean isAdmin(int id) {
        return userDAO.findUserById(id).getAdmin() == 1;
    }

    @Override
    public boolean logInUser(String email, String password) {
        if(!userDAO.existsByEmail(email)) return false;
        String salt = getSaltFromEmail(email);
        String hashedPassword = User.getSHA256Hash(password, salt);
        User user = userDAO.findUserByEmail(email);
        return user.getEmail().equals(email) && user.getPassword().equals(hashedPassword);
    }

    @Override
    public boolean registerUser(String name, String surname, String email, String location, String password) {
        if(userDAO.existsByEmail(email)) return false;
        User user = new User(name, surname, email, location, password);
        userDAO.save(user);
        return true;
    }

    @Override
    public boolean editUser(int id, String name, String surname, String location, String password) {
        if(!userDAO.existsById(id)) return false;
        User user = userDAO.findUserById(id);
        user.setName(name);
        user.setSurname(surname);
        user.setLocation(location);
        user.setPassword(User.getSHA256Hash(password, user.getSalt()));
        userDAO.save(user);
        return true;
    }

    private String getSaltFromEmail(String email){
        return userDAO.findUserByEmail(email).getSalt();
    }

}
