public class Main {
    private MemoMap memoMap = new MemoMap();

    void main() throws InterruptedException {
        var rt = Runtime.getRuntime();

        int max = 50_000;
        for (int i = 1; i <= max; i++) {
            test();

            if (i % 10_000 == 0) {
                System.gc();
                var usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
                IO.println("count " + i + " used: " + usedMB + " MB");
            }
        }
    }

    void test() {
        var writer = new Writer(memoMap);

        writer.addLine("hello!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        writer.addLine("world!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}