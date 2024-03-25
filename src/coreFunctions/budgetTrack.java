package coreFunctions;

import dataAccess.budgetData;
import interfaceWindows.budgetTrackInterface;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private final budgetTrackInterface guiManager;
    private final budgetData fileHandler;

    public budgetTrack() {
        super(new BorderLayout());
        initializeTables();
        initializeButtons();
        initializePanels();
        updateTotalLabels();
        guiManager = new budgetTrackInterface();
        fileHandler = new budgetData();
        fileHandler.loadData(incomeTableModel, INCOME_FILE_PATH);
    }

    private void initializeTables() {
        String[] incomeColumns = {"Type", "Amount"};
        incomeTableModel = new DefaultTableModel(incomeColumns, 0);
        incomeTable = new JTable(incomeTableModel);

        String[] expenseColumns = {"Type", "Amount"};
        expenseTableModel = new DefaultTableModel(expenseColumns, 0);
        expenseTable = new JTable(expenseTableModel);
    }

    private void initializeButtons() {
        JButton incomeButton = guiManager.createButton("Income", e -> handleAddButton(incomeTableModel));
        JButton expenseButton = guiManager.createButton("Expense", e -> handleAddButton(expenseTableModel));
        JButton editButton = guiManager.createButton("Edit", e -> handleEditButton());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(incomeButton);
        buttonPanel.add(expenseButton);
        buttonPanel.add(editButton);
        
        add(buttonPanel, BorderLayout.NORTH);
    }

    private JButton createButton(String label, ActionListener actionListener) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addActionListener(actionListener);
        return button;
    }

    private void initializePanels() {
        JScrollPane incomeScrollPane = new JScrollPane(incomeTable);
        JScrollPane expenseScrollPane = new JScrollPane(expenseTable);

        JPanel incomePanel = createPanelWithBorderLayout("Income", incomeScrollPane);
        JPanel expensePanel = createPanelWithBorderLayout("Expenses", expenseScrollPane);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(incomePanel, BorderLayout.WEST);
        mainPanel.add(expensePanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalIncomeLabel = new JLabel("Total Income: 0");
        totalExpenseLabel = new JLabel("Total Expense: 0");
        totalPanel.add(totalIncomeLabel);
        totalPanel.add(totalExpenseLabel);
        mainPanel.add(totalPanel, BorderLayout.SOUTH);
    }

    private JPanel createPanelWithBorderLayout(String title, Component component) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void handleAddButton(DefaultTableModel tableModel) {
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

    private boolean isValidInput(String input) {
        return input != null && !input.isEmpty();
    }

    private void handleEditButton() {
        JTable table = incomeTable.getSelectedRowCount() > 0 ? incomeTable : expenseTable;
        DefaultTableModel tableModel = table == incomeTable ? incomeTableModel : expenseTableModel;
        int selectedRow = table.getSelectedRow();
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
        } else {
            showMessageDialog("Please select a row to edit.");
        }
    }

    private void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(budgetTrack.this, message);
    }

   

    private void saveData(DefaultTableModel tableModel) {
        if (tableModel == incomeTableModel) {
            fileHandler.saveData(tableModel, fileHandler.getIncomeFilePath());
        } else {
            fileHandler.saveData(tableModel, fileHandler.getExpenseFilePath());
        }
    }

    private void loadData(String filePath, DefaultTableModel tableModel) {
        fileHandler.loadData(tableModel, filePath);
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
