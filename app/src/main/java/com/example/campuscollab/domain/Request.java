package com.example.campuscollab.domain;

import com.google.firebase.Timestamp;

public class Request {

    private String projectId;
    private String projectName;
    private String projectOwnerId;
    private String projectOwnerName;
    private String projectOwnerImageUrl;
    private String requesterId;
    private String requesterName;
    private String requesterImageUrl;
    private String id;
    private String status;
    private Timestamp submittedDate;

    public Request() {}

    public Request(String projectId, String projectName, String projectOwnerId, String projectOwnerName, String projectOwnerImageUrl, String requesterId, String requesterName, String requesterImageUrl, String id, String status, Timestamp submittedDate) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.projectOwnerId = projectOwnerId;
        this.projectOwnerName = projectOwnerName;
        this.projectOwnerImageUrl = projectOwnerImageUrl;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.requesterImageUrl = requesterImageUrl;
        this.id = id;
        this.status = status;
        this.submittedDate = submittedDate;
    }

    public Request(String projectName, String projectOwnerId, String projectOwnerName, String projectOwnerImageUrl, String requesterId, String requesterName, String requesterImageUrl, String id, String status, Timestamp submittedDate) {
        this.projectName = projectName;
        this.projectOwnerId = projectOwnerId;
        this.projectOwnerName = projectOwnerName;
        this.projectOwnerImageUrl = projectOwnerImageUrl;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.requesterImageUrl = requesterImageUrl;
        this.id = id;
        this.status = status;
        this.submittedDate = submittedDate;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectOwnerId() {
        return projectOwnerId;
    }

    public void setProjectOwnerId(String projectOwnerId) {
        this.projectOwnerId = projectOwnerId;
    }

    public String getProjectOwnerName() {
        return projectOwnerName;
    }

    public void setProjectOwnerName(String projectOwnerName) {
        this.projectOwnerName = projectOwnerName;
    }

    public String getProjectOwnerImageUrl() {
        return projectOwnerImageUrl;
    }

    public void setProjectOwnerImageUrl(String projectOwnerImageUrl) {
        this.projectOwnerImageUrl = projectOwnerImageUrl;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequesterImageUrl() {
        return requesterImageUrl;
    }

    public void setRequesterImageUrl(String requesterImageUrl) {
        this.requesterImageUrl = requesterImageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Timestamp submittedDate) {
        this.submittedDate = submittedDate;
    }
}
