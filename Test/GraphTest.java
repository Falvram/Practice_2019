import java.util.LinkedList;

import static org.junit.Assert.*;

public class GraphTest {

    @org.junit.Test
    public void clear() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(5,6,7));
        graph.clear();
        Graph graph0 = new Graph();
        assertEquals(graph.E(), graph0.E());
        assertEquals(graph.V(), graph0.V());
        assertEquals(graph.edges(), graph0.edges());
    }

    @org.junit.Test
    public void v() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(5,6,7));
        graph.addEdge(new Edge(2,4,2));
        graph.addEdge(new Edge(1,2,3));
        assertEquals(5, graph.V());
    }


    @org.junit.Test
    public void e() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(5,6,7));
        graph.addEdge(new Edge(2,4,2));
        graph.removeVertex(2);
        assertEquals(1, graph.E());
    }

    @org.junit.Test
    public void addEdge() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(3,5,3));
        graph.addEdge(new Edge(2,4,2));
        graph.addEdge(new Edge(1,2,3));
        assertEquals(3, graph.E());
    }

    @org.junit.Test
    public void removeEdge() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(2,4,3));
        graph.removeEdge(new Edge(2,3,4));
        graph.removeEdge(new Edge(1,2,4));
        assertEquals(graph.E(), 1);
    }

    @org.junit.Test
    public void removeVertex() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(2,4,3));
        graph.removeVertex(3);
        graph.removeVertex(2);
        assertEquals(graph.V(), 0);
    }

    @org.junit.Test
    public void incEdges() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(2,4,3));
        LinkedList<Edge> list= graph.incEdges(2);
        LinkedList<Edge> list0 = new LinkedList<>();
        list0.add(new Edge(1,2,3));
        list0.add(new Edge(2,4,3));
        LinkedList<String> answer = new LinkedList<>();
        LinkedList<String> expect = new LinkedList<>();
        for (Edge e : list){
            answer.add(e.toString());
        }
        for (Edge e : list0){
            expect.add(e.toString());
        }
        assertEquals(expect, answer);
    }

    @org.junit.Test
    public void edges() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(3,4,3));
        LinkedList<Edge> list= graph.edges();
        LinkedList<Edge> list0 = new LinkedList<>();
        list0.add(new Edge(1,2,3));
        list0.add(new Edge(3,4,3));
        LinkedList<String> answer = new LinkedList<>();
        LinkedList<String> expect = new LinkedList<>();
        for (Edge e : list){
            answer.add(e.toString());
        }
        for (Edge e : list0){
            expect.add(e.toString());
        }
        assertEquals(expect, answer);
    }

    @org.junit.Test
    public void vertexes() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(3,4,3));
        LinkedList<Integer> list = graph.vertexes();
        LinkedList<Integer> list0 = new LinkedList<>();
        list0.add(1);
        list0.add(2);
        list0.add(3);
        list0.add(4);
        assertEquals(list, list0);
    }

    @org.junit.Test
    public void toString1() {
        Graph graph = new Graph();
        graph.addEdge(new Edge(1,2,3));
        graph.addEdge(new Edge(3,4,3));
        assertEquals(graph.toString(), "1-2 3,0\n3-4 3,0\n");
    }
}