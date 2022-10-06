package com.example.campuscollab.service;

import android.os.AsyncTask;

import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.repository.ProjectRepository;

import java.util.List;

public class ProjectService {

    private static ProjectService instance;
    private final ProjectRepository projectRepository;

    public static synchronized ProjectService getInstance() {
        if(instance == null) {
            instance = new ProjectService();
        }
        return instance;
    }

    private ProjectService() {
        projectRepository = new ProjectRepository();
    }

    public AsyncTask<Void, Void, Project> getProject(String projectId) {
        throw new RuntimeException("getProject not implemented");
    }

    public AsyncTask<Void, Void, Project> createProject(Project project) {
        throw new RuntimeException("createProject not implemented");
    }

    public AsyncTask<Void, Void, Void> updateProject(Project project) {
        throw new RuntimeException("updateProject not implemented");
    }

    public AsyncTask<Void, Void, List<Project>> getAllProjects() {
        throw new RuntimeException("getAllProjects not implemented");
    }

    public AsyncTask<Void, Void, List<Project>> getProjectsByOwner(String userId) {
        throw new RuntimeException("getProjectsByOwner not implemented");
    }

    public AsyncTask<Void, Void, List<Project>> getProjectsByParticipant(String userId) {
        throw new RuntimeException("getProjectsByParticipant not implemented");
    }
}
