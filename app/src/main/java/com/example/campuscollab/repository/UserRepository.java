package com.example.campuscollab.repository;

import com.example.campuscollab.domain.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class UserRepository {

    private final FirebaseFirestore db;
    private final String USER_KEY = "user";

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> registerUser(User user) {
        return db.collection(USER_KEY).document(user.getId()).set(user);
    }

    public Task<DocumentSnapshot> getUser(String userId) {
        return db.collection(USER_KEY).document(userId).get();
    }

    public Task<Void> updateUser(User user) {
        return db.collection(USER_KEY).document(user.getId()).set(user, SetOptions.merge());
    }

    public Task<Void> deleteUser(String userId) {
        throw new RuntimeException("deleteUser not implemented");
    }
}
