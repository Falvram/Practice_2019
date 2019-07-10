import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;

public class Edit_window extends JFrame{
    private JButton readButton = new JButton("Считать данные из файла");
    JComboBox edgeList;//список ребер доступных для удаления
    JComboBox vertexList;//список вершин доступных для удаления
    private JButton removeEdge = new JButton("Удалить ребро");
    private JButton removeVertex = new JButton("Удалить вершину");
    private JButton deleteGraph = new JButton("Удалить граф");
    private JButton saveGraph = new JButton("Сохранить граф");
    private JButton addData = new JButton("Добавить");
    private JLabel labelFrom = new JLabel("Откуда:");
    private JLabel labelTo = new JLabel("Куда:");
    private JLabel labelWeight = new JLabel("Вес ребра:");
    private JLabel labelPath = new JLabel("Путь до файла:");
    private JTextArea listData = new JTextArea(); //список добавленных вершин и рёбер
    private JTextField inputLineFrom = new JTextField("",3);
    private JTextField inputLineTo = new JTextField("",3);
    private JTextField inputLineWeight = new JTextField("",3);
    private JTextField inputLinePath = new JTextField("",3);
    private JPanel editPanel = new JPanel(); //окно редактирования
    private int from;
    private int to;
    private double weight;

    public Edit_window(JPanel parent, Graph graph, GraphVizualizer graphPanel) {
        super("Окно редактирования графа");
        parent.setVisible(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int)((screenSize.width - Main_window.WIDTH*Main_window.coeff)/2),
                (int)((screenSize.height - Main_window.HEIGHT*Main_window.coeff)/2) ,
                (int)(Main_window.WIDTH*Main_window.coeff),(int)(Main_window.HEIGHT*Main_window.coeff)); //размер окна
        setResizable(false); //запрет на изменения размера окна
        editPanel.setLayout(null);
        add(editPanel);
        //установка действия на нажатие кнопки закрытия окна:
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        listData.setEditable(false); //запрет на редактирование, ввод текста
        listData.setLineWrap(true);
        //добавление скроллбара
        final JScrollPane scrollPane = new JScrollPane(listData, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputLinePath.setEnabled(true);

        LinkedList<Edge> listOfEdges = graph.edges();//список ребер графа
        LinkedList<Integer> listOfVertexes = graph.vertexes();//список ребер графа
        //соответствующие наборы строк
        String[] stringListOfEdges = new String[graph.E()];
        if(listOfEdges.size() != 0) {
            listData.append("В графе имеются следующие ребра:\n");
        }
        for (int i = 0; i < graph.E(); i++) {
            stringListOfEdges[i] = listOfEdges.get(i).toString();
            listData.append(listOfEdges.get(i).toString()+"\n");
        }
        String[] stringListOfVertexes = new String[graph.V()];
        for(int i = 0; i < graph.V(); i++){
            stringListOfVertexes[i] = listOfVertexes.get(i).toString();
        }

        edgeList = new JComboBox(stringListOfEdges);
        vertexList = new JComboBox(stringListOfVertexes);

        edgeList.setEditable(false);
        vertexList.setEditable(false);

        //стиль текста
        Font fontButton = new Font(null, 3, 16); //выбор шрифта
        Font fontList = new Font(null, 1, 14);
        readButton.setFont(fontButton);
        removeEdge.setFont(fontButton);
        removeVertex.setFont(fontButton);
        edgeList.setFont(fontList);
        vertexList.setFont(fontList);
        deleteGraph.setFont(fontButton);
        saveGraph.setFont(fontButton);
        addData.setFont(fontButton);
        listData.setFont(fontList);
        labelFrom.setFont(fontButton);
        labelTo.setFont(fontButton);
        labelWeight.setFont(fontButton);
        labelPath.setFont(fontButton);
        inputLineFrom.setFont(fontList);
        inputLineTo.setFont(fontList);
        inputLineWeight.setFont(fontList);
        inputLinePath.setFont(fontList);
        //размеры кнопок
        readButton.setBounds((int)(10*Main_window.coeff),(int)(190*Main_window.coeff),(int)(740*Main_window.coeff),(int)(50*Main_window.coeff));
        edgeList.setBounds((int)(10*Main_window.coeff),(int)(260*Main_window.coeff),(int)(350*Main_window.coeff),(int)(50*Main_window.coeff));
        vertexList.setBounds((int)(400*Main_window.coeff),(int)(260*Main_window.coeff),(int)(350*Main_window.coeff),(int)(50*Main_window.coeff));
        removeEdge.setBounds((int)(10*Main_window.coeff),(int)(330*Main_window.coeff),(int)(350*Main_window.coeff),(int)(50*Main_window.coeff));
        removeVertex.setBounds((int)(400*Main_window.coeff),(int)(330*Main_window.coeff),(int)(350*Main_window.coeff),(int)(50*Main_window.coeff));

        deleteGraph.setBounds((int)(10*Main_window.coeff),(int)(400*Main_window.coeff),(int)(740*Main_window.coeff),(int)(50*Main_window.coeff));
        saveGraph.setBounds((int)(10*Main_window.coeff),(int)(470*Main_window.coeff),(int)(740*Main_window.coeff),(int)(50*Main_window.coeff));
        addData.setBounds((int)(400*Main_window.coeff),(int)(50*Main_window.coeff),(int)(350*Main_window.coeff),(int)(50*Main_window.coeff));
        labelFrom.setBounds((int)(10*Main_window.coeff),(int)(5*Main_window.coeff),(int)(110*Main_window.coeff),(int)(50*Main_window.coeff));
        labelTo.setBounds((int)(130*Main_window.coeff),(int)(5*Main_window.coeff),(int)(110*Main_window.coeff),(int)(50*Main_window.coeff));
        labelWeight.setBounds((int)(250*Main_window.coeff),(int)(5*Main_window.coeff),(int)(120*Main_window.coeff),(int)(50*Main_window.coeff));
        labelPath.setBounds((int)(10*Main_window.coeff),(int)(120*Main_window.coeff),(int)(200*Main_window.coeff),(int)(50*Main_window.coeff));
        scrollPane.setBounds((int)(790*Main_window.coeff),(int)(50*Main_window.coeff),(int)(410*Main_window.coeff),(int)(470*Main_window.coeff));
        inputLineFrom.setBounds((int)(10*Main_window.coeff),(int)(50*Main_window.coeff),(int)(110*Main_window.coeff),(int)(50*Main_window.coeff));
        inputLineTo.setBounds((int)(130*Main_window.coeff),(int)(50*Main_window.coeff),(int)(110*Main_window.coeff),(int)(50*Main_window.coeff));
        inputLineWeight.setBounds((int)(250*Main_window.coeff),(int)(50*Main_window.coeff),(int)(110*Main_window.coeff),(int)(50*Main_window.coeff));
        inputLinePath.setBounds((int)(200*Main_window.coeff),(int)(120*Main_window.coeff),(int)(550*Main_window.coeff),(int)(50*Main_window.coeff));
        //Цвета полей
        editPanel.setBackground(Color.darkGray);
        listData.setBackground(Color.gray);
        inputLineFrom.setBackground(Color.gray);
        inputLineTo.setBackground(Color.gray);
        inputLineWeight.setBackground(Color.gray);
        inputLinePath.setBackground(Color.gray);
        readButton.setBackground(Color.cyan);
        edgeList.setBackground(Color.gray);
        vertexList.setBackground(Color.gray);
        removeEdge.setBackground(Color.cyan);
        removeVertex.setBackground(Color.cyan);
        deleteGraph.setBackground(Color.cyan);
        saveGraph.setBackground(Color.cyan);
        addData.setBackground(Color.cyan);
        inputLinePath.setForeground(Color.white);
        listData.setForeground(Color.white);
        labelFrom.setForeground(Color.white);
        labelTo.setForeground(Color.white);
        labelWeight.setForeground(Color.white);
        labelPath.setForeground(Color.white);
        //установка рамок
        readButton.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        removeEdge.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        removeVertex.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        deleteGraph.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        saveGraph.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        addData.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        //убрать выделение вокруг текста кнопки
        readButton.setFocusPainted(false);
        removeEdge.setFocusPainted(false);
        removeVertex.setFocusPainted(false);
        deleteGraph.setFocusPainted(false);
        saveGraph.setFocusPainted(false);
        addData.setFocusPainted(false);
        //добавление объектов на панель
        editPanel.add(readButton);
        editPanel.add(labelPath);
        editPanel.add(inputLinePath);
        editPanel.add(removeEdge);
        editPanel.add(removeVertex);
        editPanel.add(edgeList);
        editPanel.add(vertexList);
        editPanel.add(deleteGraph);
        editPanel.add(saveGraph);
        editPanel.add(addData);
        editPanel.add(labelFrom);
        editPanel.add(labelTo);
        editPanel.add(labelWeight);
        editPanel.add(scrollPane);
        editPanel.add(inputLineFrom);
        editPanel.add(inputLineTo);
        editPanel.add(inputLineWeight);
        //добавление действий
        inputLinePath.addActionListener(e->{
            Graph tmp0 = new Graph();
            String path = inputLinePath.getText();
            ArrayList<String> edges = new ArrayList<String>();
            try{
                FileInputStream fstream = new FileInputStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String str;
                while ((str = br.readLine()) != null){
                    edges.add(str);
                }
            }
            catch (IOException ioexc){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "При считывании возникла ошибка." );
                return;
            }
            for(String k : edges){
                try (Scanner s = new Scanner(k)) {
                    int args[] = new int[2];
                    double weight;
                    for(int count = 0; count<2 ;count++) {
                        if (s.hasNextInt()) {
                            args[count] = s.nextInt();
                            if(args[count] < 0){
                                throw new Exception();
                            }
                        } else {
                            throw new Exception();
                        }
                    }
                    if(s.hasNextDouble()){
                        weight = s.nextDouble();
                        if(weight <= 0){
                            throw new Exception();
                        }
                    } else {
                        throw new Exception();
                    }
                    if(s.hasNext()){
                        throw new Exception();
                    }
                    Edge tmp = new Edge(args[0], args[1], weight);
                    tmp0.addEdge(tmp);

                }
                catch(Exception exc){
                    JOptionPane.showMessageDialog(Edit_window.this,
                            "Проверьте правильность данных в файле" );
                    inputLinePath.setText("");
                    return;
                }
            }
            graph.clear();
            edgeList.removeAllItems();
            listOfEdges.clear();
            vertexList.removeAllItems();
            listOfVertexes.clear();
            listData.append("Исходный граф удален\n");
            for(Edge edge : tmp0.edges()) {
                graph.addEdge(edge);
                edgeList.addItem(edge.toString());
                listOfEdges.add(edge);
                listData.append("Добавлено ребро " + edge.toString() + '\n');
                if (listOfVertexes.indexOf(edge.first()) == -1) {
                    listOfVertexes.add(edge.first());
                    vertexList.addItem("" + edge.first());
                }
                if (listOfVertexes.indexOf(edge.second(edge.first())) == -1) {
                    listOfVertexes.add(edge.second(edge.first()));
                    vertexList.addItem("" + edge.second(edge.first()));
                }
            }
            JOptionPane.showMessageDialog(Edit_window.this,
                    "Граф считан" );
            inputLinePath.setText("");
        });
        readButton.addActionListener(e->{
            Graph tmp0 = new Graph();
            String path = inputLinePath.getText();
            ArrayList<String> edges = new ArrayList<String>();
            try{
                FileInputStream fstream = new FileInputStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String str;
                while ((str = br.readLine()) != null){
                    edges.add(str);
                }
            }
            catch (IOException ioexc){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "При считывании возникла ошибка." );
                return;
            }
            for(String k : edges){
                try (Scanner s = new Scanner(k)) {
                    int args[] = new int[2];
                    double weight;
                    for(int count = 0; count<2 ;count++) {
                        if (s.hasNextInt()) {
                            args[count] = s.nextInt();
                            if(args[count] < 0){
                                throw new Exception();
                            }
                        } else {
                            throw new Exception();
                        }
                    }
                    if(s.hasNextDouble()){
                        weight = s.nextDouble();
                        if(weight <= 0){
                            throw new Exception();
                        }
                    } else {
                        throw new Exception();
                    }
                    if(s.hasNext()){
                        throw new Exception();
                    }
                    Edge tmp = new Edge(args[0], args[1], weight);
                    tmp0.addEdge(tmp);

                }
                catch(Exception exc){
                    JOptionPane.showMessageDialog(Edit_window.this,
                            "Проверьте правильность данных в файле" );
                    inputLinePath.setText("");
                    return;
                }
            }
            graph.clear();
            edgeList.removeAllItems();
            listOfEdges.clear();
            vertexList.removeAllItems();
            listOfVertexes.clear();
            listData.append("Исходный граф удален\n");
            for(Edge edge : tmp0.edges()) {
                graph.addEdge(edge);
                edgeList.addItem(edge.toString());
                listOfEdges.add(edge);
                listData.append("Добавлено ребро " + edge.toString() + '\n');
                if (listOfVertexes.indexOf(edge.first()) == -1) {
                    listOfVertexes.add(edge.first());
                    vertexList.addItem("" + edge.first());
                }
                if (listOfVertexes.indexOf(edge.second(edge.first())) == -1) {
                    listOfVertexes.add(edge.second(edge.first()));
                    vertexList.addItem("" + edge.second(edge.first()));
                }
            }
            JOptionPane.showMessageDialog(Edit_window.this,
                    "Граф считан" );
            inputLinePath.setText("");
        });
        removeEdge.addActionListener(e->{
            int index = edgeList.getSelectedIndex();
            if(index == -1){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Нет ребер для удаления" );
                return;
            }
            Edge tmp = listOfEdges.get(index);
            //непосредственно удаление ребра
            graph.removeEdge(listOfEdges.get(index));
            edgeList.removeItemAt(index);
            listData.append("Удалено ребро "+listOfEdges.remove(index).toString()+'\n');
            //удаление вершин, исчезнувших после удаления ребра
            int first = tmp.first();
            boolean isFirstExists = false;
            int second = tmp.second(first);
            boolean isSecondExists = false;
            for(Edge edge : listOfEdges){
                if(first == edge.first() || first == edge.second(edge.first())){
                    isFirstExists = true;
                }
                if(second == edge.first() || second == edge.second(edge.first())){
                    isSecondExists = true;
                }
                if(isFirstExists && isSecondExists){
                    break;
                }
            }
            if(!isFirstExists){
                int indexFirst = listOfVertexes.indexOf(first);
                vertexList.removeItemAt(indexFirst);
                listData.append("Удалена вершина "+listOfVertexes.remove(indexFirst).toString()+'\n');
            }
            if(!isSecondExists){
                int indexSecond = listOfVertexes.indexOf(second);
                vertexList.removeItemAt(indexSecond);
                listData.append("Удалена вершина "+listOfVertexes.remove(indexSecond).toString()+'\n');
            }

        });
        removeVertex.addActionListener(e->{
            int index = vertexList.getSelectedIndex();
            if(index == -1){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Нет вершин для удаления" );
                return;
            }
            //удаление ребер инцедентных удаленной вершине
            for(Edge edge : graph.incEdges(listOfVertexes.get(index))){
                if(listOfEdges.indexOf(edge) != -1){
                    edgeList.removeItemAt(listOfEdges.indexOf(edge));
                    listData.append("Удалено ребро "+listOfEdges.remove(listOfEdges.indexOf(edge)).toString()+'\n');
                    int second = edge.second(listOfVertexes.get(index));
                    boolean isSecondExists = false;
                    for(Edge edgeTmp : listOfEdges){
                        if(second == edgeTmp.first() || second == edgeTmp.second(edgeTmp.first())){
                            isSecondExists = true;
                        }
                        if(isSecondExists){
                            break;
                        }
                    }
                    if(!isSecondExists) {
                        int indexSecond = listOfVertexes.indexOf(edge.second(listOfVertexes.get(index)));
                        vertexList.removeItemAt(indexSecond);
                        listData.append("Удалена вершина " + listOfVertexes.remove(indexSecond) + '\n');
                        if (index > indexSecond) {
                            index--;
                        }
                    }
                }
            }
            graph.removeVertex(listOfVertexes.get(index));
            vertexList.removeItemAt(index);
            listData.append("Удалена вершина "+listOfVertexes.remove(index).toString()+'\n');
        });
        deleteGraph.addActionListener(e->{
            graph.clear();
            edgeList.removeAllItems();
            listOfEdges.clear();
            vertexList.removeAllItems();
            listOfVertexes.clear();
            listData.append("Граф удален\n");
        });
        saveGraph.addActionListener(e -> {
            File graphWrite = new File("./", "Graph.txt");
            if(graphWrite.exists()) {
                graphWrite.delete();
            }
            try {
                graphWrite.createNewFile();
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Не удалось открыть файл!" );
                return;
            }
            try(BufferedWriter  writerGraph = new BufferedWriter( new FileWriter(graphWrite)))
            {
                for (Edge edge : listOfEdges) {
                    writerGraph.write(String.format("%d %d %.1f", edge.first(), edge.second(edge.first()), edge.weight()));
                    writerGraph.newLine();
                }
                writerGraph.flush();
                writerGraph.close();
            }
            catch(IOException ex){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Не удалось записать в файл!" );
                return;
            }
            JOptionPane.showMessageDialog(Edit_window.this,
                    "Граф успешно сохранен в файл Graph.txt" );
        });
        addData.addActionListener(e -> {
            try (Scanner s = new Scanner(inputLineFrom.getText())) {
                if (s.hasNextInt()) {
                    from = s.nextInt();
                    if (from < 0) {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
                if (s.hasNext()) {
                    throw new Exception();
                }
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Проверьте правильность данных поля Откуда!");
                return;
            }
            try (Scanner s = new Scanner(inputLineTo.getText())) {
                if (s.hasNextInt()) {
                    to = s.nextInt();
                    if (to < 0) {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
                if (s.hasNext()) {
                    throw new Exception();
                }
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Проверьте правильность данных поля Куда!");
                return;
            }
            if(from == to){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Вершины ребра совпадают!");
                return;
            }
            try (Scanner s = new Scanner(inputLineWeight.getText())) {
                if (s.hasNextDouble()) {
                    weight = s.nextDouble();
                    if (weight <= 0) {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
                if (s.hasNext()) {
                    throw new Exception();
                }
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Проверьте правильность данных поля Вес ребра!");
                return;
            }
            Edge tmp = new Edge(from, to, weight);
            boolean isExists = false;
            for(Edge edge : listOfEdges){
                if(edge.isEqual(tmp)){
                    isExists = true;
                    break;
                }
            }
            if(isExists){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Ребро уже существует!" );
            } else {
                graph.addEdge(tmp);
                edgeList.addItem(tmp.toString());
                listOfEdges.add(tmp);
                listData.append("Добавлено ребро " + tmp.toString() + '\n');
                if (listOfVertexes.indexOf(from) == -1) {
                    listOfVertexes.add(from);
                    vertexList.addItem("" + from);
                }
                if (listOfVertexes.indexOf(to) == -1) {
                    listOfVertexes.add(to);
                    vertexList.addItem("" + to);
                }
                inputLineFrom.setText("");
                inputLineTo.setText("");
                inputLineWeight.setText("");
            }
        });
        inputLineFrom.addActionListener(e -> {
            // Отображение введенного текста
            try (Scanner s = new Scanner(inputLineFrom.getText())) {
                if (s.hasNextInt()) {
                    from = s.nextInt();
                    if (from < 0) {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
                if (s.hasNext()) {
                    throw new Exception();
                }
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Проверьте правильность данных поля Откуда!");
                return;
            }
            inputLineTo.requestFocusInWindow();
        });
        inputLineTo.addActionListener(e -> {
            // Отображение введенного текста
            try (Scanner s = new Scanner(inputLineTo.getText())) {
                if (s.hasNextInt()) {
                    to = s.nextInt();
                    if (to < 0) {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
                if (s.hasNext()) {
                    throw new Exception();
                }
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Проверьте правильность данных поля Куда!");
                return;
            }
            if(from == to){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Вершины ребра совпадают!");
                return;
            }
            inputLineWeight.requestFocusInWindow();
        });
        inputLineWeight.addActionListener(e -> {
            // Отображение введенного текста
            try (Scanner s = new Scanner(inputLineWeight.getText())) {
                if (s.hasNextDouble()) {
                    weight = s.nextDouble();
                    if (weight < 0) {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
                if (s.hasNext()) {
                    throw new Exception();
                }
            }
            catch(Exception exc) {
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Проверьте правильность данных поля Вес ребра!");
                return;
            }
            Edge tmp = new Edge(from, to, weight);
            boolean isExists = false;
            for(Edge edge : listOfEdges){
                if(edge.isEqual(tmp)){
                    isExists = true;
                    break;
                }
            }
            if(isExists){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Ребро уже существует!" );
            } else {
                graph.addEdge(tmp);
                edgeList.addItem(tmp.toString());
                listOfEdges.add(tmp);
                listData.append("Добавлено ребро " + tmp.toString() + '\n');
                if (listOfVertexes.indexOf(from) == -1) {
                    listOfVertexes.add(from);
                    vertexList.addItem("" + from);
                }
                if (listOfVertexes.indexOf(to) == -1) {
                    listOfVertexes.add(to);
                    vertexList.addItem("" + to);
                }
                inputLineFrom.setText("");
                inputLineTo.setText("");
                inputLineWeight.setText("");
                inputLineFrom.requestFocusInWindow();
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {

                UnionFind uf = new UnionFind(graph.V());
                if(uf.isConnected(graph)){
                    String title = "Окно подтверждения";
                    UIManager.put("OptionPane.yesButtonText", "Да");
                    UIManager.put("OptionPane.noButtonText", "Нет");
                    int check = JOptionPane.showConfirmDialog(null, "Выйти из редактора?", title, JOptionPane.YES_NO_OPTION);
                    if (check == JOptionPane.YES_OPTION) {
                        //действия по сохранению графа
                        graphPanel.setGraph(graph);
                        //graphPanel.setMST(graph);
                        parent.setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(Edit_window.this,
                            "Перед выходом сделайте граф связным" );
                }
            }
        });
    }
}