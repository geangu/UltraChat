package ultrachat.controller;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import ultrachat.Constants;
import ultrachat.thread.ServerBroadcast;

public class ServerController extends Thread{

    JTextArea txt;
    
    public ServerController(int port, JTextArea txt) {
        this.txt = txt;
        Constants.SERVER_PORT = port;
    }
    
    @Override
    public void run(){
        try {
            ServerSocket server = new ServerSocket(Constants.SERVER_PORT);
            while (true) {
                Socket client = server.accept();
                String username = new DataInputStream(client.getInputStream()).readUTF();
                ServerBroadcast listener = new ServerBroadcast(client, txt);
                listener.start();
                listener.broadcast("Se ha conectado el usuario: " + username);
                Constants.CLIENTS.add(client);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
