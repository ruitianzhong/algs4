public class QuickUnion extends Points {

    QuickUnion(int N) {
        super(N);
    }

    @Override
    public void union(int p, int q) {
        verify(p);
        verify(q);
        int proot = find(p), qroot = find(q);
        if (proot == qroot) {
            return;
        }
        id[proot] = qroot;
        return;
    }

    @Override
    public boolean connected(int p, int q) {
        verify(p);
        verify(q);
        int proot = find(p), qroot = find(q);
        return proot == qroot;
    }

    protected int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }
}
