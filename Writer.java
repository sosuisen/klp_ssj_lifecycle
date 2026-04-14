import java.lang.ref.Cleaner;

public class Writer {
    private final MemoMap memoMap;
    private final MemoList memoList;

    private final Cleaner cleaner = Cleaner.create();

    public Writer(MemoMap memoMap) {
        /*
         * MemoMapオブジェクトはWriterクラスの外部で生成され、
         * コンストラクタ経由で渡されて、フィールドに保持されます。
         * この関係は「集約」（aggregation）と呼ばれます。
         * 
         * このとき、MemoMapオブジェクトは、必ず外部からも参照されています。
         * 結果として、WriterオブジェクトがGC候補になっても、
         * MemoMapオブジェクトはGC候補になりません。
         */
        this.memoMap = memoMap;

        /*
         * MemoListオブジェクトはWriterクラスの内部で生成され、
         * フィールドに保持されます。
         * この関係は「コンポジション」（composition）と呼ばれます。
         * 
         * MemoListオブジェクトは、privateかつgetterも持たない限り、
         * 外部からは参照されません。
         * この場合、WriterオブジェクトがGC候補になると、
         * MemoListオブジェクトもGC候補になります。
         */
        this.memoList = new MemoList();
        cleaner.register(memoList, () -> IO.println("memoList(composition) is destructed."));
    }

    /*
     * dateは、このメソッド内でしか利用されないローカル変数で、
     * 代入した文字列オブジェクトも、このメソッド内でしか利用されません。
     * よって、メソッドの処理が終われば、すぐにGC候補になります。
     * (寿命が短い)
     *
     * 一方、フィールドのオブジェクト（memoMap, memoList）は、
     * 少なくともWriterオブジェクトがGC候補になるまでは、GC候補になりません。
     * （寿命が長い）
     * このおかげで、memoMapとmemoListは複数のメソッドで利用することができます。
     * （addLine, printの両方で利用）
     *
     * フィールドのオブジェクトは便利ですが、いろんな場所から呼び出すことが
     * できるため、どこかで予想外の変更が加えられる可能性があります。
     * 一般に、クラス設計では、ローカル変数よりもフィールドのオブジェクトの寿命について
     * より注目する必要があります。
     */
    public void addLine(String line) {
        var date = java.time.LocalDate.now().toString();
        memoMap.put(line + " (" + date + ")");
        memoList.add(line + " (" + date + ")");
    }

    public void print() {
        memoList.print();
        memoMap.print();
    }
}
