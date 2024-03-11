package coreFunctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class budgetTrack extends JPanel {
    private DefaultTableModel incomeTableModel;
    private DefaultTableModel expenseTableModel;
    private JTable incomeTable;
    private JTable expenseTable;
    private final String INCOME_FILE_PATH = "income.txt";
    private final String EXPENSE_FILE_PATH = "expenses.txt";

    public budgetTrack() {
        // Initialize JPanel
        super(new BorderLayout());

        // Create income table
        String[] incomeColumns = {"Type", "Amount"};
        incomeTableModel = new DefaultTableModel(incomeColumns, 0);
        incomeTable = new JTable(incomeTableModel);

        // Create expense table
        String[] expenseColumns = {"Type", "Amount"};
        expenseTableModel = new DefaultTableModel(expenseColumns, 0);
        expenseTable = new JTable(expenseTableModel);

        // Create scroll panes for tables
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);

        // Create panels to hold tables
        JPanel incomePanel = new JPanel(new BorderLayout());
        incomePanel.setBorder(BorderFactory.createTitledBorder("Income"));
        incomePanel.add(incomeScrollPane, BorderLayout.CENTER);

        JPanel expensePanel = new JPanel(new BorderLayout());
        expensePanel.setBorder(BorderFactory.createTitledBorder("Expenses"));
        expensePanel.add(expenseScrollPane, BorderLayout.CENTER);

        // Create buttons for income and expenses
        JButton incomeButton = new JButton("Income");
        JButton expenseButton = new JButton("Expense");

        // Add action listeners to the buttons
        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality to handle income button click
                String type = JOptionPane.showInputDialog("Enter Income Type:");
                String amount = JOptionPane.showInputDialog("Enter Income Amount:");
                if (type != null && amount != null && !type.isEmpty() && !amount.isEmpty()) {
                    Object[] newRow = {type, amount};
                    incomeTableModel.addRow(newRow);
                    saveIncomeData(); // Save income data to file
                } else {
                    JOptionPane.showMessageDialog(budgetTrack.this, "Please enter valid type and amount.");
                }
            }
        });

        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality to handle expense button click
                String type = JOptionPane.showInputDialog("Enter Expense Type:");
                String amount = JOptionPane.showInputDialog("Enter Expense Amount:");
                if (type != null && amount != null && !type.isEmpty() && !amount.isEmpty()) {
                    Object[] newRow = {type, amount};
                    expenseTableModel.addRow(newRow);
                    saveExpenseData(); // Save expense data to file
                } else {
                    JOptionPane.showMessageDialog(budgetTrack.this, "Please enter valid type and amount.");
                }
            }
        });

        // Create panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(incomeButton);
        buttonPanel.add(expenseButton);

        // Create main panel to hold both income and expense panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(incomePanel, BorderLayout.WEST);
        mainPanel.add(expensePanel, BorderLayout.EAST);

        add(mainPanel);

        // Load data from files
        loadIncomeData();
        loadExpenseData();
       
    }

    private void loadIncomeData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INCOME_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                incomeTableModel.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadExpenseData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(EXPENSE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                expenseTableModel.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveIncomeData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INCOME_FILE_PATH))) {
            for (int i = 0; i < incomeTableModel.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < incomeTableModel.getColumnCount(); j++) {
                    line.append(incomeTableModel.getValueAt(i, j));
                    if (j < incomeTableModel.getColumnCount() - 1) {
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

    private void saveExpenseData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(EXPENSE_FILE_PATH))) {
            for (int i = 0; i < expenseTableModel.getRowCount(); i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0; j < expenseTableModel.getColumnCount(); j++) {
                    line.append(expenseTableModel.getValueAt(i, j));
                    if (j < expenseTableModel.getColumnCount() - 1) {
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

    public static void main(String[] args) {
        JFrame frame = new JFrame("Budget Tracking");
        budgetTrack tracker = new budgetTrack(); // Create an instance of budgetTrack
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(tracker); // Add budgetTrack instance to the frame's content pane
        frame.pack();
        frame.setVisible(true);
    }
    
}
    