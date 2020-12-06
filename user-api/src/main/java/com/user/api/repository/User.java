package com.user.api.repository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    @NotNull
    private String name;

    @Column(name="surname")
    @NotNull
    private String surname;

    @Column(name="email")
    @NotNull
    private String email;

    @Column(name="location")
    @NotNull
    private String location;

    @Column(name="password")
    @NotNull
    private String password;

    @Column(name="salt")
    @NotNull
    private String salt;

    @Column(name="admin")
    private int admin;

    public User(String name, String surname, String email, String location, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.location = location;


        /*Create a random salt with class SecureRandom*/
        SecureRandom random = new SecureRandom();
        byte[] num = new byte[16];
        random.nextBytes(num);
        String salt= Base64.getEncoder().encodeToString(num); /*Encode the bytes generated in Base64 printable Strings*/

        this.setSalt(salt);
        this.password= getSHA256Hash(password,salt);

    }

    public static String getSHA256Hash(String data, String salt) {
        String result = null;
        try {
            /*Create Object digest*/
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /*Add salt to object*/
            digest.update(salt.getBytes());
            /*Add the data(password) to object*/
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            /*Get the hash and encode in Base64*/
            result = Base64.getEncoder().encodeToString(hash);


        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;

    }

}
