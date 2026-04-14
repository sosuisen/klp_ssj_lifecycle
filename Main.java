import java.lang.ref.Cleaner;

public class Main {
    private MemoMap memoMap = new MemoMap();
    private final Cleaner cleaner = Cleaner.create();

    void main() throws InterruptedException {
        cleaner.register(memoMap, () -> IO.println("memoMap(aggregation) is destructed."));

        test();
        System.gc();
        Thread.sleep(3000);

        memoMap = null;
        System.gc();
        Thread.sleep(3000);
    }

    void test() {
        // memoMapオブジェクトはWriterクラスの外部で生成して、コンストラクタ経由で渡します。
        var writer = new Writer(memoMap);

        cleaner.register(writer, () -> IO.println("writer is destructed."));
        writer.addLine("hello");
        writer.addLine("world");
        writer.print();
    }
}