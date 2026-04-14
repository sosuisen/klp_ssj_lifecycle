import module java.base;

public class MemoMap {
    private final Map<Integer, String> map = new HashMap<>();
    private final List<Writer> writers = new ArrayList<>();
    private int counter = 0;

    // 循環参照ができるように、
    // わざとWriterオブジェクトを登録するためのメソッドを用意してみる。
    public void register(Writer writer) {
        writers.add(writer);
    }

    public void put(String txt) {
        map.put(counter++, txt);
    }

    public void print() {
        for (var entry : map.entrySet()) {
            IO.println("memoMap: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
