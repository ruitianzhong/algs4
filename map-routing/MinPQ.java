import javax.management.RuntimeErrorException;

public class MinPQ {

    private int n;
    private int[] pq;
    private int[] qp;

    private double[] weight;
    private int maxN;

    public MinPQ(int maxN) {
        this.maxN = maxN;
        weight = new double[maxN + 1];
        pq = new int[maxN + 1];
        qp = new int[maxN + 1];
        n = 0;
        for (int i = 0; i <= maxN; i++) {
            qp[i] = -1;
        }
    }

    public boolean contains(int i) {
        check(i);

        return qp[i] != -1;

    }

    private void check(int i) {
        if (i >= maxN) {
            throw new IllegalArgumentException();
        }
    }

    public void insert(int index, double weight) {
        if (contains(index) || n == maxN) {
            throw new IllegalArgumentException();
        }
        n++;
        pq[n] = index;
        qp[index] = n;
        this.weight[index] = weight;
        swim(n);
    }

    public void changeWeight(int index, double weight) {
        if (!contains(index)) {
            throw new IllegalArgumentException();
        }
        int pos = qp[index];
        this.weight[pos] = weight;
        swim(pos);
        sink(pos);

    }
    public boolean isEmpty(){
        return n == 0;
    }

    public void sink(int k) {
        while (k * 2 <= n) {
            int i = k * 2;
            if (k * 2 + 1 <= n && weight[pq[k * 2 + 1]] < weight[pq[i]]) {
                i++;
            }
            if (weight[pq[k]] > weight[pq[i]]) {
                exchange(i, k);
            }
            k = i;
        }

    }

    private void exchange(int i, int j) {
        int temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    public void swim(int k) {

        while (k > 1 && weight[pq[k]] < weight[pq[k / 2]]) {
            exchange(k, k / 2);
            k = k / 2;
        }

    }

    public int delMin() {
        if (n == 0) {
            throw new RuntimeErrorException(null);
        }
        int min = pq[1];
        exchange(1, n--);
        sink(1);
        qp[min] = -1;
        weight[min] = -1;
        pq[n + 1] = -1;
        return min;
    }

    public double getMin() {
        return weight[pq[1]];
    }

    // javac MinPQ.java;java -cp . -ea MinPQ
    public static void main(String[] args) {
        for (int n = 1; n < 300; n++) {
            double[] a = new double[n];
            MinPQ pq = new MinPQ(n);
            for (int j = 0; j < n; j++) {
                a[j] = j;
                pq.insert(j, n - j - 1);
            }
            for (int j = 0; j < n; j++) {
                double min = pq.getMin();
                assert (double) j == min;
                int pos = pq.delMin();
                assert pos == n - j - 1;
            }
            for (int j = 0; j < n; j++) {
                pq.insert(j, j);
            }
            for (int j = 0; j < n; j++) {
                double min = pq.getMin();
                assert (double) j == min;
                int pos = pq.delMin();
                assert pos == j;
            }
        }
        System.out.println("passed MinPQ unit test");

    }
}
