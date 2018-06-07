/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatcliente;
import gui.MainFrame;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author House
 */
public class ChatCliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        grafico();
    }
    public static void grafico(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainFrame ventanaPrincipal = new MainFrame("Latin Chat");
                } catch (IOException ex) {
                    Logger.getLogger(ChatCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
}
