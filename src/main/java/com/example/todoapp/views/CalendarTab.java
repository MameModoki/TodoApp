package com.example.todoapp.views;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

public class CalendarTab implements TabContent {

    public VBox getTabContent() {
        DatePicker calendarDatePicker = new DatePicker();
        Button addEventButton = new Button("イベントを追加");
        addEventButton.setOnAction(e -> {
            System.out.println("選ばれた日付: " + calendarDatePicker.getValue());
        });

        // カレンダータブコンテンツ
        VBox vbox = new VBox(10, calendarDatePicker, addEventButton);
        return vbox;
    }
}
