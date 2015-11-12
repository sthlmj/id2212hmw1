package hmw1.client;

import hmw1.tools.Connector;

import java.io.IOException;
import java.util.Date;

/**
 * This class has the responibility to listen to server messages and forward them to Gui
 * @author guuurris
 *
 */
public class CommunicationThread extends Thread{

	private Connector connection;
	private Gui gui;
	private long lastReceivedP = new Date().getTime();
	public CommunicationThread(Connector c,Gui g) {
		this.connection = c;
		this.gui = g;
	}
	
	@Override
	public void run() {
		
		while(!isInterrupted() ){
			try {//Listen for messages from server
				
				if(connection.hasMsg()) {
					
					String newMsg = connection.readMsg();
					
					//server is up and running
					if(newMsg.contains("*pong*")) {
						lastReceivedP = new Date().getTime();
					}
					else{
                                                lastReceivedP = new Date().getTime();
						
                                                if(newMsg.contains("You win") || newMsg.contains("You loose") ){
                                                    
                                                    gui.setTextOnConsole(newMsg);
                                                    gui.showMessage("Game information",newMsg);
                                                }
                                                else{
                                                    gui.setTextOnConsole(newMsg);
                                                }
                                        }			
				}
				
				Thread.sleep(200);//sleep 200ms before pinging server
				connection.sendMsg("*ping*");
				
				// if server timesout
				if((new Date().getTime() -  lastReceivedP) > 2000 ){
					System.out.println("Timedout with " + (new Date().getTime() -  lastReceivedP) + " ms");
					super.interrupt();//ends threads execution

				}
				
				
			} catch (IOException e) {
				//e.printStackTrace();
				
			} catch (InterruptedException e) {//Sleep in thread has been interrupted
				super.interrupt();
			}
		}
		connection.closeConnection();
	}
}
