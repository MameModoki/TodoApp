package com.example.todoapp.views;

import com.example.todoapp.models.Task;
import com.example.todoapp.models.Priority;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;

/**
 * ToDoリストを表示・管理するタブのUIコンポーネント
 * タスクの追加、削除、表示機能を提供します
 */
public class TodoTab {

    /** タスクリストを表示するListView */
    private ListView<Task> taskListView = new ListView<>();

    /**
     * タブのコンテンツを生成して返します
     * @return タスク管理用のUI要素を含むVBox
     */
    public VBox getTabContent() {
        // タスク入力フィールド
        TextField taskInput = new TextField();
        taskInput.setPromptText("タスクを入力してください");

        // 優先度選択コンボボックス
        ComboBox<Priority> priorityComboBox = new ComboBox<>(FXCollections.observableArrayList(Priority.values()));
        priorityComboBox.setValue(Priority.中);  // デフォルト
        priorityComboBox.setPromptText("優先度を選択");

        // 締切日選択
        DatePicker deadlinePicker = new DatePicker();
        deadlinePicker.setPromptText("締切日を選択");

        // タスク追加ボタン
        Button addTaskButton = new Button("追加");
        addTaskButton.setOnAction(e -> {
            String description = taskInput.getText().trim();
            if (!description.isEmpty()) {
                taskListView.getItems().add(new Task(
                    description,
                    priorityComboBox.getValue(),
                    deadlinePicker.getValue()
                ));
                // 入力フィールドをクリア
                taskInput.clear();
                priorityComboBox.setValue(Priority.中);
                deadlinePicker.setValue(null);
            }
        });

        // タスク削除ボタン
        Button deleteTaskButton = new Button("削除");
        deleteTaskButton.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                taskListView.getItems().remove(selectedTask);
            }
        });

        // コンテンツをまとめる
        VBox vbox = new VBox(10,
            taskInput,
            priorityComboBox,
            deadlinePicker,
            addTaskButton,
            deleteTaskButton,
            taskListView
        );
        vbox.setSpacing(10);
        return vbox;
    }
} 