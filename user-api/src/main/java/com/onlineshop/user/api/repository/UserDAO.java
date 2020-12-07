package com.onlineshop.user.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByEmailAndPassword(String email, String password);
    User findUserById(int id);
    List<User> findAll();
}
