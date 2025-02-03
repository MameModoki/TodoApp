package com.example.todoapp.models;

import java.time.LocalDate;

public class Todo {
    private String content;
    private String priority;
    private LocalDate dueDate;
    private boolean completed;

    public Todo(String content, String priority, LocalDate dueDate) {
        this.content = content;
        this.priority = priority;
        this.dueDate = dueDate;
        this.completed = false;
    }

    public void toggleComplete() {
        this.completed = !this.completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        String status = completed ? "✓ " : "□ ";
        return String.format("%s%s [%s] (%s)", 
            status, content, priority, 
            dueDate != null ? dueDate.toString() : "期限なし");
    }

    // Getters and Setters
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
} 