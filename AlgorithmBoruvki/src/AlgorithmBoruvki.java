import java.util.LinkedList;

public class AlgorithmBoruvki {
    private int iteration = 0;
    private Graph graph;
    private UnionFind uf;
    private LinkedList<Integer> listOfVertexes;
    private LinkedList<LinkedList<Edge>> memento = new LinkedList<>();
    private LinkedList<Edge> mst = new LinkedList<>();

    public AlgorithmBoruvki(Graph graph) {
        this.graph = graph;
        LinkedList<Edge> zeroStep = new LinkedList<>();
        memento.addLast(zeroStep);
        uf = new UnionFind(graph.V());
        listOfVertexes = graph.vertexes();
    }

    private void step() {
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
                    uf.union(listOfVertexes.indexOf(v), listOfVertexes.indexOf(w));
                }
            }
        }
        memento.addLast(new LinkedList<>(mst));
    }

    private static boolean less(Edge e, Edge f) {
        return e.weight() < f.weight();
    }

    public LinkedList<Edge> nextStep(){
        if(mst.size() >= graph.V() - 1){
            if(memento.size() == iteration+1){
                return memento.get(iteration);
            } else {
                return memento.get(++iteration);
            }
        }
        if(memento.size() <= iteration+1){
            step();
        }
        return memento.get(++iteration);
    }

    public LinkedList<Edge> deletedEdges() {
        LinkedList<Edge> deletedEdges = new LinkedList<>();
        for(int i = memento.get(iteration-1).size();i<memento.get(iteration).size();i++){
            deletedEdges.add(memento.get(iteration).get(i));
        }
        return deletedEdges;
    }

    public LinkedList<Edge> previousStep() {
        if(iteration <= 0){
            return memento.get(0);
        }
        return memento.get(--iteration);
    }

    public LinkedList<Edge> lastStep() {
        iteration = memento.size();
        for (; iteration < graph.V() && mst.size() < graph.V() - 1; iteration *=2) {
            step();
        }
        iteration = memento.size() - 1;
        return memento.getLast();
    }

    public int getIteration(){
        return iteration;
    }

    public LinkedList<Edge> edges() {
        return mst;
    }
}
