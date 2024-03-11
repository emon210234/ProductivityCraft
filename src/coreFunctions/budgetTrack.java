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
        JButton editButton = new JButton("Edit");

        // Increase font size for buttons
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        incomeButton.setFont(buttonFont);
        expenseButton.setFont(buttonFont);
        editButton.setFont(buttonFont);

        // Add action listeners to the buttons
        incomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddButton(incomeTableModel);
            }
        });

        expenseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddButton(expenseTableModel);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleEditButton();
            }
        });

        // Add mouse listener to tables to enable row selection
        incomeTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleEditButton();
                }
            }
        });

        expenseTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleEditButton();
                }
            }
        });

        // Create panel to hold buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(incomeButton);
        buttonPanel.add(expenseButton);
        buttonPanel.add(editButton);

        // Create main panel to hold both income and expense panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(incomePanel, BorderLayout.WEST);
        mainPanel.add(expensePanel, BorderLayout.EAST);

        // Add total income and total expense labels
        totalIncomeLabel = new JLabel("Total Income: 0");
        totalExpenseLabel = new JLabel("Total Expense: 0");
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        totalPanel.add(totalIncomeLabel);
        totalPanel.add(totalExpenseLabel);
        mainPanel.add(totalPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Load data from files
        loadIncomeData();
        loadExpenseData();
        updateTotalLabels();
    }

    private void handleAddButton(DefaultTableModel tableModel) {
        String type = JOptionPane.showInputDialog("Enter Type:");
        String amount = JOptionPane.showInputDialog("Enter Amount:");
        if (type != null && amount != null && !type.isEmpty() && !amount.isEmpty()) {
            Object[] newRow = {type, amount};
            tableModel.addRow(newRow);
            saveData(tableModel); // Save data to file
            updateTotalLabels();
        } else {
            JOptionPane.showMessageDialog(budgetTrack.this, "Please enter valid type and amount.");
        }
    }

    private void handleEditButton() {
        JTable table = incomeTable.getSelectedRowCount() > 0 ? incomeTable : expenseTable;
        DefaultTableModel tableModel = table == incomeTable ? incomeTableModel : expenseTableModel;
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String type = JOptionPane.showInputDialog("Enter Type:", table.getValueAt(selectedRow, 0));
            String amount = JOptionPane.showInputDialog("Enter Amount:", table.getValueAt(selectedRow, 1));
            if (type != null && amount != null && !type.isEmpty() && !amount.isEmpty()) {
                tableModel.setValueAt(type, selectedRow, 0);
                tableModel.setValueAt(amount, selectedRow, 1);
                saveData(tableModel); // Save data to file
                updateTotalLabels();
            } else {
                JOptionPane.showMessageDialog(budgetTrack.this, "Please enter valid type and amount.");
            }
        } else {
            JOptionPane.showMessageDialog(budgetTrack.this, "Please select a row to edit.");
        }
    }

    private void loadIncomeData() {
        loadData(INCOME_FILE_PATH, incomeTableModel);
    }

    private void loadExpenseData() {
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
        double totalIncome = calculateTotal(incomeTableModel);
        double totalExpense = calculateTotal(expenseTableModel);
        totalIncomeLabel.setText("Total Income: " + totalIncome);
        totalExpenseLabel.setText("Total Expense: " + totalExpense);
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
        JFrame frame = new JFrame("Budget Tracking");
        budgetTrack tracker = new budgetTrack(); // Create an instance of budgetTrack
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(tracker); // Add budgetTrack instance to the frame's content pane
        frame.pack();
        frame.setVisible(true);
    }
}
