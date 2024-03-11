// Modified taskManagement class with additional column for estimated task time
package coreFunctions;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    pomodoro pomodoro;
    private final String FILE_PATH = "tasks.txt";

    public taskManagement() {
        setLayout(new BorderLayout());
        // Create table model with columns
        String[] columns = {"Task Name", "Time", "Date", "Time Remaining", "Estimated Time"};
        tableModel = new DefaultTableModel(columns, 0);
        taskTable = new JTable(tableModel);
        // Allow resizing columns
        taskTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Wrap text in cells to avoid truncation
        taskTable.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.LEFT);
                return this;
            }
        });

        // Add table to scroll pane for scrolling if required
        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons for add, delete, and sort operations
        addButton = new JButton("Add Task");
        deleteButton = new JButton("Delete Task");
        sortButton = new JButton("Sort by Time");
        start = new JButton("Start Timer");

        // Add buttons to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(start);
        add(buttonPanel, BorderLayout.SOUTH);

        // Implement action listeners for buttons (add, delete, sort) - Placeholder logic
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt user for task details (name, time, date)
                // For simplicity, let's use JOptionPane for input dialog
                String taskName = JOptionPane.showInputDialog("Enter Task Name:");
                String time = JOptionPane.showInputDialog("Enter Time (HH:mm):");
                String date = JOptionPane.showInputDialog("Enter Date (dd/MM/yyyy):");
                String estimatedTime = JOptionPane.showInputDialog("Enter Estimated Time (minutes):");

                // Add new row to the table model
                Object[] newRow = {taskName, time, date, calculateTimeRemaining(time, date), estimatedTime};
                tableModel.addRow(newRow);

                // Create and add start button dynamically
                
                // Add start button to the panel

                saveTasksToFile(); // Save tasks to file after adding a new task
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) { // Check if a row is selected
                    tableModel.removeRow(selectedRow);
                    saveTasksToFile(); // Save tasks to file after deleting a task
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement logic to sort tasks by time (custom sorting algorithm or library)
                // For simplicity, let's assume sorting based on the second column (time)
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                taskTable.setRowSorter(sorter);
                sorter.setSortKeys(null); // Remove any existing sorting
                sorter.sort();
            }
        });
 
        // Load tasks from file on initialization
        loadTasksFromFile();
    }

    private String calculateTimeRemaining(String time, String date) {
    DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        try {
            Date taskDateTime = dateFormat.parse(time + " " + date);
            long timeDifferenceMillis = taskDateTime.getTime() - System.currentTimeMillis();
            long hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis) % 60;
            // Convert to positive values if negative
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    line.append(tableModel.getValueAt(i, j));
                    if (j < tableModel.getColumnCount() - 1) {
                        line.append(",");
                    }
                }
                writer.write(line.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getSelectedTaskEstimatedTime() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) { // Check if a row is selected
            // Assuming estimated time is in the fifth column and stored as string
            String estimatedTimeStr = (String) tableModel.getValueAt(selectedRow, 4);
            try {
                // Convert the estimated time string to integer (you might need to adjust this based on your actual data)
                return Integer.parseInt(estimatedTimeStr);
            } catch (NumberFormatException e) {
                // Handle if the string cannot be parsed to an integer
                e.printStackTrace();
            }
        }
        return 0; // Return 0 if no task is selected or if estimated time is not available
    }
    
    public String getSelectedTaskName() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) { // Check if a row is selected
            return (String) tableModel.getValueAt(selectedRow, 0); // Assuming task name is in the first column
        }
        return null; // Return null if no task is selected
    }
    
    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == tableModel.getColumnCount()) {
                    tableModel.addRow(parts);
                } else {
                    // Handle incorrect format or missing data in the file
                    System.err.println("Invalid data format in the file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addTaskStartListener(ActionListener listener) {
        this.taskStartListener = listener;
    }

    // Method to trigger task start event
    private void fireTaskStartEvent(ActionEvent evt) {
        if (taskStartListener != null) {
            taskStartListener.actionPerformed(evt);
        }
    }
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Management Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); // Set initial size of the window
        frame.setLocationRelativeTo(null); // Center the window on the screen
        frame.add(new taskManagement());
        frame.setVisible(true);
    }
}
