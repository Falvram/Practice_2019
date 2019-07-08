public class Edge implements Comparable<Edge> {
    private final int u;
    private final int v;
    private final double weight;
    public Edge(int u, int v, double w) {
        this.u = u;
        this.v = v;
        this.weight = w;
    }
    public double weight() {
        return weight;
    }
    public int first() {
        return u;
    }
    public int second(int u) {
        if(u == this.u) return v;
        else if (u == v) return this.u;
        else throw new IllegalArgumentException("Illegal endpoint");
    }
    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }
    public String toString() {
        return String.format("%d-%d %.1f", u, v, weight);
    }
    public boolean isEqual(Edge other){
        return (other.v == this.v) && (other.u == this.u);
    }
}
