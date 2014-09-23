package ultrachat.thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import ultrachat.Constants;

public class ServerBroadcast extends Thread {
    
    Socket client;
    DataInputStream in;
    JTextArea txt;
    
    public ServerBroadcast(Socket client, JTextArea txt) {
        try {
            this.client = client;
            this.txt = txt;
            this.in = new DataInputStream(client.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = in.readUTF();
                broadcast(message); 
            } catch (IOException ex) {
                Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }
        }
    }

    public void broadcast(String message) {
        txt.setText(txt.getText() + "\n" + message);
        Iterator<Socket> iter = Constants.CLIENTS.iterator();
        while (iter.hasNext()) {
            Socket cli = iter.next();
            try {
                OutputStream os = cli.getOutputStream();
                DataOutputStream out = new DataOutputStream(os);
                out.writeUTF(message);
            } catch (IOException ex) {
                Logger.getLogger(ServerBroadcast.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
  
}
