package com.example.todoapp.views;

import com.example.todoapp.models.Contact;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ContactTab implements TabContent {

    private ListView<Contact> contactListView = new ListView<>();

    public VBox getTabContent() {
        // 連絡先入力フィールド
        TextField nameInput = new TextField();
        TextField phoneInput = new TextField();
        TextField emailInput = new TextField();
        Button addContactButton = new Button("追加");
        addContactButton.setOnAction(e -> {
            contactListView.getItems().add(new Contact(nameInput.getText(), phoneInput.getText(), emailInput.getText()));
            nameInput.clear();
            phoneInput.clear();
            emailInput.clear();
        });

        // 連絡先タブコンテンツ
        VBox vbox = new VBox(10, nameInput, phoneInput, emailInput, addContactButton, contactListView);
        return vbox;
    }
}
