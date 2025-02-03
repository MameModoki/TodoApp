package com.example.todoapp;

import com.example.todoapp.views.CalendarTab;
import com.example.todoapp.views.ContactTab;
import com.example.todoapp.views.MemoTab;
import com.example.todoapp.models.TodoTab;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * アプリケーションのメインクラス
 * Todo、メモ、連絡先、カレンダーの機能を統合したタブ型アプリケーションを提供します
 */
public class Main extends Application {

    /**
     * アプリケーションのエントリーポイント
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * JavaFXアプリケーションの起動時に呼び出されるメソッド
     * UIコンポーネントの初期化と表示を行います
     * @param primaryStage メインウィンドウ
     */
    @Override
    public void start(Stage primaryStage) {
        // TabPaneの作成
        TabPane tabPane = new TabPane();

        // Todoタブ
        TodoTab todoTab = new TodoTab();
        Tab todoTabItem = new Tab("Todo");
        todoTabItem.setContent(todoTab.getTabContent());
        todoTabItem.setClosable(false);

        // メモタブ
        MemoTab memoTab = new MemoTab();
        Tab memoTabItem = new Tab("メモ");
        memoTabItem.setContent(memoTab.getTabContent());
        memoTabItem.setClosable(false);

        // 連絡先タブ
        ContactTab contactTab = new ContactTab();
        Tab contactTabItem = new Tab("連絡先");
        contactTabItem.setContent(contactTab.getTabContent());
        contactTabItem.setClosable(false);

        // カレンダータブ
        CalendarTab calendarTab = new CalendarTab();
        Tab calendarTabItem = new Tab("カレンダー");
        calendarTabItem.setContent(calendarTab.getTabContent());
        calendarTabItem.setClosable(false);

        // タブを追加
        tabPane.getTabs().addAll(todoTabItem, memoTabItem, contactTabItem, calendarTabItem);

        // シーンのセットアップ
        Scene scene = new Scene(tabPane, 500, 500);
        primaryStage.setTitle("統合アプリ");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
