package com.binnacle.api.response.project;

public class ProjectResponse {

    private int id;
    private String name;
    private String owner;
    private boolean active;

    public ProjectResponse(int id, String name, String owner, boolean active) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.active = active;
    }

    public ProjectResponse() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isActive() {
        return active;
    }


}
