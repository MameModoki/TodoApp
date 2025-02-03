package com.example.todoapp.views;

import com.example.todoapp.models.Memo;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * メモを表示・管理するタブのUIコンポーネント
 * メモの追加、表示機能を提供します
 */
public class MemoTab {

    /** メモリストを表示するListView */
    private ListView<Memo> memoListView = new ListView<>();

    /**
     * タブのコンテンツを生成して返します
     * @return メモ管理用のUI要素を含むVBox
     */
    public VBox getTabContent() {
        // メモ入力フィールド
        TextField memoInput = new TextField();
        memoInput.setPromptText("メモを入力してください");

        // メモ追加ボタン
        Button addMemoButton = new Button("追加");
        addMemoButton.setOnAction(e -> {
            String content = memoInput.getText().trim();
            if (!content.isEmpty()) {
                memoListView.getItems().add(new Memo(content));
                memoInput.clear();
            }
        });

        // メモ削除ボタン
        Button deleteMemoButton = new Button("削除");
        deleteMemoButton.setOnAction(e -> {
            Memo selectedMemo = memoListView.getSelectionModel().getSelectedItem();
            if (selectedMemo != null) {
                memoListView.getItems().remove(selectedMemo);
            }
        });

        // メモタブコンテンツ
        VBox vbox = new VBox(10,
            memoInput,
            addMemoButton,
            deleteMemoButton,
            memoListView
        );
        vbox.setSpacing(10);
        return vbox;
    }
}
