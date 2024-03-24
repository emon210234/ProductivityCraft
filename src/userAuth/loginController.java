package userAuth;

import javax.swing.JOptionPane;
import java.io.*;
public class loginController {
    public boolean isLoginSuccessful(String enteredUsername, String enteredPassword) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            boolean userFound = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2) {
                    String usernameFromFile = parts[0];
                    String passwordFromFile = parts[1];
                    if (usernameFromFile.equals(enteredUsername) && passwordFromFile.equals(enteredPassword)) {
                        userFound = true;
                        break;
                    }
                }
            }
            return userFound;
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while reading user information.");
            return false;
        }
    }
}
