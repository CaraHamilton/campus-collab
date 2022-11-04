package com.example.campuscollab.service;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.example.campuscollab.domain.Project;
import com.example.campuscollab.domain.Request;
import com.example.campuscollab.domain.User;
import com.example.campuscollab.repository.RequestRepository;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressLint("StaticFieldLeak")
public class RequestService {

    private static RequestService instance;
    private final RequestRepository requestRepository;
    private final ProjectService projectService;
    private final UserService userService;

    public static final String ACCEPTED_KEY = "ACCEPTED";
    public static final String REJECTED_KEY = "REJECTED";
    public static final String PENDING_KEY = "PENDING";

    public static synchronized RequestService getInstance() {
        if(instance == null) {
            instance = new RequestService();
        }
        return instance;
    }

    private RequestService () {
        requestRepository = new RequestRepository();
        projectService = ProjectService.getInstance();
        userService = UserService.getInstance();
    }

    public AsyncTask<Void, Void, Request> createRequest(Request request) {
        AsyncTask<Void, Void, Request> task = new AsyncTask<Void, Void, Request>() {
            @Override
            protected Request doInBackground(Void... voids) {
                try{
                    request.setStatus(PENDING_KEY);
                    Task<Void> createRequestTask = requestRepository.createRequest(request);
                    Tasks.await(createRequestTask);

                    return request;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Request> acceptRequest(Request request) {
        AsyncTask<Void, Void, Request> task = new AsyncTask<Void, Void, Request>() {
            @Override
            protected Request doInBackground(Void... voids) {
                try{
                    request.setStatus(ACCEPTED_KEY);
                    Task<Void> updateRequestTask = requestRepository.updateRequest(request);
                    Tasks.await(updateRequestTask);

                    Project project = projectService.getProject(request.getProjectId()).get();
                    List<String> participants = project.getParticipantIds();
                    participants.add(request.getRequesterId());
                    project.setParticipantIds(participants);
                    projectService.updateProject(project).get();

                    return request;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Request> rejectRequest(Request request) {
        AsyncTask<Void, Void, Request> task = new AsyncTask<Void, Void, Request>() {
            @Override
            protected Request doInBackground(Void... voids) {
                try{
                    request.setStatus(REJECTED_KEY);
                    Task<Void> updateRequestTask = requestRepository.updateRequest(request);
                    Tasks.await(updateRequestTask);

                    return request;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, Boolean> deleteRequest(String requestId) {
        AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try{
                    Task<Void> deleteRequestTask = requestRepository.deleteRequest(requestId);
                    Tasks.await(deleteRequestTask);
                    return true;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return false;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, List<Request>> getSentRequests() {
        AsyncTask<Void, Void, List<Request>> task = new AsyncTask<Void, Void, List<Request>>() {
            @Override
            protected List<Request> doInBackground(Void... voids) {
                try{
                    User currentUser = userService.getCurrentUser();

                    Task<QuerySnapshot> getRequestsTask = requestRepository.getRequestsByProjectOwner(currentUser.getId());
                    QuerySnapshot querySnapshot = Tasks.await(getRequestsTask);
                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                    List<Request> requests = new ArrayList<>();

                    for(DocumentSnapshot document: documents) {
                        requests.add(mapDocToRequest(document));
                    }

                    return requests;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public AsyncTask<Void, Void, List<Request>> getReceivedRequests() {
        AsyncTask<Void, Void, List<Request>> task = new AsyncTask<Void, Void, List<Request>>() {
            @Override
            protected List<Request> doInBackground(Void... voids) {
                try{
                    User currentUser = userService.getCurrentUser();

                    Task<QuerySnapshot> getRequestsTask = requestRepository.getRequestsByRequester(currentUser.getId());
                    QuerySnapshot querySnapshot = Tasks.await(getRequestsTask);
                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();

                    List<Request> requests = new ArrayList<>();

                    for(DocumentSnapshot document: documents) {
                        requests.add(mapDocToRequest(document));
                    }

                    return requests;
                } catch (InterruptedException | ExecutionException | NullPointerException e) {
                    return null;
                }
            }
        };

        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private Request mapDocToRequest(DocumentSnapshot documentSnapshot) {
        Request request = new Request();
        request.setProjectId((String) documentSnapshot.get("projectId"));
        request.setProjectName((String) documentSnapshot.get("projectName"));
        request.setProjectOwnerId((String) documentSnapshot.get("projectOwnerId"));
        request.setProjectOwnerImageUrl((String) documentSnapshot.get("projectOwnerImageUrl"));
        request.setRequesterId((String) documentSnapshot.get("requesterId"));
        request.setRequesterName((String) documentSnapshot.get("requesterName"));
        request.setRequesterImageUrl((String) documentSnapshot.get("requesterImageUrl"));
        request.setId((String) documentSnapshot.get("id"));
        request.setStatus((String) documentSnapshot.get("status"));
        request.setSubmittedDate((Timestamp) documentSnapshot.get("submittedDate"));

        return request;
    }

}
