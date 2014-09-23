
package ultrachat.thread;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sender extends Thread {

    String nickname;
    Socket client;
    
    public Sender(String nickname, Socket client) {
        this.nickname = nickname;
        this.client = client;
    }

    @Override
    public void run() {
        while(true){
            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                InputStreamReader isr= new InputStreamReader(System.in);
                BufferedReader in = new BufferedReader(isr);
                String message = in.readLine(); 
                out.writeUTF(nickname + ": " + message);
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
