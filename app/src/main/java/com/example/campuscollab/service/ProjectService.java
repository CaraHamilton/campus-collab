package com.example.campuscollab.service;

import com.example.campuscollab.repository.ProjectRepository;

public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService() {
        projectRepository = new ProjectRepository();
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
