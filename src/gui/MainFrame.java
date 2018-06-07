/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Listener.btnEnviarListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import objects.Recibidor;

/**
 *
 * @author House
 */
public class MainFrame extends JFrame{
    private PanelChat pnlPrincipal;
    private String nombreUsuario;
    private JPanel north;
    private JPanel south;
    private JPanel west;
    private JPanel east;
    private Socket socket;
    private DataOutputStream writer;
    private Recibidor recibidor;
    
    public MainFrame(String title) throws IOException{
        super(title);
        JTextField nickname = new JTextField(15);
        int resultMaterias;
        do{
            
            JPanel pnlNickname = new JPanel();

            pnlNickname.add(new JLabel("Nickname:"));
            pnlNickname.add(nickname);

             resultMaterias = JOptionPane.showConfirmDialog(null, pnlNickname, "Elige tu nombre :)",
                    JOptionPane.OK_CANCEL_OPTION); //Con esto tenemos un boton de ok/cancel, muy util
            if (resultMaterias == JOptionPane.OK_OPTION) {
                nombreUsuario = nickname.getText();
            }

        } while (nickname.getText().equals("") && resultMaterias != JOptionPane.CANCEL_OPTION
                && resultMaterias != JOptionPane.CLOSED_OPTION );
        if(resultMaterias == JOptionPane.CLOSED_OPTION){
            super.setVisible(false);
        }else{
            super.setLayout(new BorderLayout());
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(500, 500);
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        //Declaracion del socket y el output
        socket = new Socket("localhost", 1234);
        System.out.println("SE CONECTO!!!");
        writer = new DataOutputStream(socket.getOutputStream());
        
        //DEFINIMOS EL NOMBRE DEL USUARIO POR AHORA MANUALITO :(
        
        pnlPrincipal = new PanelChat();
        recibidor = new Recibidor(socket, pnlPrincipal.getTxtShowChat());
        //pnlPrincipal.setBackground(Color.yellow);
        pnlPrincipal.setListener(new btnEnviarListener() {
            @Override
            public void onClick(String text) {
                try {
                    Date date = new GregorianCalendar().getTime();
                    writer.writeUTF(String.format("%s (at %d:%d:%d): %s\n", nombreUsuario,
                            date.getHours(),
                            date.getMinutes(),
                            date.getSeconds(), text));
                    //pnlPrincipal.getTxtShowChat().append();
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        recibidor.start();
        
        north = new JPanel();
        north.setBackground(Color.red);
        south = new JPanel();
        south.setBackground(Color.black);
        west = new JPanel();
        west.setBackground(Color.blue);
        east = new JPanel();
        east.setBackground(Color.gray);
        
        super.add(pnlPrincipal, BorderLayout.CENTER);
        super.add(north,BorderLayout.NORTH);
        super.add(south, BorderLayout.SOUTH);
        super.add(west, BorderLayout.WEST);
        super.add(east, BorderLayout.EAST);
        super.setVisible(true);
        }
        
    }
}
