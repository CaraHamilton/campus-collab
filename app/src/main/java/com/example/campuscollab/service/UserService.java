package com.example.campuscollab.service;

import com.example.campuscollab.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository;
    private final AuthService auth;

    public UserService() {
        userRepository = new UserRepository();
        auth = new AuthService();
    }

    public void registerUser() {
        throw new RuntimeException("registerUser not implemented");
    }

    public void updateUser() {
        throw new RuntimeException("updateUser not implemented");
    }

    public void deleteUser() {
        throw new RuntimeException("deleteUser not implemented");
    }

}
