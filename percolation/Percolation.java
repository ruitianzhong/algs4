public class Percolation {
    private Points p;
    private int count;
    private boolean open[];

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        count = N;
        p = new WQUFwithPathCompression(N * N + 2);
        open = new boolean[N * N];
        int total = N * N;
        for (int i = 0; i < N; i++) {
            p.union(total, i);
            p.union(total + 1, total- 1 - i);
        }

    }
    
    public Percolation(int N, AlgorithmType type) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        count = N;
        int temp = N * N + 2;
        switch (type) {
            case QuickFind:
                p = new QuickFind(temp);
                break;
            case QuickUnion:
                p = new QuickUnion(temp);
                break;
            case WeightedQuickUnion:
                p = new WeightedQuickUnion(temp);
                break;
            case WQUFwithPathCompression:
                p = new WQUFwithPathCompression(temp);
                break;
            default:
                throw new UnsupportedOperationException(null, null);
        }
        open = new boolean[N * N];
        int total = N * N;
        for (int i = 0; i < N; i++) {
            p.union(total, i);
            p.union(total + 1, total - 1 - i);
        }
    }

    public void open(int i, int j) {
        check(i);
        check(j);
        open[(i - 1) * count + j - 1] = true;
        safe_union(i, j, i + 1, j);
        safe_union(i, j, i, j + 1);
        safe_union(i, j, i - 1, j);
        safe_union(i, j, i, j - 1);

    }

    private void safe_union(int x, int y, int neighborX, int neighborY) {
        if (safe_check(neighborX) && safe_check(neighborY) && isOpen(neighborX, neighborY)) {
            p.union((x - 1) * count + (y - 1), (neighborX - 1) * count + (neighborY - 1));
        }

    }

    private void check(int x) {
        if (!(x >= 1 && x <= count)) {
            throw new IndexOutOfBoundsException(x);
        }
    }

    private boolean safe_check(int x) {
        return x >= 1 && x <= count;
    }

    public boolean isOpen(int i, int j) {
        check(j);
        check(i);
        return open[(i - 1) * count + j - 1];
    }

    public boolean isFull(int i, int j) {
        check(j);
        check(i);
        boolean top = false, bottom = false;
        int idx = (i - 1) * count + j - 1;
        top = p.connected(count * count, idx);
        bottom = p.connected(count * count + 1, idx);

        return top && bottom;
    }

    public boolean percolates() {
        return p.connected(count * count, count * count + 1);
    }

    public static void main(String[] args) {

    }

}
