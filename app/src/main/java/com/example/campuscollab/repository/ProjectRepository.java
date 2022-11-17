package com.example.campuscollab.repository;

import com.example.campuscollab.domain.Project;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

public class ProjectRepository {

    private final FirebaseFirestore db;
    private final String PROJECT_KEY = "project";
    private final String OWNER_KEY = "ownerId";
    private final String PARTICIPANTS_KEY = "participantIds";
    private final String SCHOOL_KEY = "school";

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

    public Task<QuerySnapshot> getProjectsBySchool(String school) {
        return db.collection(PROJECT_KEY).whereEqualTo(SCHOOL_KEY, school).get();
    }

    public Task<Void> deleteProject(String projectId) {
        return db.collection(PROJECT_KEY).document(projectId).delete();
    }
}
