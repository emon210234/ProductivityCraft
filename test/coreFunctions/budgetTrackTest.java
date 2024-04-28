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
    }
}
