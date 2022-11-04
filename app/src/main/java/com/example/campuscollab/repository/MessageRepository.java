package com.example.campuscollab.repository;

import com.example.campuscollab.domain.Message;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MessageRepository {

    private final FirebaseFirestore db;
    private final String MESSAGE_KEY = "message";
    private final String SENDER_KEY = "sender";
    private final String RECEIVER_KEY = "receiver";

    public MessageRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> createMessage(Message message) {
        String id = db.collection(MESSAGE_KEY).document().getId();
        message.setId(id);
        return db.collection(MESSAGE_KEY).document(id).set(message);
    }

    public Task<QuerySnapshot> getMessages(String senderId, String receiverId) {
        return db.collection(MESSAGE_KEY).whereEqualTo(SENDER_KEY, senderId).whereEqualTo(RECEIVER_KEY, receiverId).get();
    }

}
