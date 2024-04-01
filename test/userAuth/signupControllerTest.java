package userAuth;

import dataAccess.userFile;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class signupControllerTest {

    @Test
    public void testSaveUserInfo() {
        // Create an instance of signupController with a stub userFile
        signupController Controller = new signupController();

        // Define test data
        String userName = "testUser";
        String password = "testPassword";

        // Call the method to be tested
        Controller.saveUserInfo(userName, password);

        // Get the saved user info from the stub userFile
        String savedUserName = StubUserFile.getSavedUserName();
        String savedPassword = StubUserFile.getSavedPassword();

        // Assert that the saved user info matches the input
        assertEquals(userName, savedUserName);
        assertEquals(password, savedPassword);
    }
    
    // Define a stub userFile class for testing
    private static class StubUserFile extends userFile {
        private static String savedUserName;
        private static String savedPassword;

        @Override
        public void saveUserInfo(String userName, String password) {
            // Just store the values instead of saving to a file
            savedUserName = userName;
            savedPassword = password;
        }

        public static String getSavedUserName() {
            return savedUserName;
        }

        public static String getSavedPassword() {
            return savedPassword;
        }
    }
}
