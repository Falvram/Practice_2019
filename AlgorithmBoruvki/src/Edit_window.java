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
    private JLabel label = new JLabel("Откуда:        Куда:        Вес ребра:");
    private JTextArea listData = new JTextArea(); //список добавленных вершин и рёбер
    private JTextField inputLine = new JTextField("",3);
    private JPanel editPanel = new JPanel(); //окно редактирования


    private static final double WIDTH = 1230;
    private static final double HEIGHT = 925;

    public Edit_window(JPanel parent, Graph graph) {
        super("Окно редактирования графа");
        parent.setVisible(false);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double coeff =  0.8*screenSize.height / HEIGHT;
        setBounds((int)((screenSize.width - WIDTH*coeff)/2),
                (int)((screenSize.height - HEIGHT*coeff)/2) ,
                (int)(WIDTH*coeff),(int)(HEIGHT*coeff)); //размер окна
        setResizable(false); //запрет на изменения размера окна
        editPanel.setLayout(null);
        add(editPanel);
        //установка действия на нажатие кнопки закрытия окна:
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        listData.setEditable(false); //запрет на редактирование, ввод текста
        listData.setLineWrap(true);
        //добавление скроллбара
        final JScrollPane scrollPane = new JScrollPane(listData, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputLine.setEnabled(true);

        LinkedList<Edge> listOfEdges = graph.edges();//список ребер графа
        LinkedList<Integer> listOfVertexes = graph.vertexes();//список ребер графа
        //соответствующие наборы строк
        String[] stringListOfEdges = new String[graph.E()];
        for(int i = 0; i < graph.E(); i++){
            stringListOfEdges[i] = listOfEdges.get(i).toString();
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
        label.setFont(fontButton);
        inputLine.setFont(fontButton);
        //размеры кнопок
        readButton.setBounds((int)(10*coeff),(int)(120*coeff),(int)(740*coeff),(int)(50*coeff));
        edgeList.setBounds((int)(10*coeff),(int)(190*coeff),(int)(350*coeff),(int)(50*coeff));
        vertexList.setBounds((int)(400*coeff),(int)(190*coeff),(int)(350*coeff),(int)(50*coeff));
        removeEdge.setBounds((int)(10*coeff),(int)(260*coeff),(int)(350*coeff),(int)(50*coeff));
        removeVertex.setBounds((int)(400*coeff),(int)(260*coeff),(int)(350*coeff),(int)(50*coeff));

        deleteGraph.setBounds((int)(10*coeff),(int)(330*coeff),(int)(740*coeff),(int)(50*coeff));
        saveGraph.setBounds((int)(10*coeff),(int)(400*coeff),(int)(740*coeff),(int)(50*coeff));
        addData.setBounds((int)(450*coeff),(int)(50*coeff),(int)(300*coeff),(int)(50*coeff));
        label.setBounds((int)(10*coeff),(int)(5*coeff),(int)(400*coeff),(int)(50*coeff));
        scrollPane.setBounds((int)(790*coeff),(int)(50*coeff),(int)(410*coeff),(int)(400*coeff));
        inputLine.setBounds((int)(10*coeff),(int)(50*coeff),(int)(400*coeff),(int)(50*coeff));
        //Цвета полей
        editPanel.setBackground(Color.darkGray);
        listData.setBackground(Color.gray);
        inputLine.setBackground(Color.gray);
        readButton.setBackground(Color.cyan);
        removeEdge.setBackground(Color.cyan);
        removeVertex.setBackground(Color.cyan);
        deleteGraph.setBackground(Color.cyan);
        saveGraph.setBackground(Color.cyan);
        addData.setBackground(Color.cyan);
        inputLine.setForeground(Color.white);
        listData.setForeground(Color.white);
        label.setForeground(Color.white);
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
        editPanel.add(removeEdge);
        editPanel.add(removeVertex);
        editPanel.add(edgeList);
        editPanel.add(vertexList);
        editPanel.add(deleteGraph);
        editPanel.add(saveGraph);
        editPanel.add(addData);
        editPanel.add(label);
        editPanel.add(scrollPane);
        editPanel.add(inputLine);
        //добавление действий
        readButton.addActionListener(e->{
            String path = inputLine.getText();
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
                    boolean isExists = false;
                    for(Edge edge : listOfEdges){
                        if(edge.isEqual(tmp)){
                            isExists = true;
                            break;
                        }
                    }
                    if(isExists){
                    } else {
                        graph.addEdge(tmp);
                        edgeList.addItem(tmp.toString());
                        listOfEdges.add(tmp);
                        listData.append("Добавлено ребро " + tmp.toString() + '\n');
                        if (listOfVertexes.indexOf(args[0]) == -1) {
                            listOfVertexes.add(args[0]);
                            vertexList.addItem("" + args[0]);
                        }
                        if (listOfVertexes.indexOf(args[1]) == -1) {
                            listOfVertexes.add(args[1]);
                            vertexList.addItem("" + args[1]);
                        }
                    }
                }
                catch(Exception exc){
                    JOptionPane.showMessageDialog(Edit_window.this,
                            "Проверьте правильность данных в файле" );
                    inputLine.setText("");
                    break;
                }
            }
            JOptionPane.showMessageDialog(Edit_window.this,
                    "Граф считан" );
            inputLine.setText("");
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
                }
            }
            try{
                graph.removeVertex(listOfVertexes.get(index));
            }
            catch(Exception exc){
            }
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
            String message = "Выбрано сохранение";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
            parent.setVisible(true);
            dispose();
        });
        addData.addActionListener(e -> {
            try (Scanner s = new Scanner(inputLine.getText())) {
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
                    if (listOfVertexes.indexOf(args[0]) == -1) {
                        listOfVertexes.add(args[0]);
                        vertexList.addItem("" + args[0]);
                    }
                    if (listOfVertexes.indexOf(args[1]) == -1) {
                        listOfVertexes.add(args[1]);
                        vertexList.addItem("" + args[1]);
                    }
                }
                inputLine.setText("");
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Недопустимый формат ввода" );
            }
        });
        inputLine.addActionListener(e -> {
            // Отображение введенного текста
            try (Scanner s = new Scanner(inputLine.getText())) {
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
                    if (listOfVertexes.indexOf(args[0]) == -1) {
                        listOfVertexes.add(args[0]);
                        vertexList.addItem("" + args[0]);
                    }
                    if (listOfVertexes.indexOf(args[1]) == -1) {
                        listOfVertexes.add(args[1]);
                        vertexList.addItem("" + args[1]);
                    }
                }
                inputLine.setText("");
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(Edit_window.this,
                        "Недопустимый формат ввода" );
            }

    });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String title = "Окно подтверждения";
                UIManager.put("OptionPane.yesButtonText", "Да");
                UIManager.put("OptionPane.noButtonText", "Нет");
                int check = JOptionPane.showConfirmDialog(null,"Выйти из редактора?", title, JOptionPane.YES_NO_OPTION);
                if (check == JOptionPane.YES_OPTION){
                    //действия по сохранению графа
                    parent.setVisible(true);
                    dispose();
                }
            }
        });
    }

    class ReadButtonEvent implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String message = "Выбрано чтение с файла";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class DeleteGraphEvent implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String message = "Выбрано удаление графа";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class AddDataEvent implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String message = "Выбрано добавление данных";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }
}