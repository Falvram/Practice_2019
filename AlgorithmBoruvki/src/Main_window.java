import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.TimerTask;
import java.util.Timer;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

public class Main_window extends JFrame{
    private JButton editButton = new JButton("Редактирование графа"); //кнопка
    private JButton moveForward = new JButton("Вперёд");
    private JButton moveBackward = new JButton("Назад");
    private JButton moveToEnd = new JButton("До конца");
    private JButton saveButton = new JButton("Сохранить результат в файл");
    private JButton exitButton = new JButton("Завершить работу");
    private JButton start = new JButton("Запуск по таймеру");
    private JButton stop = new JButton("Пауза");
    private JTextArea logging = new JTextArea(); //вывод логов
    private JPanel rootPanel = new JPanel(); //основное рабочее окно
    private GraphVizualizer graphPanel = new GraphVizualizer(); //окно построения графа
    private Timer timer = new Timer();


    public static final double WIDTH = 1230;
    public static final double HEIGHT = 925;
    public static double coeff;

    public Main_window () {
        super("Главное окно");
        JButton[] arrayButtons = {editButton, moveForward, moveBackward,
                moveToEnd, saveButton, exitButton, start, stop};
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        coeff =  0.8*screenSize.height / HEIGHT;
        setBounds((int)((screenSize.width - WIDTH*coeff)/2),
                (int)((screenSize.height - HEIGHT*coeff)/2),
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
        for(JButton current : arrayButtons) {
            current.setFont(fontButton);
            //цвет
            current.setBackground(Color.cyan);
            //убрать выделение вокруг текста кнопки
            current.setFocusPainted(false);
            //установка рамок
            current.setBorder(BorderFactory.createLineBorder(Color.gray,2));
        }
        logging.setFont(fontLogging);
        //размеры кнопок
        editButton.setBounds((int)(10*coeff),(int)(5*coeff),(int)(410*coeff),(int)(50*coeff));
        moveToEnd.setBounds((int)(460*coeff),(int)(5*coeff),(int)(220*coeff),(int)(50*coeff));
        moveForward.setBounds((int)(720*coeff),(int)(5*coeff),(int)(220*coeff),(int)(50*coeff));
        moveBackward.setBounds((int)(980*coeff),(int)(5*coeff),(int)(220*coeff),(int)(50*coeff));
        saveButton.setBounds((int)(10*coeff),(int)(820*coeff),(int)(670*coeff),(int)(50*coeff));
        exitButton.setBounds((int)(720*coeff),(int)(820*coeff),(int)(480*coeff),(int)(50*coeff));
        scrollPane.setBounds((int)(720*coeff),(int)(125*coeff),(int)(480*coeff),(int)(685*coeff));
        graphPanel.setBounds((int)(10*coeff),(int)(65*coeff),(int)(670*coeff),(int)(745*coeff));
        start.setBounds((int)(720*coeff),(int)(65*coeff),(int)(220*coeff),(int)(50*coeff));
        stop.setBounds((int)(980*coeff),(int)(65*coeff),(int)(220*coeff),(int)(50*coeff));
        //Цвета полей
        rootPanel.setBackground(Color.darkGray);
        graphPanel.setBackground(Color.gray);
        logging.setBackground(Color.gray);
        logging.setForeground(Color.white);
        //добавление объектов на панель
        rootPanel.add(editButton);
        rootPanel.add(moveForward);
        rootPanel.add(moveBackward);
        rootPanel.add(moveToEnd);
        rootPanel.add(saveButton);
        rootPanel.add(exitButton);
        rootPanel.add(scrollPane);
        rootPanel.add(graphPanel);
        rootPanel.add(start);
        rootPanel.add(stop);
        //устоновка таймера

        //установка неактивной кнопки назад
        stop.setEnabled(false);
        moveBackward.setEnabled(false);
        //добавление действий
        editButton.addActionListener(e->{
            Edit_window editPanel = new Edit_window(rootPanel, Main.graph, graphPanel);
            editPanel.setVisible(true);
            Main.algorithm = null;
            moveToEnd.setEnabled(true);
            moveBackward.setEnabled(false);
            moveForward.setEnabled(true);
            stop.setEnabled(false);
            start.setEnabled(true);
            logging.setText("");
            timer.cancel();
            timer.purge();
        });
        moveForward.addActionListener(e ->{
            if(Main.graph.V() != 0) {
                if (Main.algorithm == null) {
                    Main.algorithm = new AlgorithmBoruvki(Main.graph);
                }
                LinkedList<Edge> tmp = Main.algorithm.nextStep();
                graphPanel.setMSTEdges(tmp);
                if (tmp.size() == Main.graph.V() - 1) {
                    start.setEnabled(false);
                    moveForward.setEnabled(false);
                    moveToEnd.setEnabled(false);
                }
                moveBackward.setEnabled(true);
                logging.append("Итерация алгоритма Борувки №" + Main.algorithm.getIteration() + '\n'
                        + "Добавлены следующие ребра:\n");
                for (Edge edge : Main.algorithm.deletedEdges()) {
                    logging.append(edge.toString() + '\n');
                }
                logging.setCaretPosition(logging.getDocument().getLength());
            }
        });
        moveBackward.addActionListener(e-> {
            logging.append("Откат до итерации алгоритма Борувки №" + (Main.algorithm.getIteration() - 1) + '\n'
                    + "Удалены следующие ребра:\n");
            for (Edge edge : Main.algorithm.deletedEdges()) {
                logging.append(edge.toString() + '\n');
            }
            logging.setCaretPosition(logging.getDocument().getLength());
            LinkedList<Edge> tmp = Main.algorithm.previousStep();
            graphPanel.setMSTEdges(tmp);
            if (Main.algorithm.getIteration() == 0) {
                moveBackward.setEnabled(false);
            }
            start.setEnabled(true);
            moveForward.setEnabled(true);
            moveToEnd.setEnabled(true);
        });
        moveToEnd.addActionListener(e -> {
            if(Main.graph.V() != 0) {
                if (Main.algorithm == null) {
                    Main.algorithm = new AlgorithmBoruvki(Main.graph);
                }
                LinkedList<Edge> tmp = Main.algorithm.lastStep();
                graphPanel.setMSTEdges(tmp);
                moveForward.setEnabled(false);
                logging.append("Итерация алгоритма Борувки №" + Main.algorithm.getIteration() + '\n'
                        + "Добавлены следующие ребра:\n");
                for (Edge edge : tmp) {
                    logging.append(edge.toString() + '\n');
                }
                JScrollBar vertical = scrollPane.getVerticalScrollBar();
                vertical.setValue( vertical.getMaximum() );
                start.setEnabled(false);
                moveBackward.setEnabled(true);
                moveToEnd.setEnabled(false);
            }
        });
        saveButton.addActionListener(e -> {
            saveAction();
        });
        exitButton.addActionListener(e -> {
            closeAction();
        });
        start.addActionListener(e -> {
            if(Main.graph.V() != 0) {
                moveForward.setEnabled(false);
                moveBackward.setEnabled(false);
                moveToEnd.setEnabled(false);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (Main.algorithm == null) {
                            Main.algorithm = new AlgorithmBoruvki(Main.graph);
                        }
                        LinkedList<Edge> tmp = Main.algorithm.nextStep();
                        graphPanel.setMSTEdges(tmp);
                        logging.append("Итерация алгоритма Борувки №" + Main.algorithm.getIteration() + '\n'
                                + "Добавлены следующие ребра:\n");
                        for (Edge edge : Main.algorithm.deletedEdges()) {
                            logging.append(edge.toString() + '\n');
                        }
                        logging.setCaretPosition(logging.getDocument().getLength());
                        if(tmp.size() == Main.graph.V() - 1) {
                            moveBackward.setEnabled(true);
                            start.setEnabled(false);
                            stop.setEnabled(false);
                            timer.cancel();
                            timer.purge();
                        }
                    }
                };
                timer = new Timer();
                timer.schedule(timerTask, 500, 500);
                stop.setEnabled(true);
            }
        });
        stop.addActionListener(e ->{
            if (Main.algorithm != null && Main.algorithm.edges().size() == Main.graph.V() - 1) {
            } else {
                moveForward.setEnabled(true);
                moveToEnd.setEnabled(true);
            }
            moveBackward.setEnabled(true);
            stop.setEnabled(false);
            timer.cancel();
            timer.purge();
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                closeAction();
            }
        });
    }
    private void closeAction(){
        timer.cancel();
        timer.purge();
        String title = "Окно подтверждения";
        UIManager.put("OptionPane.yesButtonText", "Да");
        UIManager.put("OptionPane.noButtonText", "Нет");
        UIManager.put("OptionPane.cancelButtonText", "Отмена");
        int check = JOptionPane.showConfirmDialog(null,"Сохранить результат перед закрытием?", title, JOptionPane.YES_NO_CANCEL_OPTION);
        if (check == JOptionPane.YES_OPTION){
            if(!saveAction()) return;
            dispose();
            System.exit(0);
        }
        else if (check == JOptionPane.NO_OPTION) {
            dispose();
            System.exit(0);
        }
    }
    private boolean saveAction(){
        if(Main.algorithm == null){
            JOptionPane.showMessageDialog(Main_window.this,
                    "Нечего сохранять!" );
            return false;
        }
        if(Main.algorithm.edges().size() != Main.graph.V() - 1){
            JOptionPane.showMessageDialog(Main_window.this,
                    "Завершите алгоритм!" );
            return false;
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(Main_window.this,
                    "Forbidden.");
            return false;
        }
        JFileChooser fd = new JFileChooser(new File(".").getAbsolutePath()); //дилоговое окно
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
        fd.setFileFilter(filter);
        fd.setAcceptAllFileFilterUsed(false);

        int ret = fd.showDialog(null,"Выбор файла");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File graphWrite = fd.getSelectedFile(); //получение выбранного файла
            File save;
            StringBuilder str = new StringBuilder(graphWrite.getPath());
            String newStr = str.substring(0, str.lastIndexOf(graphWrite.getName()));
            int index = graphWrite.getName().lastIndexOf(".");
            if(index == -1) {
                save = new File(newStr,
                        graphWrite.getName() + ".txt");
            } else {
                save = new File(newStr,
                        graphWrite.getName().substring(0, index) + ".txt");
            }
            if (graphWrite.exists()) {
                graphWrite.delete();
            }
            try {
                graphWrite.renameTo(save);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Main_window.this,
                        "Не удалось открыть файл!");
                return false;
            }
            try(BufferedWriter  writerResult = new BufferedWriter( new FileWriter(save)))
            {
                writerResult.write("Для графа:");
                writerResult.newLine();
                for (Edge edge : Main.graph.edges()) {
                    writerResult.write(edge.toString());
                    writerResult.newLine();
                }
                writerResult.write("Построено минимальное остовное дерево с помошью алгоритма Борувки:");
                writerResult.newLine();
                for (Edge edge : Main.algorithm.edges()) {
                    writerResult.write(edge.toString());
                    writerResult.newLine();
                }
                writerResult.flush();
            }
            catch(IOException ex){
                JOptionPane.showMessageDialog(Main_window.this,
                        "Не удалось записать в файл!" );
                return false;
            }
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(Main_window.this,
                        "Forbidden.");
                return false;
            }
            JOptionPane.showMessageDialog(Main_window.this,
                    "Результат успешно сохранен в файл!" );
            return true;
        } else {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(Main_window.this,
                        "Forbidden.");
                return false;
            }
            JOptionPane.showMessageDialog(Main_window.this,
                    "Операция отменена.");
            return false;
        }
    }
}