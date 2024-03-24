package userAuth;

import java.io.*;
public class signupController {
    public void saveUserInfo(String userName, String password) {
        try {
            // Open file for writing
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
            // Write username and password to file
            writer.write(userName + ":" + password);
            writer.newLine();
            // Close the writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
