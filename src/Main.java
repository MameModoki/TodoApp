import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.util.Comparator;

public class Main extends Application {
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
    private final ObservableList<Memo> memos = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // タスクリストの作成
        ListView<Task> taskListView = new ListView<>(tasks);
        // メモリストの作成
        ListView<Memo> memoListView = new ListView<>(memos);
        // 連絡先リストの作成
        ListView<Contact> contactListView = new ListView<>(contacts);

        // タスク入力フィールド
        TextField taskInput = new TextField();

// 優先度コンボボックス（Priority型の値を表示するコンボボックス）
        ComboBox<Priority> priorityComboBox = new ComboBox<>(FXCollections.observableArrayList(Priority.values()));
        priorityComboBox.setValue(Priority.中);  // デフォルトで「中」を選択

// 期日ピッカー（期限を選択するためのフィールド）
        DatePicker deadlinePicker = new DatePicker();


        // タスク削除ボタン
        Button deleteTaskButton = new Button("削除");
        deleteTaskButton.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                tasks.remove(selectedTask); // 選択されたタスクを削除
                saveTasks();  // タスクを保存
            }
        });

        // 期日順でソートボタン
        Button sortByDeadlineButton = new Button("期日順でソート");
        sortByDeadlineButton.setOnAction(e -> {
            tasks.sort(Comparator.comparing(Task::getDeadline));  // 期日順でソート
            taskListView.setItems(tasks);  // ソート後に ListView を更新
        });

        // 優先度順でソートボタン
        Button sortByPriorityButton = new Button("優先度順でソート");
        sortByPriorityButton.setOnAction(e -> {
            tasks.sort(Comparator.comparing(Task::getPriority));  // 優先度順でソート
            taskListView.setItems(tasks);  // ソート後に ListView を更新
        });

        // 保存ボタン
        Button saveButton = new Button("保存");
        saveButton.setOnAction(e -> saveTasks());

        // 読み込みボタン
        Button loadButton = new Button("読み込み");
        loadButton.setOnAction(e -> loadTasks());

        // 連絡先の入力フィールド
        TextField nameInput = new TextField();
        TextField phoneInput = new TextField();
        TextField emailInput = new TextField();
        Button addContactButton = new Button("追加");
        addContactButton.setOnAction(e -> {
            contacts.add(new Contact(nameInput.getText(), phoneInput.getText(), emailInput.getText())); // 連絡先追加
            nameInput.clear();
            phoneInput.clear();
            emailInput.clear();
        });

        // メモの入力フィールド
        TextField memoInput = new TextField();
        Button addMemoButton = new Button("追加");
        addMemoButton.setOnAction(e -> {
            memos.add(new Memo(memoInput.getText())); // メモ追加
            memoInput.clear();
        });

        // カレンダーイベント追加ボタン
        Button addCalendarEventButton = new Button("予定を追加");
        addCalendarEventButton.setOnAction(e -> {
            System.out.println("選ばれた日付：" + new DatePicker().getValue());
        });

        // TabPaneで機能切り替え
        TabPane tabPane = new TabPane();

        // Todoタブ
        Tab todoTab = new Tab("Todo");
        todoTab.setContent(new VBox(10, taskInput, addTaskButton, deleteTaskButton, sortByDeadlineButton, sortByPriorityButton, taskListView));

        // メモタブ
        Tab memoTab = new Tab("メモ");
        memoTab.setContent(new VBox(10, memoInput, addMemoButton, memoListView));

        // 連絡先タブ
        Tab contactTab = new Tab("連絡先");
        contactTab.setContent(new VBox(10, nameInput, phoneInput, emailInput, addContactButton, contactListView));

        // カレンダータブ
        Tab calendarTab = new Tab("カレンダー");
        calendarTab.setContent(new VBox(10, new DatePicker(), addCalendarEventButton));

        tabPane.getTabs().addAll(todoTab, memoTab, contactTab, calendarTab);

        // シーンのセットアップ
        Scene scene = new Scene(tabPane, 400, 500);
        primaryStage.setTitle("統合アプリ");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadTasks();  // 起動時にタスクを読み込む
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.getDescription() + "," + task.getPriority() + "," + task.getDeadline());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTasks() {
        File file = new File("tasks.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] taskData = line.split(",");
                    if (taskData.length == 3) {
                        String description = taskData[0];  // タスクの説明
                        Priority priority = Priority.valueOf(taskData[1].trim());  // 優先度を解析
                        LocalDate deadline = LocalDate.parse(taskData[2].trim());  // 期日を解析
                        tasks.add(new Task(description, priority, deadline)); // タスクをリストに追加
                    }
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("データの形式が間違っています。");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

// タスククラス
class Task {
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



// 連絡先クラス
class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return name + " | " + phone + " | " + email;
    }
}

// メモクラス
class Memo {
    private String content;

    public Memo(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}

enum Priority {
    高, 中, 低
}
