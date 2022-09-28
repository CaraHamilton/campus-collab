package com.example.campuscollab.repository;

import com.google.firebase.firestore.FirebaseFirestore;

public class ProjectRepository {

    private final FirebaseFirestore db;

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    private void getProject(String projectId) {
        throw new RuntimeException("getProject not implemented");
    }

    private void createProject() {
        throw new RuntimeException("createProject not implemented");
    }

    private void updateProject() {
        throw new RuntimeException("updateProject not implemented");
    }

    private void getAllProjects() {
        throw new RuntimeException("getAllProjects not implemented");
    }

    private void getProjectsByOwner(String userId) {
        throw new RuntimeException("getProjectsByOwner not implemented");
    }

    private void getProjectsByParticipant(String userId) {
        throw new RuntimeException("getProjectsByParticipant not implemented");
    }

}
