import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class Dijkstra {
    private double[] distTo;
    private Edge[] edgeTo;
    private Graph graph;
    private MinPQ pq;
    private int start;

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

    }

    private void relax(Edge e) {
        int to = e.to(), from = e.from();
        if (distTo[to] > distTo[from] + e.weight()) {
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
