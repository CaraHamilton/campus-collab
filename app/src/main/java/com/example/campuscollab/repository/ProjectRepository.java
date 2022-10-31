package com.example.campuscollab.repository;

import com.example.campuscollab.domain.Project;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProjectRepository {

    private final FirebaseFirestore db;
    private final String PROJECT_KEY = "project";
    private final String OWNER_KEY = "ownerId";
    private final String PARTICIPANTS_KEY = "participantIds";

    public ProjectRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentSnapshot> getProject(String projectId) {
        return db.collection(PROJECT_KEY).document(projectId).get();
    }

    public Task<Void> createProject(Project project) {
        String docId = db.collection(PROJECT_KEY).document().getId();
        project.setProjectId(docId);
        return db.collection(PROJECT_KEY).document(docId).set(project);
    }

    public Task<Void> updateProject(Project project) {
        return db.collection(PROJECT_KEY).document(project.getProjectId()).set(project);
    }

    public Task<QuerySnapshot> getAllProjects() {
        return db.collection(PROJECT_KEY).get();
    }

    public Task<QuerySnapshot> getProjectsByOwner(String userId) {
        return db.collection(PROJECT_KEY).whereEqualTo(OWNER_KEY, userId).get();
    }

    public Task<QuerySnapshot> getProjectsByParticipant(String userId) {
        return db.collection(PROJECT_KEY).whereArrayContains(PARTICIPANTS_KEY, userId).get();
    }


    public Task<QuerySnapshot> getProjectBySchool(String school) {
        throw new RuntimeException("getProjectsBySchool not implemented");
    }

    public Task<QuerySnapshot> deleteProject(String projectId) {
        throw new RuntimeException("deleteProject not implemented");
    }

}
