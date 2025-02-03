package com.example.todoapp.models;

import java.time.LocalDate;

/**
 * タスクを管理するクラス
 */
public class Task {
    private String description;
    private Priority priority;
    private LocalDate deadline;
    private boolean completed;  // タスクの完了状態
    private LocalDate createdAt;  // タスクの作成日

    /**
     * タスクを作成するコンストラクタ
     */
    public Task(String description, Priority priority, LocalDate deadline) {
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completed = false;  // デフォルトは未完了
        this.createdAt = LocalDate.now();  // 作成日を現在の日付に設定
    }

    // Getters
    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("説明は空にできません");
        }
        this.description = description.trim();
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * タスクの完了状態を切り替えます
     */
    public void toggleComplete() {
        this.completed = !this.completed;
    }

    /**
     * タスクが期限切れかどうかを確認します
     */
    public boolean isOverdue() {
        return !completed && deadline != null && deadline.isBefore(LocalDate.now());
    }

    /**
     * タスクの残り日数を計算します
     */
    public long getDaysUntilDeadline() {
        if (deadline == null) return 0;
        return LocalDate.now().until(deadline).getDays();
    }

    @Override
    public String toString() {
        String status = completed ? "完了" : "未完了";
        String overdueStatus = isOverdue() ? "【期限切れ】" : "";
        return String.format("%s%s | %s | 優先度: %s | 締切: %s | 状態: %s",
            overdueStatus,
            description,
            createdAt,
            priority,
            deadline,
            status
        );
    }
}
