package com.example.todoapp.views;

import java.time.LocalDate;

import com.example.todoapp.models.Memo;
import com.example.todoapp.models.Memo.Priority;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MemoTab implements TabContent {

    private ListView<Memo> memoListView = new ListView<>();

    public VBox getTabContent() {
        // メモ入力フィールド
        TextField memoInput = new TextField();
        Button addMemoButton = new Button("追加");
        addMemoButton.setOnAction(e -> {
            memoListView.getItems().add(new Memo(memoInput.getText(), Priority.中, LocalDate.now()));
            memoInput.clear();
        });

        // メモタブコンテンツ
        VBox vbox = new VBox(10, memoInput, addMemoButton, memoListView);
        return vbox;
    }
}
