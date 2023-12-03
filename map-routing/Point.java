public class Point {
    private int x;
    private int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public double distance(Point p){
        int xq = (p.x-this.x)*(p.x-this.x),yq=(p.y-this.y)*(p.y-this.y);
        return Math.sqrt(xq + yq);
    }
}
