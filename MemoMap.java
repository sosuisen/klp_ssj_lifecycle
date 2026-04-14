import module java.base;

public class MemoMap {
    private final Map<Integer, String> map = new HashMap<>();
    private int counter = 0;

    public void put(String txt) {
        map.put(counter++, txt);
    }

    public void print() {
        for (var entry : map.entrySet()) {
            IO.println("memoMap: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}
