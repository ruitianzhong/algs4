
public class WeightedQuickUnion extends Points {
    private int[] size;

    public WeightedQuickUnion(int N) {
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
        p = find(p);
        q = find(q);
        return p == q;
    }

    private int find(int x) {
        while (id[x] != x) {
            x = id[x];
        }
        return x;
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
            size[qroot] += size[proot];
        }

    }

}
