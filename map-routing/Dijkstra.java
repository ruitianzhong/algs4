import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class Dijkstra {
    private double[] distTo;
    private Edge[] edgeTo;
    private Graph graph;
    private MinPQ pq;
    private int start;
    private int mode;
    private boolean init = false;
    private int[] modify;
    int top = -1;
    private boolean check = false;

    public Dijkstra(Graph g, int s) {
        this.graph = g;
        if (s >= g.vertex() || s < 0) {
            throw new IllegalArgumentException();
        }
        distTo = new double[graph.vertex()];

        edgeTo = new Edge[graph.vertex()];
        pq = new MinPQ(graph.vertex());
        for (int i = 0; i < graph.vertex(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            int index = pq.delMin();
            Iterator<Edge> it = graph.adj(index);
            while (it.hasNext()) {
                relax(it.next());
            }
        }
        this.start = s;
        this.init = true;

    }

    public Dijkstra(int mode, Graph g) {

        this.mode = mode;
        this.graph = g;
        distTo = new double[graph.vertex()];
        // init all data structure
        edgeTo = new Edge[graph.vertex()];
        pq = new MinPQ(graph.vertex());
        for (int i = 0; i < graph.vertex(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        if (mode == 2) {
            modify = new int[graph.vertex()];
        }
    }

    private void sanityCheck() {
        if (check) {
            for (int i = 0; i < graph.vertex(); i++) {
                if (distTo[i] != Double.POSITIVE_INFINITY || edgeTo[i] != null) {
                    throw new IllegalStateException();
                }
            }
        }
    }

    public void compute(int src, int dst) {
        if (init) {
            throw new UnsupportedOperationException();
        }
        if (mode == 1) {
            slowReinit();
        } else if (mode == 2) {
            fastReinit();
        } else {
            throw new UnsupportedOperationException();
        }
        sanityCheck();

        distTo[src] = 0;
        edgeTo[src] = null;
        if (mode == 2) {
            modify[++top] = src;
        }
        pq = new MinPQ(graph.vertex());
        pq.insert(src, 0.0);
        while (!pq.isEmpty()) {
            int index = pq.delMin();
            if (index == dst) {
                break;
            }
            Iterator<Edge> it = graph.adj(index);
            while (it.hasNext()) {
                relax(it.next());
            }
        }
        this.start = src;
    }

    private void fastReinit() {
        while (top != -1) {
            int i = modify[top--];
            distTo[i] = Double.POSITIVE_INFINITY;
            edgeTo[i] = null;
        }
    }

    private void slowReinit() {
        for (int i = 0; i < graph.vertex(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
            edgeTo[i] = null;
        }

    }

    private void relax(Edge e) {
        int to = e.to(), from = e.from();
        if (distTo[to] > distTo[from] + e.weight()) {
            if (mode == 2 && (distTo[to] == Double.POSITIVE_INFINITY)) {
                modify[++top] = to;
            }
            distTo[to] = distTo[from] + e.weight();
            if (pq.contains(to)) {
                pq.changeWeight(to, distTo[to]);
            } else {
                pq.insert(to, distTo(to));
            }
            edgeTo[to] = e;
        }
    }

    public void pathTo(int v) {
        if (!hasPathTo(v)) {
            System.out.println("No path from " + start + " to " + v);
        }
        Stack<Edge> p = new Stack<>();
        Edge e = edgeTo[v];
        while (e != null) {
            p.push(e);
            e = edgeTo[e.from()];
        }
        System.out.print("start: " + start);
        while (!p.empty()) {
            e = p.pop();
            System.out.print(" -> " + e.to());
        }
        System.out.print(" total:" + distTo[v]);
        System.out.println();

    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public static long testHelper(int tests, int mode, Graph g) {
        long start = System.currentTimeMillis(), end;
        Random r = new Random(start);
        if (mode == 0) {
            for (int i = 0; i < tests; i++) {
                int s = r.nextInt(g.vertex()), d = r.nextInt(g.vertex());
                Dijkstra dj = new Dijkstra(g, s);
                dj.distTo(d);
            }
            end = System.currentTimeMillis();
            return (end - start);

        } else {
            Dijkstra dj = new Dijkstra(mode, g);
            for (int i = 0; i < tests; i++) {
                int s = r.nextInt(g.vertex()), d = r.nextInt(g.vertex());
                dj.compute(s, d);
                dj.distTo(d);
            }
            end = System.currentTimeMillis();
            return end - start;
        }

    }

    // unit test for Dijkstra Shortest Path Algorithm
    public static void main(String[] args) throws FileNotFoundException {
        Graph g1 = ReadGraph.readGraph("small.txt");
        Dijkstra d1 = new Dijkstra(g1, 0);
        System.out.println(d1.distTo(0));
        System.out.println("start " + 0);
        System.out.println(d1.distTo(5));
        d1.pathTo(5);

        Graph g2 = ReadGraph.readGraph("input.txt");
        Dijkstra d2 = new Dijkstra(g2, 0);

        int test1 = 1000;
        // without any optimization lot of query
        System.out.println("Without any optimization time spent: " +
                testHelper(test1, 0, g2));
        // exit the moment the destination is found
        System.out.println("mode 1 time spent: " + testHelper(test1, 1, g2));
        // exit the moment the destination is found and fast re-initialization
        System.out.println("mode 2 time spent: " + testHelper(test1, 2, g2));

        while (true) {
            System.out.println("please enter the start:");
            Scanner sc = new Scanner(System.in);
            int start = sc.nextInt();
            System.out.println("please enter end:");
            int end = sc.nextInt();
            d2.pathTo(end);
        }

    }
}
