package coreFunctions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class budgetTrack extends JPanel {
    private DefaultTableModel incomeTableModel;
    private DefaultTableModel expenseTableModel;
    private JTable incomeTable;
    private JTable expenseTable;
    private JLabel totalIncomeLabel;
    private JLabel totalExpenseLabel;
    private final String INCOME_FILE_PATH = "income.txt";
    private final String EXPENSE_FILE_PATH = "expenses.txt";

    public budgetTrack() {         //constructor (6 lines)
        super(new BorderLayout());
        initializeTables();
        initializeButtons();
        initializeIncomePanels();
        initializeExpensePanels();
        loadDataFromFiles();
        updateTotalLabels();
    }

    private void initializeTables() {         // Initializes the income and expense tables (6 lines)
        String[] incomeColumns = {"Type", "Amount"};
        incomeTableModel = new DefaultTableModel(incomeColumns, 0);
        incomeTable = new JTable(incomeTableModel);

        String[] expenseColumns = {"Type", "Amount"};
        expenseTableModel = new DefaultTableModel(expenseColumns, 0);
        expenseTable = new JTable(expenseTableModel);
    }

    private void initializeButtons() {    //Creates and configures buttons for adding income, adding expense, and editing entries.(8 lines)
        JButton incomeButton = createButton("Income", e -> handleAddButton(incomeTableModel));
        JButton expenseButton = createButton("Expense", e -> handleAddButton(expenseTableModel));
        JButton editButton = createButton("Edit", e -> handleEditButton());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(incomeButton);
        buttonPanel.add(expenseButton);
        buttonPanel.add(editButton);
        
        add(buttonPanel, BorderLayout.NORTH);
    }

    private JButton createButton(String label, ActionListener actionListener) { // Helper method to create a button with 
        JButton button = new JButton(label);                                    // the specified label and action listener. (4 lines)
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(actionListener);
        return button;
    }

    private void initializeIncomePanels() {                                     // interface for income panel (9 lines)
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        JPanel incomePanel = createPanelWithBorderLayout("Income", incomeScrollPane);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(incomePanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalIncomeLabel = new JLabel("Total Income: 0");
        totalPanel.add(totalIncomeLabel);
        mainPanel.add(totalPanel, BorderLayout.SOUTH);
    }
    private void initializeExpensePanels() {                                    // interface for income panel (9 lines)
       
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);
        JPanel expensePanel = createPanelWithBorderLayout("Expenses", expenseScrollPane);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(expensePanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalExpenseLabel = new JLabel("Total Expense: 0");
        totalPanel.add(totalExpenseLabel);
        mainPanel.add(totalPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createPanelWithBorderLayout(String title, Component component) { //Helper method to create a panel with 
        JPanel panel = new JPanel(new BorderLayout());                              //a titled border and BorderLayout layout.(4 lines)
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void handleAddButton(DefaultTableModel tableModel) {                 //add button functionality
        String type = JOptionPane.showInputDialog("Enter Type:");
        String amount = JOptionPane.showInputDialog("Enter Amount:");
        if (isValidInput(type) && isValidInput(amount)) {
            Object[] newRow = {type, amount};
            tableModel.addRow(newRow);
            saveData(tableModel);
            updateTotalLabels();
        } else {
            showMessageDialog("Please enter valid type and amount.");
        }
    }

    private boolean isValidInput(String input) {                                // check validity(1 line)
        return input != null && !input.isEmpty();
    }

    private void handleEditButton() {                                           //declare necessary variables(4 lines)            
        JTable table = incomeTable.getSelectedRowCount() > 0 ? incomeTable : expenseTable;
        DefaultTableModel tableModel = table == incomeTable ? incomeTableModel : expenseTableModel;
        int selectedRow = table.getSelectedRow();
        handleEditButtonFunction(selectedRow, tableModel, table);
    }
    
    private void handleEditButtonFunction(int selectedRow, DefaultTableModel tableModel, JTable table){ // 
        if (selectedRow != -1) {
            String type = JOptionPane.showInputDialog("Enter Type:", table.getValueAt(selectedRow, 0));
            String amount = JOptionPane.showInputDialog("Enter Amount:", table.getValueAt(selectedRow, 1));
            if (isValidInput(type) && isValidInput(amount)) {
                tableModel.setValueAt(type, selectedRow, 0);
                tableModel.setValueAt(amount, selectedRow, 1);
                saveData(tableModel);
                updateTotalLabels();
            } else {
                showMessageDialog("Please enter valid type and amount.");
            }
        } 
        else {
            showMessageDialog("Please select a row to edit.");
        }
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(budgetTrack.this, message);
    }

    private void loadDataFromFiles() {
        loadData(INCOME_FILE_PATH, incomeTableModel);
        loadData(EXPENSE_FILE_PATH, expenseTableModel);
    }

    private void saveData(DefaultTableModel tableModel) {
        String filePath = (tableModel == incomeTableModel) ? INCOME_FILE_PATH : EXPENSE_FILE_PATH;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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

    private void loadData(String filePath, DefaultTableModel tableModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                tableModel.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTotalLabels() {
        totalIncomeLabel.setText("Total Income: " + calculateTotal(incomeTableModel));
        totalExpenseLabel.setText("Total Expense: " + calculateTotal(expenseTableModel));
    }

    private double calculateTotal(DefaultTableModel tableModel) {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String amountStr = (String) tableModel.getValueAt(i, 1);
            try {
                double amount = Double.parseDouble(amountStr);
                total += amount;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Budget Tracking");
            budgetTrack tracker = new budgetTrack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(tracker);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
