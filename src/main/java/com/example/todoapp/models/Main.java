package com.example.todoapp.models;

import com.example.todoapp.views.CalendarTab;
import com.example.todoapp.views.ContactTab;
import com.example.todoapp.views.MemoTab;
import com.example.todoapp.views.TodoTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // TabPaneの作成
        TabPane tabPane = new TabPane();

        // Todoタブ
        TodoTab todoTab = new TodoTab();
        Tab todoTabItem = new Tab("Todo");
        todoTabItem.setContent(todoTab.getTabContent());

        // メモタブ
        MemoTab memoTab = new MemoTab();
        Tab memoTabItem = new Tab("メモ");
        memoTabItem.setContent(memoTab.getTabContent());

        // 連絡先タブ
        ContactTab contactTab = new ContactTab();
        Tab contactTabItem = new Tab("連絡先");
        contactTabItem.setContent(contactTab.getTabContent());

        // カレンダータブ
        CalendarTab calendarTab = new CalendarTab();
        Tab calendarTabItem = new Tab("カレンダー");
        calendarTabItem.setContent(calendarTab.getTabContent());

        // タブを追加
        tabPane.getTabs().addAll(todoTabItem, memoTabItem, contactTabItem, calendarTabItem);

        // シーンのセットアップ
        Scene scene = new Scene(tabPane, 500, 500);
        primaryStage.setTitle("統合アプリ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
