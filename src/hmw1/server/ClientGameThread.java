package hmw1.server;

import hmw1.tools.Connector;
import hmw1.tools.Filehandler;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

public class ClientGameThread extends Thread {
	
	private Connector connection;
	private Filehandler  words;
	private long lastReceivedP = new Date().getTime();
	private int failedAttempts = 0;
        private int score = 0; // +=1 when user score and -=1 when server score 
	private String currentWord;
	private String hiddenWord = new String();
        private LinkedList<String> guesses; 
	
	public ClientGameThread(Filehandler filehandler, ThreadGroup tg,Socket socket) throws IOException{
		
		super(tg,"clientG");
		connection = new Connector(socket);
		words = filehandler;
                guesses = new LinkedList<String>();
	}
	
	
	/**
         * Generates a word that client shall guess on
         */
	private void generateNewWord(){
                
                guesses.clear(); //Empty guesses
		failedAttempts = 0;
		currentWord = words.getRandomWord();
		System.out.println("thread with id : " + this.getId() + " get word: " + currentWord);
		
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
        @Override
	public void run() {
		
		generateNewWord();
		
		//sends hidden word
		connection.sendMsg(informationMessage());
		
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
						connection.sendMsg(informationMessage());
						
					}
					else if(msg.length() == 1) { // A character guessed
						guesses.add(msg);
						if(currentWord.contains(msg.toUpperCase()) || currentWord.contains(msg.toLowerCase())) { // Hit on characther
							StringBuilder str = new StringBuilder();
							for(int i=0;i<currentWord.length(); i++){
								
								//If i character is matching at index position
								if(currentWord.substring(i,i+1).equalsIgnoreCase(msg.substring(0, 1))) {
									
									
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
                                                           
                                                                ++this.score;
                                                                generateNewWord();
								
								//sends hidden word
								connection.sendMsg(informationMessage());
							}
							else {// default presentation
								connection.sendMsg(informationMessage());
							}
							
						}
						else { // Wrong characther guess 
							if(++failedAttempts>currentWord.length()){
								connection.sendMsg("You loose, the correct word was" + currentWord );
								
								--this.score;//decrease score counter
								
								generateNewWord();
								
								//sends hidden word
								connection.sendMsg(informationMessage());
							} else{
								connection.sendMsg(informationMessage());
								
							}
							
						}
						
					}
					
					else { // A guees on full word has been done
						guesses.add(msg);
						if(currentWord.equalsIgnoreCase(msg)) { // victory
							connection.sendMsg("You win with " + failedAttempts + " number of fail attempts");
                                                        
                                                        ++this.score;//increase score counter
							generateNewWord();
							connection.sendMsg(informationMessage()); // annouce win and give new word
						}
						else {
                                                        if(++failedAttempts>currentWord.length()){//loose
								connection.sendMsg("You loose, the correct word was" + currentWord );
								
								--this.score;//decrease score counter
								
								generateNewWord();
								
								//sends hidden word
								connection.sendMsg(informationMessage());
							} else{
								connection.sendMsg(informationMessage());
								
							}
						}
						
						System.out.println("Client with id " + this.getId() + " says: " + msg);
					}
				}
				
				//Client is not alive anymore
				if((new Date().getTime() -  lastReceivedP) > 2000 ){
					super.interrupt();//ends threads execution
					
				}		

				
			} catch (IOException e) {
				//Ignore
			}
		}
		connection.closeConnection();
		super.interrupt();
		System.out.println("thread with id : " + this.getId() + " stopped");
	}
	
	
        /**
         * 
         * @return - A suitible string to display in client console
         */ 
	private String informationMessage(){
            StringBuilder g = new StringBuilder();
            for(String str : guesses){
                g.append(str + " ");
            }
            return "Word : " + hiddenWord + " (Length=" + hiddenWord.length()  + ")"+
                    ",Current Attempts: " + this.failedAttempts +
                    ",Score: " + this.score +
                    ",Guesses: " + g.toString();
        } 

}
