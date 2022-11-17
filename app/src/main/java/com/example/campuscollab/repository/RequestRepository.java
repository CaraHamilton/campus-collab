package com.example.campuscollab.repository;

import com.example.campuscollab.domain.Request;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class RequestRepository {

    private final FirebaseFirestore db;
    private final String REQUEST_KEY = "request";
    private final String PROJECT_OWNER_ID_KEY = "projectOwnerId";
    private final String REQUESTER_ID_KEY = "requesterId";
    private final String PROJECT_ID_KEY = "projectId";

    public RequestRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<Void> createRequest(Request request) {
        String id = db.collection(REQUEST_KEY).document().getId();
        request.setId(id);
        return db.collection(REQUEST_KEY).document(id).set(request);
    }

    public Task<Void> updateRequest(Request request) {
        return db.collection(REQUEST_KEY).document(request.getId()).set(request);
    }

    public Task<Void> deleteRequest(String requestId) {
        return db.collection(REQUEST_KEY).document(requestId).delete();
    }

    public Task<QuerySnapshot> getRequestsByProjectOwner(String ownerId) {
        return db.collection(REQUEST_KEY).whereEqualTo(PROJECT_OWNER_ID_KEY, ownerId).get();
    }

    public Task<QuerySnapshot> getRequestsByRequester(String requesterId) {
        return db.collection(REQUEST_KEY).whereEqualTo(REQUESTER_ID_KEY, requesterId).get();
    }

    public Task<QuerySnapshot> getRequestsByUserAndProject(String userId, String projectId) {
        return db.collection(REQUEST_KEY).whereEqualTo(REQUESTER_ID_KEY, userId).whereEqualTo(PROJECT_ID_KEY, projectId).get();
    }

}
