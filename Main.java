public class Main {

    void main() throws InterruptedException {
        var memoMap = new MemoMap();

        var rt = Runtime.getRuntime();

        int max = 50_000;
        for (int i = 1; i <= max; i++) {
            test(memoMap);

            if (i % 10_000 == 0) {
                System.gc();
                var usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
                IO.println("count " + i + " used: " + usedMB + " MB");
            }
        }
    }

    void test(MemoMap memoMap) {
        var writer = new Writer(memoMap);
        // memoMap.register(writer);

        writer.addLine("hello!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        writer.addLine("world!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}