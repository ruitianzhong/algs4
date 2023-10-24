import java.util.Random;

// javac *.java;java -ea -classpath . test
public class test {
    public static void main(String[] args) {
        correctnessTest();
        perfomanceTest(10000, TestType.Random);
        perfomanceTest(50000, TestType.Random);
        perfomanceTest(100000, TestType.Random);

        perfomanceTest(10000, TestType.Desc);
        perfomanceTest(10000, TestType.Duplicate);

    }

    public static int[] generateRandom(int n) {
        int[] arr = new int[n];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < n; i++) {
            arr[i] = r.nextInt(100);
        }
        return arr;
    }

    public static boolean verify(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                printArr(arr);
                return false;
            }
        }

        return true;
    }

    public static void printArr(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i]);
        }
        System.out.println();
    }

    private static void correctnessTest() {
        int[] correct = generateRandom(10);
        IS is = new IS();
        TDM tdm = new TDM();
        BUM bum = new BUM();
        RQ rq = new RQ();
        QD3P qd3p = new QD3P();
        is.sort(correct);
        assert verify(correct);
        correct = generateRandom(100);
        tdm.sort(correct);
        assert verify(correct);
        correct = generateRandom(100);
        bum.sort(correct);
        assert verify(correct);
        correct = generateRandom(100);
        rq.sort(correct);
        assert verify(correct);
        correct = generateRandom(100);
        qd3p.sort(correct);
        assert verify(correct);
        correctnessTestHelper(qd3p);
        correctnessTestHelper(tdm);
        correctnessTestHelper(bum);
        correctnessTestHelper(rq);
        correctnessTestHelper(is);
        System.out.println("Passed correctness test");

    }

    public static void correctnessTestHelper(Sort sort) {
        int[] n = { 100, 1000, 10000, 3333 };
        for (int i = 0; i < n.length; i++) {
            int[] temp;
            temp = generateDesc(n[i]);
            sort.sort(temp);
            verify(temp);
            temp = generateDup(n[i]);
            sort.sort(temp);
            verify(temp);
            temp = generateRandom(n[i]);
            sort.sort(temp);
            verify(temp);
        }

    }

    public static int[] generateDup(int n) {
        int[] arr = new int[n];
        Random r = new Random(System.currentTimeMillis());
        int num = r.nextInt();
        for (int i = 0; i < n; i++) {
            arr[i] = num;
        }
        return arr;
    }

    public static int[] generateDesc(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }

    enum TestType {
        Random, Duplicate, Desc
    }

    public static void perfomanceTest(int scale, TestType type) {
        long[][] temporal = new long[5][10];
        long[][] memory = new long[5][10];
        for (int i = 0; i < 10; i++) {
            Sort[] s = new Sort[5];
            int[] arr;

            switch (type) {
                case Desc:
                    arr = generateDesc(scale);
                    break;
                case Duplicate:
                    arr = generateDup(scale);
                    break;
                case Random:
                    arr = generateRandom(scale);
                    break;
                default:
                    throw new UnsupportedOperationException(type.name());

            }

            s[0] = new IS();
            s[1] = new TDM();
            s[2] = new BUM();
            s[3] = new RQ();
            s[4] = new QD3P();
            long start, end;
            for (int j = 0; j < 5; j++) {
                int[] ephemeral = arr.clone();
                start = System.nanoTime();
                s[j].sort(ephemeral);
                end = System.nanoTime();
                temporal[j][i] = end - start;
                memory[j][i] = s[j].memory();
            }
        }
        printResult("Time scale " + scale + " " + type.name(), temporal);
        System.out.println("Memory scale " + scale + " " + type.name());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {

                double t = (double) (memory[i][j]) / (double) 1024;
                System.out.printf("%.3f,", t);
            }
            System.out.println();
        }

    }

    public static void printResult(String tag, long[][] result) {
        System.out.println("TAG: " + tag);
        String[] s = { "IS", "TDM", "BUM", "RQ", "QD3P" };
        for (int i = 0; i < s.length; i++) {
            System.out.printf("%s,", s[i]);
            for (int j = 0; j < 10; j++) {
                System.out.printf("%d,", result[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

}
