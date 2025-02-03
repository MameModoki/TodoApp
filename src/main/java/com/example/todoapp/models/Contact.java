package com.example.todoapp.models;

import java.util.Objects;

/**
 * 連絡先情報を管理するクラス
 * 名前、電話番号、メールアドレスの基本的な連絡先情報を保持します
 */
public class Contact {

    /** 連絡先の名前 */
    private String name;
    /** 連絡先の電話番号 */
    private String phone;
    /** 連絡先のメールアドレス */
    private String email;

    /**
     * 連絡先を作成するコンストラクタ
     * @param name 連絡先の名前
     * @param phone 連絡先の電話番号
     * @param email 連絡先のメールアドレス
     */
    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters
    /**
     * 名前を取得します
     * @return 連絡先の名前
     */
    public String getName() {
        return name;
    }

    /**
     * 電話番号を取得します
     * @return 連絡先の電話番号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * メールアドレスを取得します
     * @return 連絡先のメールアドレス
     */
    public String getEmail() {
        return email;
    }

    // Setters
    /**
     * 名前を設定します
     * @param name 設定する名前
     * @throws IllegalArgumentException 名前がnullまたは空の場合
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("名前は空にできません");
        }
        this.name = name.trim();
    }

    /**
     * 電話番号を設定します
     * @param phone 設定する電話番号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * メールアドレスを設定します
     * @param email 設定するメールアドレス
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * オブジェクトの文字列表現を返します
     * @return "名前 | 電話番号 | メールアドレス" 形式の文字列
     */
    @Override
    public String toString() {
        return name + " | " + phone + " | " + email;
    }

    /**
     * このオブジェクトと他のオブジェクトが等しいかどうかを判定します
     * @param o 比較対象のオブジェクト
     * @return 名前、電話番号、メールアドレスがすべて一致する場合はtrue
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return name.equals(contact.name) &&
               phone.equals(contact.phone) &&
               email.equals(contact.email);
    }

    /**
     * このオブジェクトのハッシュコードを返します
     * @return オブジェクトのハッシュコード
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email);
    }
}
