package com.example.campuscollab.repository;

import com.example.campuscollab.domain.Project;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProjectRepository {

    private final FirebaseFirestore db;

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentSnapshot> getProject(String projectId) {
        throw new RuntimeException("getProject not implemented");
    }

    public Task<Void> createProject(Project project) {
        throw new RuntimeException("createProject not implemented");
    }

    public Task<Void> updateProject(Project project) {
        throw new RuntimeException("updateProject not implemented");
    }

    public Task<QuerySnapshot> getAllProjects() {
        throw new RuntimeException("getAllProjects not implemented");
    }

    public Task<QuerySnapshot> getProjectsByOwner(String userId) {
        throw new RuntimeException("getProjectsByOwner not implemented");
    }

    public Task<QuerySnapshot> getProjectsByParticipant(String userId) {
        throw new RuntimeException("getProjectsByParticipant not implemented");
    }

}
