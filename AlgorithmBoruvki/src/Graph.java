import java.util.*;

public class Graph {
    private int V = 0;
    private int E = 0;
    private Map<Integer, LinkedList<Edge>> incEdges;

    public Graph(){
        incEdges = new HashMap<Integer, LinkedList<Edge>>();
    }

    public int V(){ return V; }
    public int E(){ return E; }
    public void addEdge(Edge e) {
        E++;
        if(incEdges.containsKey(e.first())) {
            incEdges.get(e.first()).add(e);
        }
        else {
            V++;
            incEdges.putIfAbsent(e.first(), new LinkedList<Edge>());
            incEdges.get(e.first()).add(e);
        }
        if(incEdges.containsKey(e.second(e.first()))) {
            incEdges.get(e.second(e.first())).add(e);
        }
        else {
            V++;
            incEdges.putIfAbsent(e.second(e.first()), new LinkedList<Edge>());
            incEdges.get(e.second(e.first())).add(e);
        }
    }
    public void removeEdge(Edge e) {
        E--;
        incEdges.get(e.first()).remove(e);
        if(incEdges.get(e.first()) == null){ V--; }
        incEdges.get(e.second(e.first())).remove(e);
        if(incEdges.get(e.second(e.first())) == null){ V--; }
    }
    public void removeVertex(int v){
        for(Edge k : incEdges.get(v)) {
            removeEdge(k);
        }
        incEdges.remove(v);
    }
    public LinkedList<Edge> incEdges(int v){
        LinkedList<Edge> a = new LinkedList<Edge>();
        for(Edge e : incEdges.get(v)) {
            a.add(e);
        }
        return a;
    }
    public LinkedList<Edge> edges(){
        LinkedList<Edge> a = new LinkedList<Edge>();
        for(int v : incEdges.keySet()){
            for(Edge e : incEdges.get(v)) {
                if (e.second(v) > v) {
                    a.add(e);
                }
            }
        }
        return a;
    }
    public LinkedList<Integer> vertexes() {
        LinkedList<Integer> a = new LinkedList<Integer>();
        for(int v : incEdges.keySet()){
            a.add(v);
        }
        return a;
    }
    public String toString(){
        String str = "";
        for(Edge e: edges()){
            str += e.toString()+'\n';
        }
        return str;
    }
}