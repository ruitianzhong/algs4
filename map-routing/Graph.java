import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Graph {

    private ArrayList<LinkedList<Edge>> matrix;
    private int n;

    public Graph(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matrix.add(new LinkedList<Edge>());
        }
        this.n = n;
    }

    public void AddEdge(Edge e) {
        int from = e.from(), to = e.to();
        if (from == to) {
            throw new IllegalArgumentException();

        }
        double weight = e.weight();
        matrix.get(from).add(e);
        Edge ne = new Edge(to, from, weight);
        matrix.get(to).add(ne);
    }

    public ListIterator<Edge> adj(int i) {
        if (i >= n) {
            throw new IllegalArgumentException();
        }
        return matrix.get(i).listIterator();
    }

    // To run the unit test, run the following command:
    // javac Graph.java Edge.java; java -cp . -ea Graph
    public static void main(String[] args) {
        System.out.println("Graph Unit Test");
        Graph g = new Graph(6);

        g.AddEdge(new Edge(0, 1, 10));
        g.AddEdge(new Edge(1, 3, 20));
        assert g.adj(0).hasNext() && g.adj(1).hasNext();
        assert g.adj(3).hasNext();
        assert !g.adj(5).hasNext() && !g.adj(4).hasNext() && !g.adj(2).hasNext();
        Edge e;
        Iterator<Edge> it = g.adj(1);
        e = it.next();
        assert e.weight() == 10;
        e = it.next();
        assert e.weight() == 20;
        assert !it.hasNext();
    }
}
