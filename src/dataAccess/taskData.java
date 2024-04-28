package dataAccess;

import javax.swing.table.DefaultTableModel;
import java.io.*;
public class taskData {
    public static void loadData(DefaultTableModel tableModel, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == tableModel.getColumnCount()) {
                    tableModel.addRow(parts);
                } else {
                    System.err.println("Invalid data format in the file: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static int getTotalEntries(DefaultTableModel tableModel) {
        return tableModel.getRowCount();
    }
    public static void saveData(DefaultTableModel tableModel, String filePath) {
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
}
