package com.binnacle.api.response.subtask;

public class SubtaskResponse {
    private int id;
    private String name;
    private int task_id;
    private boolean active;

    public SubtaskResponse(int id, String name, int task_id, boolean active) {
        this.id = id;
        this.name = name;
        this.task_id = task_id;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
