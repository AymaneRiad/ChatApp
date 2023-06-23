package com.example.chatapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatWithServer extends Thread{
    private int nbrClient=0;
    private List<Communication> ClientConn = new ArrayList<Communication>();
    public static void main(String[] args) {
        new ChatWithServer().start();

    }
    @Override
    public void run(){
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Le serveur essai de demarrer please wait ...");
            while(true){
                Socket s = ss.accept();
                ++nbrClient;
                Communication NewCommunication = new  Communication(s, nbrClient);
                ClientConn.add(NewCommunication);
                NewCommunication.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public class Communication extends Thread{
        private int numClient;
        private Socket s;
        Communication (Socket s, int numClient){
            this.s = s;
            this.numClient = numClient;
        }
        @Override
        public void run(){
            try {
                InputStream is = s.getInputStream(); //octet
                InputStreamReader isr = new InputStreamReader(is); //caractere
                BufferedReader br = new BufferedReader(isr); //chaine de caractere

                OutputStream os = s.getOutputStream(); //octet
                PrintWriter pw = new PrintWriter(os, true);

                String Ip = s.getRemoteSocketAddress().toString();
                System.out.println("Vous etes le client numero : "+numClient+" et son Ip est : "+ Ip);//message du serveur

                pw.println("Vous etes le client num "+numClient);//message por le serveur
                pw.println("Envoyer le message que vous voulez ...");

                while(true) {
                    String userRequest = br.readLine();
                    if(userRequest.contains("=>")){
                        String[] usermessage = userRequest.split("=>");
                        if(usermessage.length == 2){
                            String msg = usermessage[1];
                            int numeClient = Integer.parseInt(usermessage[0]) ;
                            Send(msg, s, numeClient);
                        }
                    }
                    else {
                        Send(userRequest, s, -1);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        void Send(String userRequest, Socket socket, int nbre) throws IOException {
            for (Communication clt : ClientConn){
                if (clt.s != socket) {
                    if(clt.numClient == nbre || clt.numClient == -1){
                        PrintWriter pw = new PrintWriter(clt.s.getOutputStream(), true);
                        pw.println(userRequest);
                    }
                }
            }

        }
    }
}