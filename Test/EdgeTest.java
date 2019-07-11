import static org.junit.Assert.*;

public class EdgeTest {

    @org.junit.Test
    public void weight() {
        Edge edge = new Edge(0,1,2);
        assertEquals(2, edge.weight(), 0.01);
    }

    @org.junit.Test
    public void first() {
        Edge edge = new Edge(0,1,2);
        assertEquals(0, edge.first());
    }

    @org.junit.Test
    public void second() {
        Edge edge = new Edge(0,1,2);
        assertEquals(1, edge.second(edge.first()));
    }

    @org.junit.Test
    public void compareTo() {
        Edge ths = new Edge(0, 1 ,10);
        Edge other = new Edge(10, 20, 10);
        assertEquals(0, ths.compareTo(other));
    }

    @org.junit.Test
    public void toString1() {
        Edge edge = new Edge(0, 1 ,10);
        assertEquals("0-1 10,0", edge.toString());
    }

    @org.junit.Test
    public void isEqual() {
        Edge ths = new Edge(0, 1 ,10);
        Edge other = new Edge(10, 20, 10);
        Edge eth = new Edge(10,20,10);
        assertEquals(false, ths.isEqual(other));
        assertEquals(true, eth.isEqual(other));
    }
}