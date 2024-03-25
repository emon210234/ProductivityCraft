package userAuth;
import dataAccess.userFile;
import javax.swing.JOptionPane;
import java.io.*;
public class loginController {
    private final userFile userFileManager;
    public loginController(){
        userFileManager = new userFile();
    }
    
    public boolean isLoginSuccessful(String enteredUsername, String enteredPassword) {
        return userFileManager.isLoginSuccessful(enteredUsername, enteredPassword);
    }
}
