import java.util.*;

public class Graph {
    private int V = 0;
    private int E = 0;
    private Map<Integer, LinkedList<Edge>> incEdges;

    public Graph(){
        incEdges = new HashMap<Integer, LinkedList<Edge>>();
    }
    public void clear(){
        V = 0;
        E = 0;
        incEdges = new HashMap<Integer, LinkedList<Edge>>();
    }
    public int V(){ return V; }
    public int E(){ return E; }
    public void addEdge(Edge e) {
        boolean isExists = false;
        for(Edge edge : edges()){
            if(edge.isEqual(e)){
                isExists = true;
                break;
            }
        }
        if(!isExists){
            E++;
        }
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
        boolean isExists = false;
        for(Edge edge : edges()){
            if(edge.isEqual(e)){
                isExists = true;
                break;
            }
        }
        if(!isExists){
            return;
        }
        E--;
        incEdges.get(e.first()).remove(e);
        if(incEdges.get(e.first()).size() == 0){
            V--;
            incEdges.remove(e.first());
        }
        incEdges.get(e.second(e.first())).remove(e);
        if(incEdges.get(e.second(e.first())).size() == 0){
            V--;
            incEdges.remove(e.second(e.first()));
        }
    }
    public void removeVertex(int v){
        if(incEdges.get(v) == null){
            return;
        }
        int size = incEdges.get(v).size();
        if(size != 0) {
            for (int i = incEdges.get(v).size() - 1; i >= 0; i--) {
                removeEdge(incEdges.get(v).get(i));
            }
        } else{
            V--;
            incEdges.remove(v);
        }

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