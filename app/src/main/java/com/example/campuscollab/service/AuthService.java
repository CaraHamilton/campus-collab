package com.example.campuscollab.service;

import com.google.firebase.auth.FirebaseAuth;

public class AuthService {

    private final FirebaseAuth auth;

    public AuthService() {
        auth = FirebaseAuth.getInstance();
    }

    public void signIn(String email, String password) {
        throw new RuntimeException("sign in not implemented");
    }

    public void signOut() {
        throw new RuntimeException("sign out not implemented");
    }

    public void createUser(String email, String password) {
        throw new RuntimeException("create user not implemented");
    }

    public void deleteUser(String userId) {
        throw new RuntimeException("deletUser not implemented");
    }
}
