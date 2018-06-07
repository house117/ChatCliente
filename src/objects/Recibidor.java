/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author House
 */
public class Recibidor extends Thread{
    private Socket socket;
    private DataInputStream reader;
    private JTextArea txtChat;
    public Recibidor(Socket socket, JTextArea txtChat) throws IOException{
        this.socket = socket;
        this.txtChat = txtChat;
        reader = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while(true){
            try {
                
                String strFileContents = null;
                strFileContents = reader.readUTF();
                if(strFileContents != null){
                    txtChat.append(String.format("%s\n", strFileContents));
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Recibidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
