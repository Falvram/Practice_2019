import java.util.LinkedList;

public class AlgorithmBoruvki {
    private LinkedList<Edge> mst = new LinkedList<>();
    private int iteration = 0;
    public LinkedList<LinkedList<Edge>> memento = new LinkedList<>();
    private double weight = 0.0;

    public AlgorithmBoruvki(Graph graph){
        LinkedList<Edge> zeroStep = new LinkedList<>();
        memento.addLast(zeroStep);
        UnionFind uf = new UnionFind(graph.V());
        LinkedList<Integer> listOfVertexes = graph.vertexes();
        for (int t = 1; t < graph.V() && mst.size() < graph.V() - 1; t = t + t) {
            Edge[] closest = new Edge[graph.V()];
            for (Edge e : graph.edges()) {
                int v = e.first(), w = e.second(v);
                int i = uf.find(listOfVertexes.indexOf(v)), j = uf.find(listOfVertexes.indexOf(w));
                if (i == j) continue;   // same tree
                if (closest[i] == null || less(e, closest[i])) closest[i] = e;
                if (closest[j] == null || less(e, closest[j])) closest[j] = e;
            }

            for (int i = 0; i < graph.V(); i++) {
                Edge e = closest[i];
                if (e != null) {
                    int v = e.first(), w = e.second(v);
                    if (!uf.connected(listOfVertexes.indexOf(v), listOfVertexes.indexOf(w))) {
                        mst.add(e);
                        weight += e.weight();
                        uf.union(listOfVertexes.indexOf(v), listOfVertexes.indexOf(w));
                    }
                }
            }
            memento.addLast(new LinkedList<>(mst));
        }
        assert check(graph);
    }

    private static boolean less(Edge e, Edge f) {
        return e.weight() < f.weight();
    }
    public LinkedList<Edge> nextStep(){
        return memento.get(++iteration);
    }

    public LinkedList<Edge> deletedEdges(){
        LinkedList<Edge> deletedEdges = new LinkedList<>();
        for(int i = memento.get(iteration-1).size();i<memento.get(iteration).size();i++){
            deletedEdges.add(memento.get(iteration).get(i));
        }
        return deletedEdges;
    }

    public LinkedList<Edge> previousStep(){
        return memento.get(--iteration);
    }

    public LinkedList<Edge> lastStep(){
        iteration = memento.size()-1;
        return memento.getLast();
    }

    public int getIteration(){
        return iteration;
    }

    private boolean check(Graph graph) {
        // check that it is acyclic
        UnionFind uf = new UnionFind(graph.V());
        for (Edge e : edges()) {
            int v = e.first(), w = e.second(v);
            if (uf.connected(v, w)) {
                System.err.println("Not a forest");
                return false;
            }
            uf.union(v, w);
        }
        // check that it is a spanning forest
        for (Edge e : graph.edges()) {
            int v = e.first(), w = e.second(v);
            if (!uf.connected(v, w)) {
                System.err.println("Not a spanning forest");
                return false;
            }
        }
        // check that it is a minimal spanning forest
        for (Edge e : edges()) {
            uf = new UnionFind(graph.V());
            for (Edge f : mst) {
                int x = f.first(), y = f.second(x);
                if (f != e) uf.union(x, y);
            }

            for (Edge f : graph.edges()) {
                int x = f.first(), y = f.second(x);
                if (!uf.connected(x, y)) {
                    if (f.weight() < e.weight()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public LinkedList<Edge> edges() {
        return mst;
    }
}
