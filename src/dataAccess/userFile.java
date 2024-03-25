/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dataAccess;

import javax.swing.JOptionPane;
import java.io.*;
public class userFile {
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
    
    public void saveUserInfo(String userName, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            // Write username and password to file
            writer.write(userName + ":" + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
