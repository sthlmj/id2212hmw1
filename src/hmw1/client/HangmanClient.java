package hmw1.client;

import hmw1.tools.Connector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HangmanClient implements ActionListener, KeyListener{
    
private static HangmanClient client;    


private Gui gui;
private Socket socket;
private Connector connector;
private CommunicationThread t;        
        
        
    public HangmanClient(){
        gui = new Gui(this);
        
        
    }
    /**
     * Connect to server
     * @param socket 
     */
    public void createConnection(Socket socket) 
    {
        this.socket = socket;
        connector = new Connector(socket);
        t = new CommunicationThread(connector,gui);
        t.start();
    }
    
    /**
     * Disconnect from server
     * @param args 
     */
    public void disconnectServer()
    {
        t.interrupt();
    }
    
	public static void main(String[] args) {

		//TODO move testarrr
		//gui = new Gui(new HangmanClient());

                  client = new HangmanClient();
        }
    /**
     * If user pushes a button in the Gui
     * @param e 
     */    
    @Override
    public void actionPerformed(ActionEvent e) {
       String actionCommand = e.getActionCommand();
       
       if(actionCommand.equals("button_connect")) {//Connect button
         
           if(client.gui.isConnected()) { //Disconnect pressed (ending connection)
               disconnectServer();
               client.gui.setConnectionStatus(false);
           } 
           else {//Connect pressed (connecting to server)
               
                try {
                    String ip = client.gui.getGivenIP();
                    int newport = Integer.parseInt(client.gui.getPort());
                    createConnection(new Socket(ip,newport));
                    client.gui.setConnectionStatus(true);
                    
               } catch (IOException ex) {
                   client.gui.setTextOnConsole("Connection failed.. test a another ip or port");
                   client.gui.setConnectionStatus(false);
                           //ex.printStackTrace();
                         
               } catch (NumberFormatException ex){ //user didn't enter a integer as portnumbertried to enter other value th to enter 
                   client.gui.setTextOnConsole("Connection failed.. test a another ip or port");
                   client.gui.setConnectionStatus(false);
               }
               
           } 
       }
       //User wants to guess
       if(actionCommand.equals("button_guess")){
           
           if(client.gui.isConnected()) {
               String guess = client.gui.getGuess();
               if(guess != null && !guess.equals("")) {
                   connector.sendMsg(guess);
               }   
           }
           //System.out.println("Guess button");
       }
       
       if(actionCommand.equals("button_showMessage_ok")) {
           client.gui.exitShowMessage();
       }
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            if(client.gui.isConnected()) {//To a guess if connected
                String guess = client.gui.getGuess();
               if(guess != null && !guess.equals("")) {
                   connector.sendMsg(guess);
                   
               }   
           }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
