package coreFunctions;

import java.awt.event.ActionListener;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class taskManagementTest {
    private taskManagement taskManagement;
    
    public taskManagementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getSelectedTaskEstimatedTime method, of class taskManagement.
     */
    public void testAddTask() {
        taskManagement = new taskManagement(); // Initialize your TaskManagement instance

        // Prepare test data
        String taskName = "Test Task";
        String time = "12:00";
        String date = "01/04/2024";
        String estimatedTime = "60"; // 60 minutes

        // Call the method under test
        taskManagement.addTask(taskName, time, date, estimatedTime);

        // Assert.assertEquals(expectedValue, actualValue);
        assertEquals(1, taskManagement.tableModel.getRowCount());

        // Add more assertions to verify the content of the added row if needed
        assertEquals(taskName, taskManagement.tableModel.getValueAt(0, 0));
        assertEquals(time, taskManagement.tableModel.getValueAt(0, 1));
        assertEquals(date, taskManagement.tableModel.getValueAt(0, 2));
        
    }
    @Test
    public void testTaskTime() {
        System.out.println("getSelectedTaskEstimatedTime");
        taskManagement instance = new taskManagement();
        int expResult = 0;
        int result = instance.getSelectedTaskEstimatedTime();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of getSelectedTaskName method, of class taskManagement.
     */
    @Test
    public void testTaskName() {
        System.out.println("getSelectedTaskName");
        taskManagement instance = new taskManagement();
        String expResult = null;
        String result = instance.getSelectedTaskName();
        assertEquals(expResult, result);
    }

    /**
     * Test of addTaskStartListener method, of class taskManagement.
     */
    @Test
    public void testAddTaskStartListener() {
        System.out.println("addTaskStartListener");
        ActionListener listener = null;
        taskManagement instance = new taskManagement();
        instance.addTaskStartListener(listener);
        //to check whether the action performed object is set properly
    }

    /**
     * Test of main method, of class taskManagement.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        taskManagement.main(args);
    }
    
}
