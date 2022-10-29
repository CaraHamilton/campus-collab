package com.example.campuscollab.domain;

public class Request {
    private String owner_id;
    private String owner_name;
    private String requesting_id;
    private String requesting_name;
    private Project project;

    public Request(String owner_id, String owner_name, String requesting_id, String requesting_name, Project project) {
        this.owner_id = owner_id;
        this.owner_name = owner_name;
        this.requesting_id = requesting_id;
        this.requesting_name = requesting_name;
        this.project = project;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getRequesting_id() {
        return requesting_id;
    }

    public void setRequesting_id(String requesting_id) {
        this.requesting_id = requesting_id;
    }

    public String getRequesting_name() {
        return requesting_name;
    }

    public void setRequesting_name(String requesting_name) {
        this.requesting_name = requesting_name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
