package com.example.todoapp.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.todoapp.models.Todo;
import com.example.todoapp.utils.TodoFileUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TodoTab implements TabContent {
    private ListView<Todo> todoListView = new ListView<>();
    private ComboBox<String> priorityComboBox = new ComboBox<>();

    @Override
    public VBox getTabContent() {
        // タスク入力フィールド
        TextField taskInput = new TextField();
        taskInput.setPromptText("タスクを入力してください");

        // 優先度選択
        priorityComboBox.getItems().addAll("高", "中", "低");
        priorityComboBox.setValue("中");

        // 期限選択
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // ボタン類
        Button addButton = new Button("追加");
        Button toggleButton = new Button("完了/未完了");
        Button deleteButton = new Button("削除");

        // ボタンの動作設定
        addButton.setOnAction(e -> {
            String task = taskInput.getText();
            if (!task.isEmpty()) {
                Todo newTodo = new Todo(
                    task,
                    priorityComboBox.getValue(),
                    datePicker.getValue()
                );
                todoListView.getItems().add(newTodo);
                taskInput.clear();
            }
        });

        toggleButton.setOnAction(e -> {
            Todo selectedTodo = todoListView.getSelectionModel().getSelectedItem();
            if (selectedTodo != null) {
                selectedTodo.toggleComplete();
                todoListView.refresh();
            }
        });

        deleteButton.setOnAction(e -> {
            Todo selectedTodo = todoListView.getSelectionModel().getSelectedItem();
            if (selectedTodo != null) {
                todoListView.getItems().remove(selectedTodo);
            }
        });

        // 入力部分のレイアウト
        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(taskInput, priorityComboBox, datePicker);

        // ボタン部分のレイアウト
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(addButton, toggleButton, deleteButton);

        // メインレイアウト
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(inputBox, buttonBox, todoListView);
        
        return vbox;
    }

    private void saveTodos() {
        ObservableList<Todo> todos = todoListView.getItems();
        TodoFileUtil.saveTodos(new ArrayList<>(todos));
    }

    private void loadTodos() {
        List<Todo> todos = TodoFileUtil.loadTodos();
        todoListView.setItems(FXCollections.observableArrayList(todos));
    }

    private void setupSearch(TextField searchField) {
        FilteredList<Todo> filteredData = new FilteredList<>(todoListView.getItems(), p -> true);
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(todo -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return todo.getContent().toLowerCase().contains(newValue.toLowerCase());
            });
        });

        SortedList<Todo> sortedData = new SortedList<>(filteredData);
        todoListView.setItems(sortedData);
    }

    private void setupSorting(ComboBox<String> sortBox) {
        sortBox.getItems().addAll("作成日", "優先度", "期限日");
        sortBox.setValue("作成日");
        
        sortBox.setOnAction(e -> {
            String sortType = sortBox.getValue();
            ObservableList<Todo> items = todoListView.getItems();
            
            switch (sortType) {
                case "優先度":
                    FXCollections.sort(items, 
                        (a, b) -> a.getPriority().compareTo(b.getPriority()));
                    break;
                case "期限日":
                    FXCollections.sort(items, 
                        (a, b) -> a.getDueDate().compareTo(b.getDueDate()));
                    break;
                default:
                    // デフォルトは作成日順（リストの順序）
                    break;
            }
        });
    }

    public ObservableList<Todo> getTodoItems() {
        return todoListView.getItems();
    }

    public void setTodoItems(List<Todo> todos) {
        todoListView.setItems(FXCollections.observableArrayList(todos));
    }
}
