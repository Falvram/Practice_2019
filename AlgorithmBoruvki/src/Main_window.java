import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.io.*;

public class Main_window extends JFrame{
    private JButton editButton = new JButton("Редактирование графа"); //кнопка
    private JButton moveForward = new JButton("Вперёд");
    private JButton moveBackward = new JButton("Назад");
    private JButton moveToEnd = new JButton("До конца");
    private JButton saveButton = new JButton("Сохранить результат в файл");
    private JButton exitButton = new JButton("Завершить работу");
    private JTextArea logging = new JTextArea(); //вывод логов
    private JPanel rootPanel = new JPanel(); //основное рабочее окно
    private GraphVizualizer graphPanel = new GraphVizualizer(); //окно построения графа
    private AlgorithmBoruvki algorithm = null;
    public Graph graph = new Graph();

    public static final double WIDTH = 1230;
    public static final double HEIGHT = 925;
    public static double coeff;
    //?? для визуализации графа окно

    public Main_window () {
        super("Главное окно");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        coeff =  0.8*screenSize.height / HEIGHT;
        setBounds((int)((screenSize.width - WIDTH*coeff)/2),
                (int)((screenSize.height - HEIGHT*coeff)/2) ,
                (int)(WIDTH*coeff),(int)(HEIGHT*coeff)); //размер окна
        setResizable(false); //запрет на изменения размера окна
        rootPanel.setLayout(null);
        add(rootPanel);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        logging.setEditable(false); //запрет на редактирование, ввод текста
        logging.setLineWrap(true);
        //добавление скроллбара
        final JScrollPane scrollPane = new JScrollPane(logging, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        Font fontButton = new Font(null, 3, 16); //выбор шрифта
        Font fontLogging = new Font(null, 1, 14);
        editButton.setFont(fontButton);
        moveForward.setFont(fontButton);
        moveBackward.setFont(fontButton);
        moveToEnd.setFont(fontButton);
        saveButton.setFont(fontButton);
        exitButton.setFont(fontButton);
        logging.setFont(fontLogging);
        //размеры кнопок
        editButton.setBounds((int)(10*coeff),(int)(5*coeff),(int)(410*coeff),(int)(50*coeff));
        moveToEnd.setBounds((int)(460*coeff),(int)(5*coeff),(int)(220*coeff),(int)(50*coeff));
        moveForward.setBounds((int)(720*coeff),(int)(5*coeff),(int)(220*coeff),(int)(50*coeff));
        moveBackward.setBounds((int)(980*coeff),(int)(5*coeff),(int)(220*coeff),(int)(50*coeff));
        saveButton.setBounds((int)(10*coeff),(int)(820*coeff),(int)(670*coeff),(int)(50*coeff));
        exitButton.setBounds((int)(720*coeff),(int)(820*coeff),(int)(480*coeff),(int)(50*coeff));
        scrollPane.setBounds((int)(720*coeff),(int)(65*coeff),(int)(480*coeff),(int)(745*coeff));
        graphPanel.setBounds((int)(10*coeff),(int)(65*coeff),(int)(670*coeff),(int)(745*coeff));
        //Цвета полей
        rootPanel.setBackground(Color.darkGray);
        graphPanel.setBackground(Color.gray);
        logging.setBackground(Color.gray);
        logging.setForeground(Color.white);
        editButton.setBackground(Color.cyan);
        moveToEnd.setBackground(Color.cyan);
        moveForward.setBackground(Color.cyan);
        moveBackward.setBackground(Color.cyan);
        saveButton.setBackground(Color.cyan);
        exitButton.setBackground(Color.cyan);
        //установка рамок
        editButton.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        moveToEnd.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        moveForward.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        moveBackward.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        saveButton.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        exitButton.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        //убрать выделение вокруг текста кнопки
        editButton.setFocusPainted(false);
        moveForward.setFocusPainted(false);
        moveBackward.setFocusPainted(false);
        moveToEnd.setFocusPainted(false);
        saveButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);
        //добавление объектов на панель
        rootPanel.add(editButton);
        rootPanel.add(moveForward);
        rootPanel.add(moveBackward);
        rootPanel.add(moveToEnd);
        rootPanel.add(saveButton);
        rootPanel.add(exitButton);
        rootPanel.add(scrollPane);
        rootPanel.add(graphPanel);
        //установка неактивной кнопки назад
        moveBackward.setEnabled(false);
        //добавление действий
        editButton.addActionListener(new edit_button_event());
        moveForward.addActionListener(e ->{
            if(graph.V() != 0) {
                if (algorithm == null) {
                    algorithm = new AlgorithmBoruvki(graph);
                }
                LinkedList<Edge> tmp = algorithm.nextStep();
                graphPanel.setMSTEdges(tmp);
                if (tmp.size() == graph.V() - 1) {
                    moveForward.setEnabled(false);
                    moveToEnd.setEnabled(false);
                }
                moveBackward.setEnabled(true);
                logging.append("Итерация алгоритма Борувки №" + algorithm.getIteration() + '\n'
                        + "Добавлены следующие ребра:\n");
                for (Edge edge : algorithm.deletedEdges()) {
                    logging.append(edge.toString() + '\n');
                }
            }
        });
        moveBackward.addActionListener(e-> {
            logging.append("Откат до итерации алгоритма Борувки №" + (algorithm.getIteration() - 1) + '\n'
                    + "Удалены следующие ребра:\n");
            for (Edge edge : algorithm.deletedEdges()) {
                logging.append(edge.toString() + '\n');
            }
            LinkedList<Edge> tmp = algorithm.previousStep();
            graphPanel.setMSTEdges(tmp);
            if (algorithm.getIteration() == 0) {
                moveBackward.setEnabled(false);
            }
            moveForward.setEnabled(true);
            moveToEnd.setEnabled(true);
        });
        moveToEnd.addActionListener(e -> {
            if(graph.V() != 0) {
                if (algorithm == null) {
                    algorithm = new AlgorithmBoruvki(graph);
                }
                LinkedList<Edge> tmp = algorithm.lastStep();
                graphPanel.setMSTEdges(tmp);
                moveForward.setEnabled(false);
                logging.append("Итерация алгоритма Борувки №" + algorithm.getIteration() + '\n'
                        + "Добавлены следующие ребра:\n");
                for (Edge edge : tmp) {
                    logging.append(edge.toString() + '\n');
                }
                moveBackward.setEnabled(true);
                moveToEnd.setEnabled(false);
            }
        });
        saveButton.addActionListener(new save_button_event());
        exitButton.addActionListener(new exit_button_event());
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent we) {
                String title = "Окно подтверждения";
                UIManager.put("OptionPane.yesButtonText", "Да");
                UIManager.put("OptionPane.noButtonText", "Нет");
                UIManager.put("OptionPane.cancelButtonText", "Отмена");
                int check = JOptionPane.showConfirmDialog(null,"Сохранить результат перед закрытием?", title, JOptionPane.YES_NO_CANCEL_OPTION);
                if (check == JOptionPane.YES_OPTION) dispose(); // тут будут действия по сохранению результата
                else if (check == JOptionPane.NO_OPTION) dispose(); //тут не будет действий по сохранению результата
            }
        });
    }
    class edit_button_event implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            Edit_window editPanel = new Edit_window(rootPanel, graph, graphPanel);
            editPanel.setVisible(true);
        }
    }
    class save_button_event implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            if(algorithm == null){
                JOptionPane.showMessageDialog(Main_window.this,
                        "Запустите выполнение алгоритма кнопкой\nДо конца или Вперед!" );
                return;
            }
            File result = new File("./", "Result.txt");
            if(result.exists()) {
                result.delete();
            }
            try {
                result.createNewFile();
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(Main_window.this,
                        "Не удалось открыть файл!" );
                return;
            }
            try(BufferedWriter writerResult = new BufferedWriter(new FileWriter(result)))
            {
                writerResult.write("Для графа:");
                writerResult.newLine();
                for (Edge edge : graph.edges()) {
                    writerResult.write(edge.toString());
                    writerResult.newLine();
                }
                writerResult.write("Построено минимальное остовное дерево с помошью алгоритма Борувки:");
                writerResult.newLine();
                for (Edge edge : algorithm.edges()) {
                    writerResult.write(edge.toString());
                    writerResult.newLine();
                }
                writerResult.flush();
            }
            catch(IOException ex){
                JOptionPane.showMessageDialog(Main_window.this,
                        "Не удалось записать в файл!" );
                return;
            }
            JOptionPane.showMessageDialog(Main_window.this,
                    "Результат успешно сохранен в файл result.txt!" );
        }
    }

    class exit_button_event implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String title = "Окно подтверждения";
            UIManager.put("OptionPane.yesButtonText", "Да");
            UIManager.put("OptionPane.noButtonText", "Нет");
            UIManager.put("OptionPane.cancelButtonText", "Отмена");
            int check = JOptionPane.showConfirmDialog(null,"Сохранить результат перед закрытием?", title, JOptionPane.YES_NO_CANCEL_OPTION);
            if (check == JOptionPane.YES_OPTION) dispose(); // тут будут действия по сохранению результата
            else if (check == JOptionPane.NO_OPTION) dispose(); //тут не будет действий по сохранению результата
        }
    }
}