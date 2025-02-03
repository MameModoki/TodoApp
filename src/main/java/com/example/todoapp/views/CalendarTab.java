package com.example.todoapp.views;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.time.LocalDate;

/**
 * カレンダーを表示・管理するタブのUIコンポーネント
 * イベントの追加、表示機能を提供します
 */
public class CalendarTab {

    /** イベントリストを表示するListView */
    private ListView<String> eventListView = new ListView<>();

    /**
     * タブのコンテンツを生成して返します
     * @return カレンダー管理用のUI要素を含むVBox
     */
    public VBox getTabContent() {
        // 日付選択
        DatePicker calendarDatePicker = new DatePicker();
        calendarDatePicker.setPromptText("日付を選択してください");

        // イベント入力フィールド
        TextField eventInput = new TextField();
        eventInput.setPromptText("イベントを入力してください");

        // イベント追加ボタン
        Button addEventButton = new Button("イベントを追加");
        addEventButton.setOnAction(e -> {
            LocalDate selectedDate = calendarDatePicker.getValue();
            String eventText = eventInput.getText().trim();
            
            if (selectedDate != null && !eventText.isEmpty()) {
                String event = selectedDate + ": " + eventText;
                eventListView.getItems().add(event);
                eventInput.clear();
                calendarDatePicker.setValue(null);
            }
        });

        // イベント削除ボタン
        Button deleteEventButton = new Button("削除");
        deleteEventButton.setOnAction(e -> {
            String selectedEvent = eventListView.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                eventListView.getItems().remove(selectedEvent);
            }
        });

        // 入力部分をまとめる
        HBox inputBox = new HBox(10,
            calendarDatePicker,
            eventInput
        );

        // ボタンをまとめる
        HBox buttonBox = new HBox(10,
            addEventButton,
            deleteEventButton
        );

        // カレンダータブコンテンツ
        VBox vbox = new VBox(10,
            inputBox,
            buttonBox,
            eventListView
        );
        vbox.setSpacing(10);
        return vbox;
    }
}
