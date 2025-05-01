package com.cognifyz;

public class Task {
    private int id;
    private String title;
    private String description;

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task ID: " + id + " | Title: " + title + " | Description: " + description;
    }

    public String toFileString() {
        return id + "," + title + "," + description;
    }

    public static Task fromFileString(String line) {
        String[] parts = line.split(",", 3);
        return new Task(Integer.parseInt(parts[0]), parts[1], parts[2]);
    }
}
