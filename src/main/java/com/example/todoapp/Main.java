package com.example.todoapp;

import java.util.ArrayList;
import java.util.List;

import com.example.todoapp.models.Todo;
import com.example.todoapp.utils.TodoFileUtil;
import com.example.todoapp.views.CalendarTab;
import com.example.todoapp.views.ContactTab;
import com.example.todoapp.views.MemoTab;
import com.example.todoapp.views.TabContent;
import com.example.todoapp.views.TodoTab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private TabPane tabPane;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // メインレイアウト
        BorderPane mainLayout = new BorderPane();

        // メニューバーの作成
        MenuBar menuBar = createMenuBar();
        mainLayout.setTop(menuBar);

        // TabPaneの作成
        tabPane = new TabPane();
        
        // タブの作成
        Tab todoTabItem = createTab("Todo", new TodoTab());
        Tab memoTabItem = createTab("メモ", new MemoTab());
        Tab contactTabItem = createTab("連絡先", new ContactTab());
        Tab calendarTabItem = createTab("カレンダー", new CalendarTab());

        // タブを追加
        tabPane.getTabs().addAll(todoTabItem, memoTabItem, contactTabItem, calendarTabItem);
        mainLayout.setCenter(tabPane);

        // シーンのセットアップ
        Scene scene = new Scene(mainLayout, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        
        primaryStage.setTitle("統合アプリ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        
        // ファイルメニュー
        Menu fileMenu = new Menu("ファイル");
        MenuItem exportItem = new MenuItem("エクスポート");
        MenuItem importItem = new MenuItem("インポート");
        MenuItem exitItem = new MenuItem("終了");
        
        exportItem.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null && selectedTab.getUserData() instanceof TodoTab) {
                TodoTab todoTab = (TodoTab) selectedTab.getUserData();
                TodoFileUtil.exportTodos(new ArrayList<>(todoTab.getTodoItems()), primaryStage);
            }
        });
        
        importItem.setOnAction(e -> {
            Tab selectedTab = tabPane.getSelectionModel().getSelectedItem();
            if (selectedTab != null && selectedTab.getUserData() instanceof TodoTab) {
                TodoTab todoTab = (TodoTab) selectedTab.getUserData();
                List<Todo> importedTodos = TodoFileUtil.importTodos(primaryStage);
                todoTab.setTodoItems(importedTodos);
            }
        });
        
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().addAll(exportItem, importItem, exitItem);

        // 編集メニュー
        Menu editMenu = new Menu("編集");
        MenuItem settingsItem = new MenuItem("設定");
        editMenu.getItems().add(settingsItem);

        // ヘルプメニュー
        Menu helpMenu = new Menu("ヘルプ");
        MenuItem aboutItem = new MenuItem("このアプリについて");
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        return menuBar;
    }

    private Tab createTab(String title, Object content) {
        Tab tab = new Tab(title);
        tab.setContent(((TabContent)content).getTabContent());
        tab.setUserData(content);
        tab.setClosable(false);
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
