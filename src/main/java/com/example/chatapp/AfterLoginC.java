//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.chatapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class AfterLoginC {
    @FXML
    TextField Port;
    @FXML
    TextField Host;
    @FXML
    TextField Message;
    @FXML
    ListView<String> listview;
    @FXML
    TextField Username;
    PrintWriter pw;

    public AfterLoginC() {
    }

    @FXML
    public void onConnect(ActionEvent actionEvent) throws IOException {
        String host = Host.getText();
        int port = Integer.parseInt(Port.getText());
        Socket s = new Socket(host,port);

        InputStream is = s.getInputStream();
        InputStreamReader isr= new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        OutputStream os = s.getOutputStream();
        pw= new PrintWriter(os,true);
        String Ip = s.getRemoteSocketAddress().toString();

        new Thread(()-> {
            while(true){
                try {
                    String Response = br.readLine();
                    Platform.runLater(() -> {
                        listview.getItems().add(Response);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onSubmit(ActionEvent actionEvent) {
        String message = Message.getText();
        String username = Username.getText();
        pw.println(username + " : " +message);
    }
}
