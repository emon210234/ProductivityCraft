package coreFunctions;

import javax.swing.table.DefaultTableModel;
import org.junit.Test;
import static org.junit.Assert.*;

public class budgetTrackTest {

    @Test
    public void testCalculateTotal() {
        budgetTrack budgetTracker = new budgetTrack();

        DefaultTableModel incomeTableModel = new DefaultTableModel();
        incomeTableModel.addColumn("Type");
        incomeTableModel.addColumn("Amount");
        incomeTableModel.addRow(new Object[]{"Salary", "1000"});
        incomeTableModel.addRow(new Object[]{"Bonus", "500"});

        double expectedIncomeTotal = 1500.0;
        double actualIncomeTotal = budgetTracker.calculateTotal(incomeTableModel);

        assertEquals(expectedIncomeTotal, actualIncomeTotal, 0.001);

        DefaultTableModel expenseTableModel = new DefaultTableModel();
        expenseTableModel.addColumn("Type");
        expenseTableModel.addColumn("Amount");
        expenseTableModel.addRow(new Object[]{"Food", "200"});
        expenseTableModel.addRow(new Object[]{"Rent", "700"});

        double expectedExpenseTotal = 900.0;
        double actualExpenseTotal = budgetTracker.calculateTotal(expenseTableModel);

        assertEquals(expectedExpenseTotal, actualExpenseTotal, 0.001);
    }
    
    //new
    public void testAddData() {
        // Creating an instance of budgetTrack
        budgetTrack budgetTracker = new budgetTrack();

        // Adding test data
        String type = "Salary";
        double amount = 2500.0;
        boolean isIncome = true;

        // Adding data using addData method
        budgetTracker.addData(type, amount, isIncome);

        // Retrieving the income table model
        DefaultTableModel incomeTableModel = budgetTracker.getIncomeTableModel();

        // Verifying the data added to the income table
        assertEquals(1, incomeTableModel.getRowCount()); // Expecting one row
        assertEquals(type, incomeTableModel.getValueAt(0, 0)); // Expecting type at first column
        assertEquals(amount, Double.parseDouble((String) incomeTableModel.getValueAt(0, 1)), 0.001); // Expecting amount at second column
    }
    
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        budgetTrack.main(args);
    }
}
