package com.example.campuscollab.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class ImageRepository {

    private final FirebaseStorage storage;
    //Maximum amount of bytes to download
    private final long ONE_MEGABYTE = 1024 * 1024;

    public ImageRepository() {
        storage = FirebaseStorage.getInstance();
    }

    public Task<byte[]> getImageBytes(String imagePath) {
        return storage.getReference().child(imagePath).getBytes(ONE_MEGABYTE);
    }

    public UploadTask uploadImageBytes(String imagePath, byte[] imageBytes) {
        return storage.getReference().child(imagePath).putBytes(imageBytes);
    }

}
