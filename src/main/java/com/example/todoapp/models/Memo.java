package com.example.todoapp.models;

/**
 * メモ情報を管理するクラス
 */
public class Memo {

    /** メモの内容 */
    private String content;

    /**
     * メモを作成するコンストラクタ
     * @param content メモの内容
     */
    public Memo(String content) {
        this.content = content;
    }

    /**
     * メモの内容を取得します
     * @return メモの内容
     */
    public String getContent() {
        return content;
    }

    /**
     * メモの内容を設定します
     * @param content 設定する内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * オブジェクトの文字列表現を返します
     * @return メモの内容
     */
    @Override
    public String toString() {
        return content;
    }
}
