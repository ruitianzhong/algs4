public class WQUFwithPathCompression extends Points {

    private int[] size;

    public WQUFwithPathCompression(int N) {
        super(N);
        size = new int[N];
        for (int i = 0; i < N; i++) {
            size[i] = 1;
        }

    }

    @Override
    boolean connected(int p, int q) {
        verify(p);
        verify(q);
        return find(p) == find(q);
    }

    @Override
    void union(int p, int q) {
        verify(p);
        verify(q);
        int proot = find(p), qroot = find(q);
        if (proot == qroot) {
            return;
        }
        if (size[proot] < size[qroot]) {
            id[proot] = qroot;
            size[qroot] += size[proot];
        } else {
            id[qroot] = proot;
            size[proot] += size[qroot];
        }
    }

    private int find(int p) {
        while (p != id[p]) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }

}