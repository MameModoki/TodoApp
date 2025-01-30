package TodoApp;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TodoApp {

    private final List<Task> tasks = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "tasks.txt";

    private enum Priority {
        高, 中, 低
    }

    private static class Task {
        String description;
        LocalDate deadline;
        Priority priority;

        Task(String description, LocalDate deadline, Priority priority) {
            this.description = description;
            this.deadline = deadline;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "[優先度: " + priority + "] " + description + " (締切: " + deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ")";
        }
    }

    public TodoApp() {
        loadTasks(); // ✅ 起動時にタスクを読み込む
    }

    public void start() {
        int choice;
        do {
            System.out.println("\n==== TODO アプリ ====");
            System.out.println("1. タスクを追加");
            System.out.println("2. タスクを表示（期日順）");
            System.out.println("3. タスクを編集");
            System.out.println("4. タスクを削除");
            System.out.println("5. 終了");
            System.out.print("選択してください: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // 改行を消す

            switch (choice) {
                case 1 -> addTask();
                case 2 -> showTasksSortedByDeadline();
                case 3 -> editTask();
                case 4 -> removeTask();
                case 5 -> {
                    saveTasks();
                    System.out.println("アプリを終了します...");
                }
                default -> System.out.println("無効な選択です。もう一度入力してください。");
            }
        } while (choice != 5);
    }

    private void addTask() {
        System.out.print("追加するタスク: ");
        String description = scanner.nextLine();

        LocalDate deadline;
        while (true) {
            System.out.print("締切 (YYYY-MM-DD 形式で入力してください): ");
            String deadlineInput = scanner.nextLine();
            try {
                deadline = LocalDate.parse(deadlineInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("❌ 無効な日付です。もう一度入力してください（例: 2025-12-31）。");
            }
        }

        System.out.print("優先度を選択（1: 高, 2: 中, 3: 低）: ");
        int priorityChoice = scanner.nextInt();
        scanner.nextLine();  // 改行を消す
        Priority priority = switch (priorityChoice) {
            case 1 -> Priority.高;
            case 2 -> Priority.中;
            case 3 -> Priority.低;
            default -> {
                System.out.println("無効な選択です。デフォルトの「中」に設定します。");
                yield Priority.中;
            }
        };

        tasks.add(new Task(description, deadline, priority));
        saveTasks();
        System.out.println("✅ タスクを追加しました！");
    }

    private void removeTask() {
        showTasksSortedByDeadline();
        if (tasks.isEmpty()) return;

        System.out.print("削除するタスクの番号: ");
        int index = scanner.nextInt();
        scanner.nextLine();  // 改行を消す

        if (index >= 1 && index <= tasks.size()) {
            tasks.remove(index - 1);
            saveTasks();
            System.out.println("✅ タスクを削除しました！");
        } else {
            System.out.println("❌ 無効な番号です。");
        }
    }

    private void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Task task : tasks) {
                writer.write(task.description + "," + task.deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "," + task.priority);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("タスクの保存中にエラーが発生しました: " + e.getMessage());
        }
    }

    private void loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        LocalDate deadline = LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        Priority priority = Priority.valueOf(parts[2]);
                        tasks.add(new Task(parts[0], deadline, priority));
                    } catch (DateTimeParseException | IllegalArgumentException e) {
                        System.out.println("❌ 読み込みエラー: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("タスクの読み込み中にエラーが発生しました: " + e.getMessage());
        }
    }

    private void showTasksSortedByDeadline() {
        if (tasks.isEmpty()) {
            System.out.println("現在タスクはありません。");
        } else {
            System.out.println("=== タスク一覧（期日順） ===");
            List<Task> sortedTasks = tasks.stream()
                    .sorted(Comparator.comparing(task -> task.deadline))
                    .toList();

            for (int i = 0; i < sortedTasks.size(); i++) {
                System.out.println((i + 1) + ". " + sortedTasks.get(i));
            }
        }
    }

    private void editTask() {
        showTasksSortedByDeadline();
        if (tasks.isEmpty()) return;

        System.out.print("編集するタスクの番号: ");
        int index = scanner.nextInt();
        scanner.nextLine();  // 改行を消す

        if (index < 1 || index > tasks.size()) {
            System.out.println("無効な番号です。");
            return;
        }

        Task task = tasks.get(index - 1);

        System.out.println("編集する項目を選択してください:");
        System.out.println("1. タスクの内容: " + task.description);
        System.out.println("2. 締め切り (YYYY-MM-DD): " + task.deadline);
        System.out.println("3. 優先度: " + task.priority);
        System.out.print("選択: ");

        int choice = scanner.nextInt();
        scanner.nextLine();  // 改行を消す

        switch (choice) {
            case 1:
                System.out.print("新しいタスクの内容: ");
                task.description = scanner.nextLine();
                break;
            case 2:
                System.out.print("新しい締め切り (YYYY-MM-DD): ");
                while (true) {
                    String newDeadline = scanner.nextLine();
                    try {
                        task.deadline = LocalDate.parse(newDeadline, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("❌ 無効な日付です。もう一度入力してください（例: 2025-12-31）。");
                    }
                }
                break;
            case 3:
                System.out.print("新しい優先度を選択（1: 高, 2: 中, 3: 低）: ");
                task.priority = Priority.values()[Math.max(0, Math.min(2, scanner.nextInt() - 1))];
                scanner.nextLine();
                break;
            default:
                System.out.println("無効な選択です。");
        }

        saveTasks();
        System.out.println("✅ タスクを更新しました！");
    }
}
