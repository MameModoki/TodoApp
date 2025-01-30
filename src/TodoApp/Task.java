package TodoApp;

import java.time.LocalDate;

public class Task {

    private String description;
    private Priority priority;
    private LocalDate deadline;

    public Task(String description, Priority priority, LocalDate deadline) {
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return description + " | 優先度: " + priority + " | 締切: " + deadline;
    }
}
