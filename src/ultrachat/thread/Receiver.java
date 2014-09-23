package ultrachat.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

public class Receiver extends Thread {

    Socket client;
    JTextArea txtMessages;

    public Receiver(Socket client, JTextArea txtMessages) {
        this.client = client;
        this.txtMessages = txtMessages;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DataInputStream in = new DataInputStream(client.getInputStream());
                String message = in.readUTF();
                txtMessages.setText(txtMessages.getText() + "\n" + message);
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
