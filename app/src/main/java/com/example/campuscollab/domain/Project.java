package com.example.campuscollab.domain;

public class Project {
    private String projectName;
    private String ownerEmail;
    private String description;

    public Project(String projectName, String ownerEmail, String description, String[] participantEmails) {
        this.projectName = projectName;
        this.ownerEmail = ownerEmail;
        this.description = description;
        this.participantEmails = participantEmails;
    }

    private String [] participantEmails;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getParticipantEmails() {
        return participantEmails;
    }

    public void setParticipantEmails(String[] participantEmails) {
        this.participantEmails = participantEmails;
    }
}
