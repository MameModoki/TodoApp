package com.example.todoapp.models;

import java.time.LocalDate;

/**
 * タスク情報を管理するクラス
 * タスクの説明、優先度、締切日を保持します
 */
public class Task {

    /** タスクの説明 */
    private String description;
    /** タスクの優先度 */
    private Priority priority;
    /** タスクの締切日 */
    private LocalDate deadline;

    /**
     * タスクを作成するコンストラクタ
     * @param description タスクの説明
     * @param priority タスクの優先度
     * @param deadline タスクの締切日
     */
    public Task(String description, Priority priority, LocalDate deadline) {
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    /**
     * タスクの説明を取得します
     * @return タスクの説明
     */
    public String getDescription() {
        return description;
    }

    /**
     * タスクの優先度を取得します
     * @return タスクの優先度
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * タスクの締切日を取得します
     * @return タスクの締切日
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * タスクの説明を設定します
     * @param description 設定する説明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * タスクの優先度を設定します
     * @param priority 設定する優先度
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * タスクの締切日を設定します
     * @param deadline 設定する締切日
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * オブジェクトの文字列表現を返します
     * @return "説明 | 優先度: [優先度] | 締切: [締切日]" 形式の文字列
     */
    @Override
    public String toString() {
        return description + " | 優先度: " + priority + " | 締切: " + deadline;
    }
}
