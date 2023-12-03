import java.util.Iterator;
import java.util.Stack;

public class Dijkstra {
    private double[] distTo;
    private Edge[] edgeTo;
    private Graph graph;
    private MinPQ pq;

    public Dijkstra(Graph g, int s) {
        this.graph = g;
        if (s >= g.vertex() || s <= 0) {
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
        }
    }

    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<Edge> p = new Stack<>();
        Edge e = edgeTo[v];
        while (e != null) {
            p.push(e);
        }

        return p;
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public static void main(String[] args) {

    }
}
