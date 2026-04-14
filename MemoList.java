import module java.base;

public class MemoList {
    private final List<String> list = new ArrayList<>();
    private int counter = 0;

    public void add(String txt) {
        var newLine = "[%d] %s".formatted(counter++, txt);
        list.add(newLine);
    }

    public void print() {
        for (var line : list) {
            IO.println("memoList: " + line);
        }
    }
}
