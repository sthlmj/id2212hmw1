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
        t = new CommunicationThread(connector);
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
                          
                          
		
		
		/*Socket socket;
		Connector connector;
		try {
			 socket = new Socket("localhost",1337);
			 connector = new Connector(socket);
			 CommunicationThread t = new CommunicationThread(connector);
			 t.start();
				
				
				
				/*Scanner sc = new Scanner(System.in);
				while(!t.isInterrupted()){
					String str=sc.nextLine();
					
					
					
					if(str.equals("-exit")){
						t.interrupt();//stops all threads
						System.out.println("Client shutdown down at timestamp: " + new Date().getTime());
						break;
					}
					else{
						connector.sendMsg(str);
					}
				}
				
		} catch (UnknownHostException e) {
			System.out.println("Host unreachable!"); //TODO redirect to GUI output
			
		} catch (IOException e) {		
		}*/
		
		
		
		//connect

	}

    @Override
    public void actionPerformed(ActionEvent e) {
       String actionCommand = e.getActionCommand();
       
       if(actionCommand.equals("button_connect")) {
          
           
           if(client.gui.isConnected()) { //disconnected
               disconnectServer();
               
               
               client.gui.setConnectionStatus(false);
               
           } 
           else {try {
               //connect to server
               createConnection(new Socket("localhost",1337));
               client.gui.setConnectionStatus(true);
                       } catch (IOException ex) {
                   
                           ex.printStackTrace();
                         
               }
           }
           
           
           
           
           
           System.out.println("Connect button");
       }
       if(actionCommand.equals("button_guess")){
           System.out.println("Guess button");
       }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            System.out.println("Pressed Enter");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
