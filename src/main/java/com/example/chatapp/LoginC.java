//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.chatapp;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginC {
    @FXML
    private TextField usernameID;
    @FXML
    private PasswordField passwordID;

    public LoginC() {
    }

    @FXML
    protected void onLogin() throws IOException {
        if (this.usernameID.getText().equals("admin") && this.passwordID.getText().equals("admin")) {
            Stage s = (Stage)this.usernameID.getScene().getWindow();
            FXMLLoader fxl = new FXMLLoader(MainApp.class.getResource("AfterLogin.fxml"));
            Scene s2 = new Scene(fxl.load());
            s.setScene(s2);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Authentification Failed");
            alert.setHeaderText("Username or Password incorrect");
            alert.setContentText("If u forgot ur psd try forgot password !");
            alert.show();
        }

    }
}
