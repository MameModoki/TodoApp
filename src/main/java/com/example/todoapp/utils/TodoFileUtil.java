package com.example.todoapp.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.todoapp.models.Todo;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TodoFileUtil {
    private static final String FILE_PATH = "todos.txt";

    public static void saveTodos(List<Todo> todos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Todo todo : todos) {
                writer.write(String.format("%s,%s,%s,%b%n",
                    todo.getContent(),
                    todo.getPriority(),
                    todo.getDueDate(),
                    todo.isCompleted()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Todo> loadTodos() {
        List<Todo> todos = new ArrayList<>();
        File file = new File(FILE_PATH);
        
        if (!file.exists()) {
            return todos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Todo todo = new Todo(
                        parts[0],
                        parts[1],
                        LocalDate.parse(parts[2])
                    );
                    if (Boolean.parseBoolean(parts[3])) {
                        todo.toggleComplete();
                    }
                    todos.add(todo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public static void exportTodos(List<Todo> todos, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("タスクリストの保存");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("テキストファイル", "*.txt")
        );
        
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Todo todo : todos) {
                    writer.write(String.format("%s,%s,%s,%b%n",
                        todo.getContent(),
                        todo.getPriority(),
                        todo.getDueDate(),
                        todo.isCompleted()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Todo> importTodos(Stage stage) {
        List<Todo> todos = new ArrayList<>();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("タスクリストの読み込み");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("テキストファイル", "*.txt")
        );
        
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        Todo todo = new Todo(
                            parts[0],
                            parts[1],
                            LocalDate.parse(parts[2])
                        );
                        if (Boolean.parseBoolean(parts[3])) {
                            todo.toggleComplete();
                        }
                        todos.add(todo);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return todos;
    }
} 