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
    private final FirebaseStorage storage;
    //Maximum amount of bytes to download
    private final long ONE_MEGABYTE = 1024 * 1024;
    private final String USER_KEY = "user";

    public UserRepository() {
        storage = FirebaseStorage.getInstance();
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

    public Task<byte[]> getImageBytes(String imagePath) {
        return storage.getReference().child(imagePath).getBytes(ONE_MEGABYTE);
    }

    public UploadTask uploadImageBytes(String imagePath, byte[] imageBytes) {
        return storage.getReference().child(imagePath).putBytes(imageBytes);
    }
}
