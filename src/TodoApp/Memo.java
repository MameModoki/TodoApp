package TodoApp;

public class Memo {

    private String content;

    public Memo(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
