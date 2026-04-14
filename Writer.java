public class Writer {
    private final MemoList memoList;
    private final MemoMap memoMap;

    public Writer(MemoMap memoMap) {
        this.memoMap = memoMap;
        this.memoList = new MemoList();

        // わざとmemoMapにWriterオブジェクトを登録して、
        // 循環参照を作ってみる。
        memoMap.register(this);
    }

    public void addLine(String line) {
        // 実験結果が判りやすいように、memoListにのみデータを追加します。
        var date = java.time.LocalDate.now().toString();
        memoList.add(line + " (" + date + ")");
    }

    public void print() {
        memoList.print();
    }
}
