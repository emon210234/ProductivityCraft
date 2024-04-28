
package dataAccess;

import javax.swing.table.DefaultTableModel;
import java.io.*;

import javax.swing.table.DefaultTableModel;
import java.io.*;

public class budgetData {
    private final String INCOME_FILE_PATH = "income.txt";
    private final String EXPENSE_FILE_PATH = "expenses.txt";

    public void saveData(DefaultTableModel tableModel, String filePath) {
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

    public void loadData(DefaultTableModel tableModel, String filePath) {
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
    
    
    public String getIncomeFilePath() {
        return INCOME_FILE_PATH;
    }

    public String getExpenseFilePath() {
        return EXPENSE_FILE_PATH;
    }
    
    
}

