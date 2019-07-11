import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.lang.Math;

//класс, конвертирующий абстрактный граф в его плоскостное представление
public class GraphVizualizer extends JPanel {

    private LinkedList<Edge> listOfEdges; //список ребер исходного графа
    private LinkedList<Edge> listOfEdgesOfMSTStep; //список ребер минимального остовного дерева на данном шаге
    private LinkedList<Integer> listOfVerteces;//список вершин
    private ArrayList<Coordinates> coordsOfVerteces;//координаты вершин
    public Integer indexOfSelectedVertex;

    public GraphVizualizer(){
        setLayout(null); //абсолютное позиционирование - расположение файлов задается точно
        Font fontLogging = new Font(null, 1, 14);
        setFont(fontLogging);
    }
    public void setGraph(Graph graph) {
        setBackground(Color.gray);
        addMouseListener(new MyMouse());
        addMouseMotionListener(new MyMove());

        listOfVerteces = graph.vertexes();
        listOfEdges = graph.edges();
        listOfEdgesOfMSTStep = new LinkedList<>();
        coordsOfVerteces = new ArrayList<Coordinates>(graph.V());
        arrangement(); //располложение
        revalidate();
        repaint();
    }
    public void paint(Graphics graph) { //отрисовка графа
        super.paint(graph);
        if(listOfVerteces == null){
            return;
        }
        Graphics2D graph_2d = (Graphics2D) graph;
        graph_2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //размывание крайних пикселей линий для гладкости изображения
        Color oldColor = Color.black;
        for (Edge e : listOfEdges) {
            //настройка пера
            if(listOfEdgesOfMSTStep != null && listOfEdgesOfMSTStep.indexOf(e) != -1){
                graph_2d.setStroke(new BasicStroke(3));
                graph_2d.setColor(Color.cyan);
            } else {
                graph_2d.setStroke(new BasicStroke());
                graph_2d.setColor(Color.black);
            }
            int indexFirst = listOfVerteces.indexOf(e.first());
            int indexSecond = listOfVerteces.indexOf(e.second(e.first()));
            Coordinates from = coordsOfVerteces.get(indexFirst);
            Coordinates to = coordsOfVerteces.get(indexSecond);
            graph_2d.draw(new Line2D.Double(from.x, from.y, to.x, to.y));
            //отображение веса ребер
            graph_2d.setColor(oldColor);
            graph_2d.drawString("" + e.weight(), (to.x - from.x) * 2 / 5 + from.x, (to.y - from.y) * 2 / 5 + from.y);
        }
        //работа с цветом

        //отображение вершин
        for (int i = 0; i < listOfVerteces.size(); i++) { //проход по всем вершинам
            Coordinates center = coordsOfVerteces.get(i);
            Ellipse2D.Double v = new Ellipse2D.Double(center.x - 17,center.y - 13,35,35);
            graph_2d.draw(v);
            graph_2d.setColor(Color.cyan);
            graph_2d.fill(v);
            graph_2d.setColor(oldColor);
            graph_2d.drawString("" + listOfVerteces.get(i), center.x - 5, center.y + 10);
        }
    }
    private void arrangement() { //расстановка вершин/рёбер графа
        for (int i = 0; i < listOfVerteces.size(); i++) { //проход по всем вершинам
            //вид правильного n-угольника
            coordsOfVerteces.add(new Coordinates((int)(335 * Main_window.coeff + 150 * Math.cos(6.28 / listOfVerteces.size() * i)), (int)(372 * Main_window.coeff + 150 * Math.sin(6.28 / listOfVerteces.size() * i))));
        }
    }
    public void setMSTEdges(LinkedList<Edge> edges){
        listOfEdgesOfMSTStep = edges;
        revalidate();
        repaint();
    }
    private Integer find(Point2D cursorPosition){
        for(Coordinates center : coordsOfVerteces) {
            Ellipse2D.Double tmp = new Ellipse2D.Double(center.x-17,center.y-13,35,35);
            if (tmp.contains(cursorPosition)) return coordsOfVerteces.indexOf(center);
        }
        return null;
    }
    private class MyMouse extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            indexOfSelectedVertex = find(e.getPoint());
        }
    }
    private class MyMove implements MouseMotionListener {
        @Override
        public void mouseMoved(MouseEvent e) {
            if(find(e.getPoint()) != null)
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); //
            else
                setCursor(Cursor.getDefaultCursor());
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if(indexOfSelectedVertex != null){
                Ellipse2D.Double tmp = new Ellipse2D.Double(coordsOfVerteces.get(indexOfSelectedVertex).x - 25, coordsOfVerteces.get(indexOfSelectedVertex).y - 25, 25, 25);
                Coordinates crd = new Coordinates(e.getX()+10,e.getY());
                if(crd.x<20) crd.x = 20;
                if(crd.y<20) crd.y = 20;
                if(crd.x>(int)(650 * Main_window.coeff)) crd.x = (int)(650 * Main_window.coeff);
                if(crd.y>(int)(725 * Main_window.coeff)) crd.y = (int)(725 * Main_window.coeff);
                tmp.setFrame(crd.x-20, crd.y-10,30,30);
                coordsOfVerteces.set(indexOfSelectedVertex, crd);
                repaint();
            }
        }
    }
}