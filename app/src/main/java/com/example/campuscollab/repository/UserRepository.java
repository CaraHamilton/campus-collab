package com.example.campuscollab.repository;

import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private final FirebaseFirestore db;

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
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
