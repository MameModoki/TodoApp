import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Main extends Application {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList(); // タスクのリスト

    @Override
    public void start(Stage primaryStage) {
        // タスクの一覧を表示する ListView
        ListView<Task> taskListView = new ListView<>(tasks);
        taskListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.description() + " | 優先度: " + item.priority() + " | 締切: " + item.deadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
            }
        });

        // タスク入力フィールド
        TextField taskInput = new TextField();
        taskInput.setPromptText("タスクを入力");

        // 優先度コンボボックス
        ComboBox<Priority> priorityComboBox = new ComboBox<>(FXCollections.observableArrayList(Priority.values()));
        priorityComboBox.setValue(Priority.中);

        // 期日ピッカー
        DatePicker deadlinePicker = new DatePicker();
        deadlinePicker.setPromptText("期限");

        // 追加ボタン
        Button addButton = getButton(taskInput, priorityComboBox, deadlinePicker);

        // 削除ボタン
        Button deleteButton = new Button("削除");
        deleteButton.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                tasks.remove(selectedTask); // 選択されたタスクを削除
                saveTasks();  // タスクを保存
            }
        });

// 保存ボタン
        Button saveButton = new Button("保存");
        saveButton.setOnAction(e -> saveTasks());

// 読み込みボタン
        Button loadButton = new Button("読み込み");
        loadButton.setOnAction(e -> loadTasks());

// 期日順にソートするボタン
        Button sortByDeadlineButton = new Button("期日順でソート");
        sortByDeadlineButton.setOnAction(e -> {
            tasks.sort(Comparator.comparing(Task::deadline));  // 期日順でソート
            taskListView.setItems(tasks);  // ソート後に ListView を更新
        });

// 優先度順にソートするボタン
        Button sortByPriorityButton = new Button("優先度順でソート");
        sortByPriorityButton.setOnAction(e -> {
            tasks.sort(Comparator.comparing(Task::priority));  // 優先度順でソート
            taskListView.setItems(tasks);  // ソート後に ListView を更新
        });

// レイアウトにソートボタンを追加
        VBox layout = new VBox(10, taskInput, priorityComboBox, deadlinePicker, addButton, deleteButton,
                sortByDeadlineButton, sortByPriorityButton, saveButton, loadButton, taskListView);
        layout.setStyle("-fx-padding: 20px;");

        // シーンのセットアップ
        Scene scene = new Scene(layout, 400, 500);
        primaryStage.setTitle("TodoApp - JavaFX版");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadTasks();  // 起動時にタスクを読み込む
    }

    private Button getButton(TextField taskInput, ComboBox<Priority> priorityComboBox, DatePicker deadlinePicker) {
        Button addButton = new Button("追加");
        addButton.setOnAction(e -> {
            String taskDescription = taskInput.getText();
            Priority priority = priorityComboBox.getValue();
            LocalDate deadline = deadlinePicker.getValue();
            if (taskDescription != null && !taskDescription.isEmpty() && deadline != null) {
                tasks.add(new Task(taskDescription, priority, deadline)); // リストにタスクを追加
                taskInput.clear();  // 入力フィールドをクリア
                deadlinePicker.setValue(null); // 期日をリセット
                saveTasks();  // タスクを保存
            }
        });
        return addButton;
    }

    // タスクをファイルに保存する
    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.description() + "," + task.priority() + "," + task.deadline());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // タスクをファイルから読み込む
    private void loadTasks() {
        File file = new File("tasks.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] taskData = line.split(",");
                    if (taskData.length == 3) {
                        String description = taskData[0];  // タスクの説明
                        // 優先度と期日を正しく分けて解析
                        Priority priority = Priority.valueOf(taskData[1].trim());  // 優先度を解析
                        LocalDate deadline = LocalDate.parse(taskData[2].trim());  // 期日を解析
                        tasks.add(new Task(description, priority, deadline));  // タスクをリストに追加
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                System.out.println("データの形式が間違っています。優先度や期日が正しくない可能性があります。");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// タスクのデータクラス
record Task(String description, Priority priority, LocalDate deadline) {

    @Override
    public String toString() {
        return description + " | 優先度: " + priority + " | 締切: " + deadline;
    }
}

enum Priority {
    高, 中, 低
}



