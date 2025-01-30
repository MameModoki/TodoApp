import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class Main extends Application {
    private final ObservableList<String> tasks = FXCollections.observableArrayList(); // タスクのリスト

    @Override
    public void start(Stage primaryStage) {
        // タスクの一覧を表示する ListView
        ListView<String> taskListView = new ListView<>(tasks);

        // タスク入力フィールド
        TextField taskInput = new TextField();
        taskInput.setPromptText("タスクを入力");

        // 追加ボタン
        Button addButton = new Button("追加");
        addButton.setOnAction(e -> {
            String task = taskInput.getText();
            if (!task.isEmpty()) {
                tasks.add(task); // リストにタスクを追加
                taskInput.clear();  // 入力フィールドをクリア
                saveTasks();  // タスクを保存
            }
        });

        // 削除ボタン
        Button deleteButton = new Button("削除");
        deleteButton.setOnAction(e -> {
            String selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                tasks.remove(selectedTask); // 選択されたタスクを削除
                saveTasks();  // タスクを保存
            }
        });

        // 保存ボタン (ファイルに保存)
        Button saveButton = new Button("保存");
        saveButton.setOnAction(e -> saveTasks());

        // 読み込みボタン (ファイルからタスクを読み込む)
        Button loadButton = new Button("読み込み");
        loadButton.setOnAction(e -> loadTasks());

        // レイアウト
        VBox layout = new VBox(10, taskInput, addButton, deleteButton, saveButton, loadButton, taskListView);
        layout.setStyle("-fx-padding: 20px;");

        // シーンのセットアップ
        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("TodoApp - JavaFX版");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadTasks();  // 起動時にタスクを読み込む
    }

    // タスクをファイルに保存する
    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (String task : tasks) {
                writer.write(task);
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
                    tasks.add(line);  // 読み込んだタスクをリストに追加
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
