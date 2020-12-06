package com.user.api.service;

public interface UserService {

    boolean isAdmin(int id);
    boolean logInUser(String email, String password);
    boolean registerUser(String name, String surname, String email, String location, String password);
    boolean editUser(int id, String name, String surname, String location, String password);
}
