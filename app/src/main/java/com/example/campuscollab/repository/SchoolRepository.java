package com.example.campuscollab.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class SchoolRepository {

    private final FirebaseFirestore db;

    public SchoolRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> getAllSchools() {
        throw new RuntimeException("getAllSchools not implemented");
    }

    public Task<Void> getSchool(String id) {
        throw new RuntimeException("getSchool not implemented");
    }

}
