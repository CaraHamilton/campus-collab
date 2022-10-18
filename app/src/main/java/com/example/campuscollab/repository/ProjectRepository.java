package com.example.campuscollab.repository;

import com.example.campuscollab.domain.Project;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProjectRepository {

    private final FirebaseFirestore db;
    private final String projectsKey = "project";
    private final String ownerKey = "ownerId";
    private final String participantsKey = "participantIds";

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentSnapshot> getProject(String projectId) {
        return db.collection(projectsKey).document(projectId).get();
    }

    public Task<Void> createProject(Project project) {
        String docId = db.collection(projectsKey).document().getId();
        project.setProjectId(docId);
        return db.collection(projectsKey).document(docId).set(project);
    }

    public Task<Void> updateProject(Project project) {
        return db.collection(projectsKey).document(project.getProjectId()).set(project);
    }

    public Task<QuerySnapshot> getAllProjects() {
        return db.collection(projectsKey).get();
    }

    public Task<QuerySnapshot> getProjectsByOwner(String userId) {
        return db.collection(projectsKey).whereEqualTo(ownerKey, userId).get();
    }

    public Task<QuerySnapshot> getProjectsByParticipant(String userId) {
        return db.collection(projectsKey).whereArrayContains(participantsKey, userId).get();
    }


    public Task<QuerySnapshot> getProjectBySchool(String school) {
        throw new RuntimeException("getProjectsBySchool not implemented");
    }

    public Task<QuerySnapshot> deleteProject(String projectId) {
        throw new RuntimeException("deleteProject not implemented");
    }

}
