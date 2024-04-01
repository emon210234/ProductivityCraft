//to check if the button functionalities work properly
package coreFunctions;

import java.awt.event.ActionEvent;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
      public void testHandleStartButtonAction() {
        System.out.println("handleStartButtonAction");
        pomodoro instance = new pomodoro();
        instance.handleStartButtonAction();
    }


    @Test
    public void testHandlePauseButtonAction() {
        System.out.println("handlePauseButtonAction");
        pomodoro instance = new pomodoro();
        instance.handlePauseButtonAction();
    }

    @Test
    public void testHandleResetButtonAction() {
        System.out.println("handleResetButtonAction");
        pomodoro instance = new pomodoro();
        instance.handleResetButtonAction();
      
  
    }

    @Test
    public void testIsTimerThreadAlive() {
        System.out.println("isTimerThreadAlive");
        pomodoro instance = new pomodoro();
        boolean expResult = false;
        boolean result = instance.isTimerThreadAlive();
        assertEquals(expResult, result);
        
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
