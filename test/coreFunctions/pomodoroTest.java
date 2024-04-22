//to check if the button functionalities work properly
package coreFunctions;

import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.swing.JLabel;


public class pomodoroTest {
    
    public pomodoroTest() {
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
     * Test of actionPerformed method, of class pomodoro.
     */
    

    @Test
    public void testIsTimerThreadAlive() {
        System.out.println("isTimerThreadAlive");
        pomodoro instance = new pomodoro();
        boolean expResult = false;
        boolean result = instance.isTimerThreadAlive();
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testGetCurrentTime() {
        JLabel timerLabel = new JLabel("25:00");
        pomodoro pomodoroInstance = new pomodoro();
        pomodoroInstance.timerLabel = timerLabel; // Set the timerLabel for the pomodoro instance
        String expectedTime = "25:00";
        String actualTime = pomodoroInstance.getCurrentTime();
        assertEquals(expectedTime, actualTime);
    }
    @Test
    public void testIsTimerThreadPaused() {
        System.out.println("isTimerThreadPaused");
        pomodoro instance = new pomodoro();
        boolean expResult = false;
        boolean result = instance.isTimerThreadPaused();
        assertEquals(expResult, result);
    
    }
    
}
