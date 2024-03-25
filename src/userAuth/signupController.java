package userAuth;

import dataAccess.userFile;
import java.io.*;
public class signupController {
    private final userFile fileHandler;
    
    public signupController(){
        fileHandler = new userFile();
    }
    
    public void saveUserInfo(String userName, String password) {
        fileHandler.saveUserInfo(userName, password);
    }
}
