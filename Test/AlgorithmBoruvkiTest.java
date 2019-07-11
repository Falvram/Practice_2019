import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.LinkedList;
import static org.junit.Assert.*;

public class AlgorithmBoruvkiTest {

    @Test
    public void nextStep() {
        Graph graph = new Graph();
        try {
            FileInputStream fstream = new FileInputStream("TestGraph.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                    Scanner s = new Scanner(str);
                    graph.addEdge(new Edge(s.nextInt(), s.nextInt(), s.nextDouble()));
            }
        }
        catch (IOException ioexc) {
            return;
        }
        AlgorithmBoruvki alg = new AlgorithmBoruvki(graph);
        LinkedList<String> expect = new LinkedList<>();
        LinkedList<String> answer = new LinkedList<>();
        LinkedList<Edge> tmp = alg.nextStep();
        for (Edge e : tmp){
            answer.add(e.toString());
        }
        try {
            FileInputStream fstream = new FileInputStream("TestRes.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                expect.add(str);
            }
        }
        catch (IOException ioexc) {
            return;
        }
        assertEquals(expect, answer);
    }

    @Test
    public void deletedEdges() {
        Graph graph = new Graph();
        try {
            FileInputStream fstream = new FileInputStream("TestGraph.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                Scanner s = new Scanner(str);
                graph.addEdge(new Edge(s.nextInt(), s.nextInt(), s.nextDouble()));
            }
        }
        catch (IOException ioexc) {
            return;
        }
        AlgorithmBoruvki alg = new AlgorithmBoruvki(graph);
        LinkedList<String> expect = new LinkedList<>();
        LinkedList<String> answer = new LinkedList<>();
        LinkedList<Edge> tmp = alg.nextStep();
        alg.nextStep();
        alg.previousStep();
        tmp = alg.deletedEdges();
        for (Edge e : tmp){
            answer.add(e.toString());
        }
        try {
            FileInputStream fstream = new FileInputStream("TestRes.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                expect.add(str);
            }
        }
        catch (IOException ioexc) {
            return;
        }
        //assertEquals(expect, answer);
    }

    @Test
    public void previousStep() {
        Graph graph = new Graph();
        try {
            FileInputStream fstream = new FileInputStream("TestGraph.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                Scanner s = new Scanner(str);
                graph.addEdge(new Edge(s.nextInt(), s.nextInt(), s.nextDouble()));
            }
        }
        catch (IOException ioexc) {
            return;
        }
        AlgorithmBoruvki alg = new AlgorithmBoruvki(graph);
        LinkedList<String> expect = new LinkedList<>();
        LinkedList<String> answer = new LinkedList<>();
        LinkedList<Edge> tmp = alg.nextStep();
        tmp = alg.nextStep();
        tmp = alg.previousStep();
        for (Edge e : tmp){
            answer.add(e.toString());
        }
        try {
            FileInputStream fstream = new FileInputStream("TestRes.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                expect.add(str);
            }
        }
        catch (IOException ioexc) {
            return;
        }
        assertEquals(expect, answer);
    }

    @Test
    public void lastStep() {
        Graph graph = new Graph();
        try {
            FileInputStream fstream = new FileInputStream("TestGraph.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                Scanner s = new Scanner(str);
                graph.addEdge(new Edge(s.nextInt(), s.nextInt(), s.nextDouble()));
            }
        }
        catch (IOException ioexc) {
            return;
        }
        AlgorithmBoruvki alg = new AlgorithmBoruvki(graph);
        LinkedList<String> expect = new LinkedList<>();
        LinkedList<String> answer = new LinkedList<>();
        LinkedList<Edge> tmp = alg.lastStep();
        for (Edge e : tmp){
            answer.add(e.toString());
        }
        try {
            FileInputStream fstream = new FileInputStream("TestLast.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                expect.add(str);
            }
        }
        catch (IOException ioexc) {
            return;
        }
        assertEquals(expect, answer);
    }

    @Test
    public void getIteration() {
        Graph graph = new Graph();
        try {
            FileInputStream fstream = new FileInputStream("TestGraph.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                Scanner s = new Scanner(str);
                graph.addEdge(new Edge(s.nextInt(), s.nextInt(), s.nextDouble()));
            }
        }
        catch (IOException ioexc) {
            return;
        }
        AlgorithmBoruvki alg = new AlgorithmBoruvki(graph);
        alg.nextStep();
        assertEquals(1, alg.getIteration());
    }

    @Test
    public void edges() {
        Graph graph = new Graph();
        try {
            FileInputStream fstream = new FileInputStream("TestGraph.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                Scanner s = new Scanner(str);
                graph.addEdge(new Edge(s.nextInt(), s.nextInt(), s.nextDouble()));
            }
        }
        catch (IOException ioexc) {
            return;
        }
        AlgorithmBoruvki alg = new AlgorithmBoruvki(graph);
        LinkedList<String> expect = new LinkedList<>();
        LinkedList<String> answer = new LinkedList<>();
        LinkedList<Edge> tmp = alg.lastStep();
        tmp = alg.edges();
        for (Edge e : tmp){
            answer.add(e.toString());
        }
        try {
            FileInputStream fstream = new FileInputStream("TestLast.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String str;
            while ((str = br.readLine()) != null) {
                expect.add(str);
            }
        }
        catch (IOException ioexc) {
            return;
        }
        assertEquals(expect, answer);
    }
}