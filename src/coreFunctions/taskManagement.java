package coreFunctions;

import dataAccess.taskData;
import interfaceWindows.taskInterface;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class taskManagement extends JPanel {

    private ActionListener taskStartListener;
    private DefaultTableModel tableModel;
    private JTable taskTable;
    private JButton addButton;
    private JButton deleteButton;
    private JButton sortButton;
    private JButton start;
    private final String FILE_PATH = "tasks.txt";
    private final taskInterface guiManager;
    private final taskData fileHandler;

    public taskManagement() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 220, 220));
        createTableModel();
        createTable();
        createButtons();
        addActionListeners();
        loadTasksFromFile();
        guiManager = new taskInterface();
        fileHandler = new taskData();
    }

    private void createTableModel() {
        String[] columns = {"Task Name", "Time", "Date", "Time Remaining", "Estimated Time"};
        tableModel = new DefaultTableModel(columns, 0);
    }

    private void createTable() {
        taskTable = new JTable(tableModel);
        taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        taskTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.LEFT);
                setFont(getFont().deriveFont(Font.BOLD, 16));
                return this;
            }
        });
        taskTable.setBackground(new Color(220, 255, 220));
        JTableHeader header = taskTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.setBackground(new Color(220, 255, 220));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void createButtons() {
        addButton = guiManager.createButton("Add Task", e -> addTask());
        deleteButton = guiManager.createButton("Delete Task", e -> deleteTask());
        sortButton = guiManager.createButton("Sort by Time", e -> sortTasks());
        start = guiManager.createButton("Start Timer", e -> fireTaskStartEvent(e));

        JPanel buttonPanel = guiManager.createButtonPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(start);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.setBackground(new Color(255, 165, 0));
        deleteButton.setBackground(new Color(255, 0, 0));
        sortButton.setBackground(new Color(0, 191, 255));
        start.setBackground(new Color(255, 140, 0));
    }

    private void addActionListeners() {
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());
        sortButton.addActionListener(e -> sortTasks());
    }

    private void addTask() {
        String taskName = JOptionPane.showInputDialog("Enter Task Name:");
        String time = JOptionPane.showInputDialog("Enter Time (HH:mm):");
        String date = JOptionPane.showInputDialog("Enter Date (dd/MM/yyyy):");
        String estimatedTime = JOptionPane.showInputDialog("Enter Estimated Time (minutes):");
        Object[] newRow = {taskName, time, date, calculateTimeRemaining(time, date), estimatedTime};
        tableModel.addRow(newRow);
        saveTasksToFile();
    }

    private void deleteTask() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveTasksToFile();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row to delete.");
        }
    }

    private void sortTasks() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        taskTable.setRowSorter(sorter);
        sorter.setSortKeys(null);
        sorter.sort();
    }

    private String calculateTimeRemaining(String time, String date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        try {
            Date taskDateTime = dateFormat.parse(time + " " + date);
            long timeDifferenceMillis = taskDateTime.getTime() - System.currentTimeMillis();
            long hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis) % 60;
            if (hours < 0) {
                hours = -hours;
                minutes = -minutes;
            }
            return String.format("%02d:%02d", hours, minutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void saveTasksToFile() {
        fileHandler.saveData(tableModel, FILE_PATH); // Use the file handling class to save data
    }

    public int getSelectedTaskEstimatedTime() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            String estimatedTimeStr = (String) tableModel.getValueAt(selectedRow, 4);
            try {
                return Integer.parseInt(estimatedTimeStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public String getSelectedTaskName() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            return (String) tableModel.getValueAt(selectedRow, 0);
        }
        return null;
    }

    public void addTaskStartListener(ActionListener listener) {
        this.taskStartListener = listener;
    }

    private void fireTaskStartEvent(ActionEvent evt) {
        if (taskStartListener != null) {
            taskStartListener.actionPerformed(evt);
        }
    }

    private void loadTasksFromFile() {
        fileHandler.loadData(tableModel, FILE_PATH); // Use the file handling class to load data
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Task Management Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.add(new taskManagement());
        frame.setVisible(true);
    }
}
