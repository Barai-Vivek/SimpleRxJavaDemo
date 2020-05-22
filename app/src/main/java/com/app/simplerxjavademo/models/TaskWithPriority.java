package com.app.simplerxjavademo.models;

public class TaskWithPriority {
    String description;
    boolean isActive;
    int priority;

    public TaskWithPriority(String description, boolean isActive, int priority) {
        this.description = description;
        this.isActive = isActive;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getPriority() {
        return priority;
    }
}
