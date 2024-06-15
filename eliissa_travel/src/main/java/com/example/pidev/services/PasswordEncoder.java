/*
        * To change this license header, choose License Headers in Project Properties.
        * To change this template file, choose Tools | Templates
        * and open the template in the editor.
        */
        package com.example.pidev.services;

        import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author don7a
 */
        public class PasswordEncoder {

        public static String encode(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
        }

  /*  public static boolean matches(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }*/
        public static boolean matches(String password, String hash) {
        // Check if the hash starts with $2y$
        if (hash.startsWith("$2y$")) {
        // Replace $2y$ with $2a$ to make it compatible with jBCrypt
        hash = hash.replaceFirst("\\$2y\\$", "\\$2a\\$");
        }
        return BCrypt.checkpw(password, hash);
        }
        }