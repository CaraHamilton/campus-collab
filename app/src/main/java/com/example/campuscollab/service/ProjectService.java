package com.example.campuscollab.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.campuscollab.domain.Project;
import com.example.campuscollab.repository.ProjectRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressLint("StaticFieldLeak")
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
        AsyncTask<Void, Void, Project> task = new AsyncTask<Void, Void, Project>() {
            @Override
            protected Project doInBackground(Void... voids) {
                try{
                    Task<DocumentSnapshot> getProjectDocTask = projectRepository.getProject(projectId);
                    DocumentSnapshot projectDoc = Tasks.await(getProjectDocTask);

                    return mapDocToProject(projectDoc);
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Project> createProject(Project project) {
        AsyncTask<Void, Void, Project> task = new AsyncTask<Void, Void, Project>() {
            @Override
            protected Project doInBackground(Void... voids) {
                try{
                    Task<Void> createProjectTask = projectRepository.createProject(project);
                    Tasks.await(createProjectTask);
                    return project;
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Project> updateProject(Project project) {
        AsyncTask<Void, Void, Project> task = new AsyncTask<Void, Void, Project>() {
            @Override
            protected Project doInBackground(Void... voids) {
                try{
                    project.setEditedDate(Timestamp.now());
                    Task<Void> updateProjectTask = projectRepository.updateProject(project);
                    Tasks.await(updateProjectTask);
                    return project;
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, List<Project>> getAllProjects() {
        AsyncTask<Void, Void, List<Project>> task = new AsyncTask<Void, Void, List<Project>>() {
            @Override
            protected List<Project> doInBackground(Void... voids) {
                try{
                    Task<QuerySnapshot> getProjectsTask = projectRepository.getAllProjects();
                    QuerySnapshot projectDocs = Tasks.await(getProjectsTask);

                    List<Project> projects = new ArrayList<>();
                    for(DocumentSnapshot doc: projectDocs) {
                        projects.add(mapDocToProject(doc));
                    }

                    return projects;
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, List<Project>> getProjectsByOwner(String userId) {
        AsyncTask<Void, Void, List<Project>> task = new AsyncTask<Void, Void, List<Project>>() {
            @Override
            protected List<Project> doInBackground(Void... voids) {
                try{
                    Task<QuerySnapshot> getProjectsTask = projectRepository.getProjectsByOwner(userId);
                    QuerySnapshot projectDocs = Tasks.await(getProjectsTask);

                    List<Project> projects = new ArrayList<>();
                    for(DocumentSnapshot doc: projectDocs) {
                        projects.add(mapDocToProject(doc));
                    }

                    return projects;
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, List<Project>> getProjectsByParticipant(String userId) {
        AsyncTask<Void, Void, List<Project>> task = new AsyncTask<Void, Void, List<Project>>() {
            @Override
            protected List<Project> doInBackground(Void... voids) {
                try{
                    Task<QuerySnapshot> getProjectsTask = projectRepository.getProjectsByParticipant(userId);
                    QuerySnapshot projectDocs = Tasks.await(getProjectsTask);

                    List<Project> projects = new ArrayList<>();
                    for(DocumentSnapshot doc: projectDocs) {
                        projects.add(mapDocToProject(doc));
                    }

                    return projects;
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, List<Project>> getProjectsBySchool(String school) {
        AsyncTask<Void, Void, List<Project>> task = new AsyncTask<Void, Void, List<Project>>() {
            @Override
            protected List<Project> doInBackground(Void... voids) {
                try{
                    Task<QuerySnapshot> getProjectsTask = projectRepository.getProjectsBySchool(school);
                    QuerySnapshot projectDocs = Tasks.await(getProjectsTask);

                    List<Project> projects = new ArrayList<>();
                    for(DocumentSnapshot doc: projectDocs) {
                        projects.add(mapDocToProject(doc));
                    }

                    return projects;
                } catch (InterruptedException | ExecutionException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Void> deleteProject(String projectId) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try{
                    Task<Void> deleteProjectTask = projectRepository.deleteProject(projectId);
                    Tasks.await(deleteProjectTask);
                } catch (InterruptedException | ExecutionException e) {
                    //Do something with the error message
                }
                return null;
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private Project mapDocToProject(DocumentSnapshot documentSnapshot){
        String projectId = (String) documentSnapshot.get("projectId");
        String projectName = (String) documentSnapshot.get("projectName");
        String ownerId = (String) documentSnapshot.get("ownerId");
        String description = (String) documentSnapshot.get("description");
        Timestamp editedDate = (Timestamp) documentSnapshot.get("editedDate");
        Timestamp createdDate = (Timestamp) documentSnapshot.get("createdDate");
        List<String> participantIds = (List<String>) documentSnapshot.get("participantIds");
        Integer maxParticipants = (Integer) documentSnapshot.get("maxParticipants");
        String imageUrl = (String) documentSnapshot.get("imageUrl");
        String school = (String) documentSnapshot.get("school");

        return new Project(projectId, projectName, ownerId, description, participantIds, createdDate, editedDate, maxParticipants, imageUrl, school);
    }
}
