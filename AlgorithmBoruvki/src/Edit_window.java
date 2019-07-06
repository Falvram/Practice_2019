import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;

public class Edit_window extends JFrame{
    private JButton readButton = new JButton("Считать данные из файла");
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

    public Edit_window(JPanel parent) {
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

        //стиль текста
        Font fontButton = new Font(null, 3, 16); //выбор шрифта
        Font fontList = new Font(null, 1, 14);
        readButton.setFont(fontButton);
        removeEdge.setFont(fontButton);
        removeVertex.setFont(fontButton);
        deleteGraph.setFont(fontButton);
        saveGraph.setFont(fontButton);
        addData.setFont(fontButton);
        listData.setFont(fontList);
        label.setFont(fontButton);
        inputLine.setFont(fontButton);
        //размеры кнопок
        readButton.setBounds((int)(10*coeff),(int)(120*coeff),(int)(740*coeff),(int)(50*coeff));
        removeEdge.setBounds((int)(10*coeff),(int)(190*coeff),(int)(350*coeff),(int)(50*coeff));
        removeVertex.setBounds((int)(400*coeff),(int)(190*coeff),(int)(350*coeff),(int)(50*coeff));
        deleteGraph.setBounds((int)(10*coeff),(int)(260*coeff),(int)(740*coeff),(int)(50*coeff));
        saveGraph.setBounds((int)(10*coeff),(int)(330*coeff),(int)(740*coeff),(int)(50*coeff));
        addData.setBounds((int)(450*coeff),(int)(50*coeff),(int)(300*coeff),(int)(50*coeff));
        label.setBounds((int)(10*coeff),(int)(5*coeff),(int)(400*coeff),(int)(50*coeff));
        scrollPane.setBounds((int)(790*coeff),(int)(50*coeff),(int)(410*coeff),(int)(330*coeff));
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
        editPanel.add(deleteGraph);
        editPanel.add(saveGraph);
        editPanel.add(addData);
        editPanel.add(label);
        editPanel.add(scrollPane);
        editPanel.add(inputLine);
        //добавление действий
        readButton.addActionListener(new ReadButtonEvent());
        removeEdge.addActionListener(new RemoveEdgeEvent());
        removeVertex.addActionListener(new RemoveVertexEvent());
        deleteGraph.addActionListener(new DeleteGraphEvent());
        saveGraph.addActionListener(e -> {
            String message = "Выбрано сохранение";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
            parent.setVisible(true);
            dispose();
        });
        addData.addActionListener(new AddDataEvent());
        inputLine.addActionListener(e -> {
            // Отображение введенного текста
            JOptionPane.showMessageDialog(Edit_window.this,
                    "Вы ввели : " + inputLine.getText(),"Output",JOptionPane.PLAIN_MESSAGE);
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String title = "Окно подтверждения";
                UIManager.put("OptionPane.yesButtonText", "Да");
                UIManager.put("OptionPane.noButtonText", "Нет");
                UIManager.put("OptionPane.cancelButtonText", "Отмена");
                int check = JOptionPane.showConfirmDialog(null,"Сохранить граф перед выходом?", title, JOptionPane.YES_NO_CANCEL_OPTION);
                if (check == JOptionPane.YES_OPTION){
                    //действия по сохранению графа
                    parent.setVisible(true);
                    dispose();
                }
                else if (check == JOptionPane.NO_OPTION) {
                    //выход без сохранения графа
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

    class RemoveEdgeEvent implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String message = "Выбрано удаление ребра";
            JOptionPane.showMessageDialog(null, message, "Output", JOptionPane.PLAIN_MESSAGE);
        }
    }

    class RemoveVertexEvent implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            String message = "Выбрано удаление вершины";
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