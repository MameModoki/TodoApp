package TodoApp;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class MemoTab {

    private ListView<Memo> memoListView = new ListView<>();

    public VBox getTabContent() {
        // メモ入力フィールド
        TextField memoInput = new TextField();
        Button addMemoButton = new Button("追加");
        addMemoButton.setOnAction(e -> {
            memoListView.getItems().add(new Memo(memoInput.getText()));
            memoInput.clear();
        });

        // メモタブコンテンツ
        VBox vbox = new VBox(10, memoInput, addMemoButton, memoListView);
        return vbox;
    }
}
