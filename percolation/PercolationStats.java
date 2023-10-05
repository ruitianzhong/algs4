import java.util.List;
import java.util.Random;
import java.util.ArrayList;

// java 17.0.1
// compile command :
// cd percolation; javac *.java;java -classpath . PercolationStats
public class PercolationStats {

    private double[] result;
    private int N;
    private Random random;
    private double avgTime = 0;
      
    public PercolationStats(int N, int T) {

        result = new double[T];
        this.N = N;
        random = new Random(System.currentTimeMillis());

        for (int i = 0; i < T; i++) {
            test(i, AlgorithmType.QuickFind);
        }

    }

    public PercolationStats(int N, int T, AlgorithmType type) {
        result = new double[T];
        this.N = N;
        random = new Random(System.currentTimeMillis());

        for (int i = 0; i < T; i++) {
            test(i, type);
        }

    }

    private void test(int num, AlgorithmType type) {
        Percolation p = new Percolation(N, type);
        List<Integer> blockedX = new ArrayList<Integer>();
        List<Integer> blockedY = new ArrayList<Integer>();
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                blockedX.add(i);
                blockedY.add(j);
            }
        }
        int count = 0;
        while (!p.percolates()) {
            int idx = random.nextInt(blockedX.size());
            int x = blockedX.get(idx), y = blockedY.get(idx);
            blockedX.remove(idx);
            blockedY.remove(idx);
            p.open(x, y);
            count++;
        }

        result[num] = ((double) count) / ((double) N * N);

    }
    public double getAverageTime(){
        return this.avgTime;
    }
    public void setAverageTime(double time){
        this.avgTime = time;
    }
    public double mean() {
        double sum = 0;
        for (int i = 0; i < result.length; i++) {
            sum += result[i];
        }
        double ret = ((double) sum) / ((double) result.length);
        return ret;
    }

    public double stddev() {
        double average = mean();
        double sum = 0;
        for (int i = 0; i < result.length; i++) {
            double x = result[i] - average;
            sum += x * x;
        }
        return Math.sqrt(sum / (result.length - 1));
    }

    public double confidenceLo() {
        double x = mean(), y = stddev();

        return x - (1.96 * y / Math.sqrt(result.length));
    }

    public double confidenceHi() {
        double x = mean(), y = stddev();

        return x + (1.96 * y / Math.sqrt(result.length));
    }

    public static void main(String[] args) {
        testHelper(10, 100, AlgorithmType.WeightedQuickUnion);
        testHelper(30, 100, AlgorithmType.WeightedQuickUnion);
        testHelper(100, 100, AlgorithmType.WeightedQuickUnion);
        testHelper(200, 100, AlgorithmType.WeightedQuickUnion);
        int i = 5;
        while (i <= 320) {
            PercolationStats p = testHelper(i, 40, AlgorithmType.WeightedQuickUnion);
            System.out.print(p.getAverageTime() + ",");
            i *= 2;
        }
        i = 5;
        System.out.println();
        while (i <= 320) {
            PercolationStats p = testHelper(i, 40, AlgorithmType.QuickFind);
            System.out.print(p.getAverageTime() + ",");
            i *= 2;
        }

    }

    private static PercolationStats testHelper(int N,int T,AlgorithmType type){
        long start = System.currentTimeMillis(), end;
        // System.out.println(type.name());
        PercolationStats p = new PercolationStats(N, T, type);
        end = System.currentTimeMillis();
        // System.out.println("mean: " + p.mean());
        // System.out.println("stddev: " + p.stddev());
        // System.out.println("confidenceLo: " + p.confidenceLo());
        // System.out.println("confidenceHi: " + p.confidenceHi());
        // System.out.println("time spent on average:" + (float)(end-start)/(float)T + " ms");
        p.setAverageTime((float)(end-start)/(float)T);
        // System.out.println();
        return p;
    }
}
