package hmw1.server;

import hmw1.tools.Connector;
import hmw1.tools.Filehandler;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ClientGameThread extends Thread {
	
	private boolean keepThreadAlive ;
	private Connector connection;
	private Filehandler  words;
	private long lastReceivedP = new Date().getTime();
	private int failedAttempts = 0;
	private String currentWord;
	private String hiddenWord = new String();
	private int serverScore = 0;
	private int clientScore = 0;
	
	public ClientGameThread(Filehandler filehandler, ThreadGroup tg,Socket socket) {
		
		super(tg,"clientG");
		keepThreadAlive = true;
		connection = new Connector(socket);
		words = filehandler;
	}
	
	
	
	private void generateNewWord(){
		failedAttempts = 0;
		currentWord = words.getRandomWord();
		System.out.println("thread with id : " + this.getId() + " started" + " get word: " + currentWord);
		
		/*** Hide characters in word***/
		StringBuilder str = new StringBuilder();
		for(int i=0;i<currentWord.length(); i++){
			str.append("-");
		}
		hiddenWord = str.toString();
		/***                         ***/
	}
	
	/**
	 * @Override Threads method
	 */
	public void run() {
		
		generateNewWord();
		
		//sends hidden word
		connection.sendMsg(hiddenWord);
		
		while(!isInterrupted()){
			
			try {
				
				if(connection.hasMsg()){
					String msg = connection.readMsg();
					
					if(msg.contains("*ping*")){
						lastReceivedP = new Date().getTime();
						connection.sendMsg("*pong*");
						
					}
					else if(msg.contains("start game")) { // Starts new game
						failedAttempts = 0;
						generateNewWord();
						connection.sendMsg(hiddenWord);
						
					}
					else if(msg.length() == 1) { // A character guessed
						
						if(currentWord.contains(msg.toUpperCase()) || currentWord.contains(msg.toLowerCase())) { // Hit on characther
							StringBuilder str = new StringBuilder();
							for(int i=0;i<currentWord.length(); i++){
								
								//If i character is matching at index position
								if(currentWord.substring(i,i+1).equalsIgnoreCase(msg.substring(0, 1))) {
									
									System.out.println("match");
									str.append(msg.substring(0,1).toLowerCase());
								} else {
									if(hiddenWord.charAt(i) != '-'){
										str.append(hiddenWord.charAt(i));
									}else { 
										str.append("-");
									}
											
		
								}	
							}
							hiddenWord = str.toString();
							if(!hiddenWord.contains("-")) {
								connection.sendMsg("You win with " + failedAttempts + " number of fail attempts");
								generateNewWord();
								
								//sends hidden word
								connection.sendMsg(hiddenWord);
							}
							else {// default presentation
								connection.sendMsg(hiddenWord + " " + failedAttempts);
							}
							
						}
						else { // Wrong characther guess 
							if(++failedAttempts>currentWord.length()){
								connection.sendMsg("You loose, the correct word was" + currentWord );
								
								connection.sendMsg("Score : Server= " + ++serverScore  + "You= " + ++clientScore );
								
								generateNewWord();
								
								//sends hidden word
								connection.sendMsg(hiddenWord);
							} else{
								connection.sendMsg(hiddenWord + " " + failedAttempts);
								
							}
							
						}
						
					}
					
					else { // A guees on full word has been done
						
						if(currentWord.equalsIgnoreCase(msg)) { // victory
							connection.sendMsg("You win with " + failedAttempts + " number of fail attempts");
							generateNewWord();
							connection.sendMsg(hiddenWord); // annouce win and give new word
						}
						else {
							connection.sendMsg(hiddenWord + " " + ++failedAttempts);
							
						}
						
						System.out.println("Client with id " + this.getId() + " says: " + msg);
					}
				}
				
				//Client is not alive anymore
				if((new Date().getTime() -  lastReceivedP) > 2000 ){
					super.interrupt();//ends threads execution
					
				}		

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		connection.closeConnection();
		super.interrupt();
		System.out.println("thread with id : " + this.getId() + " stopped");
	}
	
	
	 

}
