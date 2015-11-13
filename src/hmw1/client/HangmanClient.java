package hmw1.client;

import hmw1.tools.Connector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;

public class HangmanClient implements ActionListener, KeyListener{

    
private Gui gui;
private Socket socket;
private Connector connector;
private CommunicationThread t;        
    
    public HangmanClient(){
        gui = new Gui(this);  
    }
    
    public static void main(String[] args) {
        new HangmanClient();
        
    }    
    
   /**
    * Connect to server
    * @param socket
    * @throws IOException - throws exception if we cant establish connection
    */
    public void createConnection(Socket socket) throws IOException
    {
        this.socket = socket;
        connector = new Connector(socket);
        t = new CommunicationThread(connector,gui);
        t.start();
    }
    
    /**
     * Disconnect from server 
     */
    public void disconnectServer()
    {
        t.interrupt();//Stopp communication thread
    }
    
	
    /**
     * If user pushes a button in the Gui
     * @param e
     */    
    @Override
    public void actionPerformed(ActionEvent e) {
       String actionCommand = e.getActionCommand();
       
       if(actionCommand.equals("button_connect")) {//Connect button
         
           if(gui.isConnected()) { //Disconnect pressed (ending connection)
               disconnectServer();
               gui.setTextOnConsole(Gui.DEFAULT_CONSOLE_MESSAGE);//puts default console message
               gui.setConnectionStatus(false);
           } 
           else {//Connect pressed (connecting to server)
               
                try {
                    String ip = gui.getGivenIP();
                    int newport = Integer.parseInt(gui.getPort());
                    createConnection(new Socket(ip,newport));
                    gui.setConnectionStatus(true);
                    
               } catch (IOException ex) {
                   gui.showMessage("Connection failure","Connection failed.. test a another ip or port");
                   gui.setConnectionStatus(false);
                           //ex.printStackTrace();
                         
               } catch (NumberFormatException ex){ //user didn't enter a integer as portnumbertried to enter other value th to enter 
                   gui.showMessage("Connection failure","Connection failed.. test a another ip or port");
                   gui.setConnectionStatus(false);
               }
               
           } 
       }
       //User wants to guess
       if(actionCommand.equals("button_guess")){//Button pressed
           
           if(gui.isConnected()) {
               String guess = gui.getGuess();
               if(guess != null && !guess.equals("")) {
                   connector.sendMsg(guess);
               }   
           };
       }
       
       if(actionCommand.equals("button_showMessage_ok")) {
           gui.exitShowMessage();
       }
       
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     * If user pushes a key in the Gui
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){//Enter key pressed
            if(gui.isConnected()) {//Do a guess if connected
                String guess = gui.getGuess();
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
