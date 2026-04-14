import java.lang.ref.Cleaner;

public class Main {
    private final Cleaner cleaner = Cleaner.create();

    void main() throws InterruptedException {
        var memoMap = new MemoMap();
        cleaner.register(memoMap, () -> IO.println("memoMap(aggregation) is destructed."));

        test(memoMap);

        // System.gc();
        // Thread.sleep(3000);

        // memoMap = null;
        // System.gc();
        // Thread.sleep(3000);
    }

    /*
     * Writerオブジェクトは、testメソッド内で生成されて、
     * ローカル変数writerに代入されます。
     * そのため、（循環参照がないよう正しく設計されていれば）
     * testメソッドの処理が終わったとき、すぐにGCの対象になります。
     * (寿命が短い)
     */
    void test(MemoMap memoMap) {
        // MemoMapオブジェクトはWriterクラスの外部で生成して、コンストラクタ経由で渡します。
        var writer = new Writer(memoMap);

        cleaner.register(writer, () -> IO.println("writer is destructed."));
        writer.addLine("hello");
        writer.addLine("world");
        writer.print();
    }
}