package com.example.todoapp.views;

import com.example.todoapp.models.Contact;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import com.example.todoapp.models.Contact;

/**
 * 連絡先を表示・管理するタブのUIコンポーネント
 * 連絡先の追加、表示機能を提供します
 */
public class ContactTab {

    /** 連絡先リストを表示するListView */
    private ListView<Contact> contactListView = new ListView<>();

    /**
     * タブのコンテンツを生成して返します
     * @return 連絡先管理用のUI要素を含むVBox
     */
    public VBox getTabContent() {
        // 連絡先入力フィールド
        TextField nameInput = new TextField();
        nameInput.setPromptText("名前を入力してください");

        TextField phoneInput = new TextField();
        phoneInput.setPromptText("電話番号を入力してください");

        TextField emailInput = new TextField();
        emailInput.setPromptText("メールアドレスを入力してください");

        // 連絡先追加ボタン
        Button addContactButton = new Button("追加");
        addContactButton.setOnAction(e -> {
            String name = nameInput.getText().trim();
            String phone = phoneInput.getText().trim();
            String email = emailInput.getText().trim();

            if (!name.isEmpty()) {
                contactListView.getItems().add(new Contact(name, phone, email));
                // 入力フィールドをクリア
                nameInput.clear();
                phoneInput.clear();
                emailInput.clear();
            }
        });

        // 連絡先削除ボタン
        Button deleteContactButton = new Button("削除");
        deleteContactButton.setOnAction(e -> {
            Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
            if (selectedContact != null) {
                contactListView.getItems().remove(selectedContact);
            }
        });

        // 連絡先タブコンテンツ
        VBox vbox = new VBox(10,
            nameInput,
            phoneInput,
            emailInput,
            addContactButton,
            deleteContactButton,
            contactListView
        );
        vbox.setSpacing(10);
        return vbox;
    }
}
