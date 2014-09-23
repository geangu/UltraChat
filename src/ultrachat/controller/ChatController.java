package ultrachat.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import ultrachat.Constants;
import ultrachat.thread.Receiver;
import ultrachat.thread.Sender;

public class ChatController {
    
    public static Socket CLIENT;
    public static String NICKNAME;

    public boolean start(String nickname, JTextArea txtMessages, int port){
        try {
            
            Constants.SERVER_PORT = port;
            NICKNAME = nickname;
            CLIENT = new Socket(Constants.SERVER_URL, Constants.SERVER_PORT);
            new Receiver(CLIENT, txtMessages).start();
            new Sender(nickname, CLIENT).start();
            DataOutputStream dos = new DataOutputStream(CLIENT.getOutputStream());
            dos.writeUTF(nickname);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void send(String text) {
        try {
            DataOutputStream out = new DataOutputStream(CLIENT.getOutputStream());
            out.writeUTF(NICKNAME + ": " + text);
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
}
