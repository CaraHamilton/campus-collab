package com.example.campuscollab.repository;

import com.google.firebase.firestore.FirebaseFirestore;

public class SchoolRepository {

    private final FirebaseFirestore db;

    public SchoolRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void getAllSchools() {
        throw new RuntimeException("getAllSchools not implemented");
    }

    public void getSchool(String id) {
        throw new RuntimeException("getSchool not implemented");
    }

}
