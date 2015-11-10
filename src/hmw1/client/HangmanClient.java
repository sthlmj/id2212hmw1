package hmw1.client;

import hmw1.tools.Connector;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;

public class HangmanClient {

	public static void main(String[] args) {

		//TODO move test
		new Gui();

	
		
		
		Socket socket;
		Connector connector;
		try {
			 socket = new Socket("localhost",1337);
			 connector = new Connector(socket);
			 CommunicationThread t = new CommunicationThread(connector);
			 t.start();
				
				
				
				Scanner sc = new Scanner(System.in);
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
		}
		
		
		
		//connect

	}
}
