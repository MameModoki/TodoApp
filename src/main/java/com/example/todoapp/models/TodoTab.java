package TodoApp;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.DatePicker;

public class TodoTab {

    private ListView<Task> taskListView = new ListView<>();

    public VBox getTabContent() {
        // タスク入力フィールド
        TextField taskInput = new TextField();
        ComboBox<Priority> priorityComboBox = new ComboBox<>(FXCollections.observableArrayList(Priority.values()));
        priorityComboBox.setValue(Priority.中);  // デフォルト

        DatePicker deadlinePicker = new DatePicker();

        // タスク追加ボタン
        Button addTaskButton = new Button("追加");
        addTaskButton.setOnAction(e -> {
            taskListView.getItems().add(new Task(taskInput.getText(), priorityComboBox.getValue(), deadlinePicker.getValue()));
            taskInput.clear();
            priorityComboBox.setValue(Priority.中);
            deadlinePicker.setValue(null);
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
        VBox vbox = new VBox(10, taskInput, priorityComboBox, deadlinePicker, addTaskButton, deleteTaskButton, taskListView);
        return vbox;
    }
}
