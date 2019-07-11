import org.junit.Test;

import static org.junit.Assert.*;

public class UnionFindTest {

    @Test
    public void isConnected() {
        UnionFind uf = new UnionFind(4);
        Graph graph = new Graph();
        graph.addEdge(new Edge(0, 1, 2));
        graph.addEdge(new Edge(2, 3, 4));
        assertEquals(false, uf.isConnected(graph));
        graph.addEdge(new Edge(0, 2, 4));
        assertEquals(true, uf.isConnected(graph));
    }

    @Test
    public void find() {
        UnionFind uf = new UnionFind(4);
        Graph graph = new Graph();
        graph.addEdge(new Edge(0, 1, 2));
        graph.addEdge(new Edge(2, 3, 4));
        graph.addEdge(new Edge(0, 2, 4));
        uf.union(0,1 );
        uf.union(2,3 );
        uf.union(0,2 );
        assertEquals(0, uf.find(3));
    }

    @Test
    public void count() {
        UnionFind uf = new UnionFind(4);
        Graph graph = new Graph();
        graph.addEdge(new Edge(0, 1, 2));
        graph.addEdge(new Edge(2, 3, 4));
        graph.addEdge(new Edge(0, 2, 4));
        uf.union(0,1 );
        assertEquals(3, uf.count());
    }

    @Test
    public void connected() {
        UnionFind uf = new UnionFind(4);
        Graph graph = new Graph();
        graph.addEdge(new Edge(0, 1, 2));
        graph.addEdge(new Edge(2, 3, 4));
        graph.addEdge(new Edge(0, 2, 4));
        assertEquals(false, uf.connected(0, 1));
        uf.union(0,1 );
        assertEquals(true, uf.connected(0, 1));
    }

    @Test
    public void union() {
        UnionFind uf = new UnionFind(4);
        Graph graph = new Graph();
        graph.addEdge(new Edge(0, 1, 2));
        graph.addEdge(new Edge(2, 3, 4));
        graph.addEdge(new Edge(0, 2, 4));
        assertEquals(3, uf.find(3));
        uf.union(2,3 );
        assertEquals(2, uf.find(3));
    }
}