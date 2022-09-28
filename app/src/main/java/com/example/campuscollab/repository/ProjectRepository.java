package com.example.campuscollab.repository;

import com.google.firebase.firestore.FirebaseFirestore;

public class ProjectRepository {

    private final FirebaseFirestore db;

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void getProject(String projectId) {
        throw new RuntimeException("getProject not implemented");
    }

    public void createProject() {
        throw new RuntimeException("createProject not implemented");
    }

    public void updateProject() {
        throw new RuntimeException("updateProject not implemented");
    }

    public void getAllProjects() {
        throw new RuntimeException("getAllProjects not implemented");
    }

    public void getProjectsByOwner(String userId) {
        throw new RuntimeException("getProjectsByOwner not implemented");
    }

    public void getProjectsByParticipant(String userId) {
        throw new RuntimeException("getProjectsByParticipant not implemented");
    }

}
