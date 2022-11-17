package com.example.campuscollab.domain;

import com.google.firebase.Timestamp;

import java.util.List;

public class Project implements Comparable<Project> {
    private Timestamp createdDate;
    private String description;
    private Timestamp editedDate;
    private String imagePath;
    private String projectName;
    private Integer maxParticipants;
    private String ownerId;
    private List<String> participantIds;
    private String projectId;
    private String school;

    public Project(String projectName, String ownerId, String description, List<String> participantIds, Timestamp createdDate, Timestamp editedDate, Integer maxParticipants, String imagePath, String school) {
        this.projectName = projectName;
        this.ownerId = ownerId;
        this.description = description;
        this.participantIds = participantIds;
        this.createdDate = createdDate;
        this.editedDate = editedDate;
        this.maxParticipants = maxParticipants;
    }

    public Project(String projectId, String projectName, String ownerId, String description, List<String> participantIds, Timestamp createdDate, Timestamp editedDate, Integer maxParticipants, String imagePath, String school) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.ownerId = ownerId;
        this.description = description;
        this.participantIds = participantIds;
        this.createdDate = createdDate;
        this.editedDate = editedDate;
        this.maxParticipants = maxParticipants;
        this.imagePath = imagePath;
        this.school = school;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public int compareTo(Project o) {
        return getCreatedDate().compareTo(o.getCreatedDate());
    }
}
