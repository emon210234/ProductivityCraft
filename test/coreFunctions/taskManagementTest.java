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

        String taskName = "Test Task";
        String time = "12:00";
        String date = "01/04/2024";
        String estimatedTime = "60"; // 60 minutes

        taskManagement.addTask(taskName, time, date, estimatedTime);
        assertEquals(1, taskManagement.tableModel.getRowCount());
        
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
    
    @Test
    public void testGetNumberOfTasks() {
        taskManagement taskManager = new taskManagement();
        taskManager.addTask("Task 1", "12:00", "2024-04-22", "60");
        taskManager.addTask("Task 2", "14:00", "2024-04-23", "30");
        
        assertEquals(2, taskManager.getNumberOfTasks());
    }
}
