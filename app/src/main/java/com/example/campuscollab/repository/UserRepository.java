package com.example.campuscollab.repository;

import com.example.campuscollab.domain.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private final FirebaseFirestore db;

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> registerUser(User user) {
        return db.collection("user").document(user.getId()).set(user);
    }

    public Task<Void> updateUser(User user) {
        throw new RuntimeException("updateUser not implemented");
    }

    public Task<Void> deleteUser(String userId) {
        throw new RuntimeException("deleteUser not implemented");
    }

}
