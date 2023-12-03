import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReadGraph {

    public static Graph readGraph(String filename) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new FileReader(filename))) {
            String line = sc.nextLine();

            String[] number = line.split("  *");
            int v = Integer.parseInt(number[0]), e = Integer.parseInt(number[1]);
            System.out.printf("vertex:%d,edge:%d\n", v, e);
            Graph g = new Graph(v);
            Point[] p = new Point[v];
            for (int i = 0; i < v; i++) {
                line = sc.nextLine();
                number = line.split("\\s+");
                int vn = Integer.parseInt(number[1]), x = Integer.parseInt(number[2]), y = Integer.parseInt(number[3]);
                p[vn] = new Point(x, y);
            }
            sc.nextLine(); // read blank line in the txt file

            for (int i = 0; i < e; i++) {
                line = sc.nextLine();
                number = line.split("\\s+");
                int n1 = Integer.parseInt(number[1]), n2 = Integer.parseInt(number[2]);
                if (n1 == n2) {
                    throw new IllegalArgumentException();
                }

                g.AddEdge(new Edge(n1, n2, p[n1].distance(p[n2])));
            }
            assert !sc.hasNextLine();

            sc.close();

            System.out.println("Succeed to create the graph");
            return g;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        readGraph("input.txt");

    }

}
