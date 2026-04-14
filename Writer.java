import java.lang.ref.Cleaner;

public class Writer {
    private final MemoMap memoMap;
    private final MemoList memoList;

    private final Cleaner cleaner = Cleaner.create();

    public Writer(MemoMap memoMap) {
        /*
         * MemoMapオブジェクトはWriterクラスの外部で生成され、
         * コンストラクタ経由で渡されて、フィールドに保持されます。
         * この構造は「集約」（aggregation）と呼ばれます。
         * 
         * このとき、MemoMapオブジェクトは、必ず外部からも参照されています。
         * 結果として、WriterオブジェクトがGCの対象になっても、
         * MemoMapオブジェクトはGCの対象になりません。
         */
        this.memoMap = memoMap;

        /*
         * MemoListオブジェクトはWriterクラスの内部で生成され、
         * フィールドに保持されます。
         * この構造は「コンポジション」（composition）と呼ばれます。
         * 
         * このとき、MemoListオブジェクトは、privateにして、getterも持たない限り、
         * 外部からは参照されません。
         * この場合、WriterオブジェクトがGCの対象になると、
         * MemoListオブジェクトもGCの対象になります。
         */
        this.memoList = new MemoList();
        cleaner.register(memoList, () -> IO.println("memoList(composition) is destructed."));
    }

    /*
     * dateは、このメソッド内でしか利用されないローカル変数で、
     * 代入した文字列オブジェクトも、このメソッド内でしか利用されません。
     * よって、メソッドの処理が終われば、すぐにGCの対象になります。
     * (寿命が短い)
     *
     * 一方、フィールドのオブジェクト（memoMap, memoList）は、
     * WriterオブジェクトがGCの対象になるまで、GCの対象になりません。
     * （寿命が長い）
     * このおかげで、memoMapとmemoListは複数のメソッドで利用することができます。
     * （addLine, printの両方で利用）
     *
     * フィールドのオブジェクトは便利ですが、いろんな場所から呼び出すことが
     * できるため、どこかで予想外の変更が加えられる可能性があります。
     * つまり、クラス設計では、フィールドのオブジェクトの寿命により注目する必要があります。
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
