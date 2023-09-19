public class QuickFind extends Points {

    public QuickFind(int N) {
        super(N);
    }

    @Override
    boolean connected(int p, int q) {
        verify(p);
        verify(q);
        return id[p] == id[q];
    }

    @Override
    void union(int p, int q) {
        verify(p);
        verify(q);
        int pid = id[p], qid = id[q];

        if (pid == qid) {
            return;
        }
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] = qid;
            }
        }
        return;
    }

}
