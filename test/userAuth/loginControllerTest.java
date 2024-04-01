package userAuth;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class loginControllerTest {
    
    public loginControllerTest() {
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
     * Test of isLoginSuccessful method, of class loginController.
     */
    @Test
    public void testIsLoginSuccessful() {
        System.out.println("isLoginSuccessful");
        String enteredUsername = "";
        String enteredPassword = "";
        loginController instance = new loginController();
        boolean expResult = false;
        boolean result = instance.isLoginSuccessful(enteredUsername, enteredPassword);
        assertEquals(expResult, result);
    }
    
}
