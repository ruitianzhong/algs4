public abstract class Points {
    protected int[] id;
    protected int count;

    public Points(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        id = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    protected void verify(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException();
        }
    }

    abstract boolean connected(int p, int q); // need to be implemented

    abstract void union(int p, int q); // need to be implemented
}
