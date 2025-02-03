package com.example.todoapp.models;

import java.time.LocalDate;

public class Memo {

    private Long id;
    private String content;
    private Priority priority;
    private LocalDate dueDate;

    public enum Priority {
        高, 中, 低
    }

    public Memo(String content, Priority priority, LocalDate dueDate) {
        this.content = content;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return content;
    }
}
