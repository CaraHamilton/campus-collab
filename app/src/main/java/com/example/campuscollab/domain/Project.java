package com.example.campuscollab.domain;

import com.google.firebase.Timestamp;

import java.util.List;

public class Project {
    private String projectId;
    private String projectName;
    private String ownerId;
    private String description;
    private Timestamp editedDate;
    private Timestamp createdDate;
    private List<String> participantIds;
    private Integer maxParticipants;

    public Project(String projectName, String ownerId, String description, List<String> participantIds, Timestamp createdDate, Timestamp editedDate, Integer maxParticipants) {
        this.projectName = projectName;
        this.ownerId = ownerId;
        this.description = description;
        this.participantIds = participantIds;
        this.createdDate = createdDate;
        this.editedDate = editedDate;
        this.maxParticipants = maxParticipants;
    }

    public Project(String projectId, String projectName, String ownerId, String description, List<String> participantIds, Timestamp createdDate, Timestamp editedDate, Integer maxParticipants) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.ownerId = ownerId;
        this.description = description;
        this.participantIds = participantIds;
        this.createdDate = createdDate;
        this.editedDate = editedDate;
        this.maxParticipants = maxParticipants;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<String> participantIds) {
        this.participantIds = participantIds;
    }

    public void setEditedDate(Timestamp editedDate) {
        this.editedDate = editedDate;
    }

    public Timestamp getEditedDate() {
        return editedDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }
}
