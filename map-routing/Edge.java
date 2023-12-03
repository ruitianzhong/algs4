public class Edge {
    private double weight;
    private int from;
    private int to;

    public Edge(int from, int to, double weight) {
        this.from = from;
        this.weight = weight;
        this.to = to;
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
    }

    public int from() {
        return this.from;
    }

    public int to() {
        return this.to;
    }

    public double weight() {
        return this.weight;
    }
}
