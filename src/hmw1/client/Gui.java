package hmw1.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;

import javax.swing.*;

import java.awt.*;

public class Gui extends JPanel {

private JFrame frame;
private JPanel window_top;
private JPanel window_midd;
private JPanel window_down;

//private Menu connection
private JButton button_connect;
private JButton button_guess;
private JTextArea output;
private JTextField text_ipAddress;
private JTextField text_port;
private JTextField text_guess;
private GridBagConstraints constraints;
private HangmanClient parentclass;
private boolean isConnected = false;

public boolean isConnected() {
    return isConnected;
}
    
    
        
public Gui(HangmanClient hangmanclient) {

    super(new GridBagLayout());
    parentclass = hangmanclient;
    //Make constraints rules
    constraints = new GridBagConstraints();
    constraints.gridwidth = GridBagConstraints.REMAINDER;

    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weightx = 1.0;
    constraints.weighty = 1.0;

    //Init visual fields and set default behavior 		
    text_ipAddress = new JTextField("localhost",20);
    text_port = new JTextField("1337",4);
    button_connect = new JButton("Connect");
    output = new JTextArea("",10,10);
    output.setEditable(false);
    output.setFont(new Font(Font.SANS_SERIF, 3, 20));



    text_guess = new JTextField("",10);
    button_guess = new JButton("Guess");

    //Adds possibility to change host and ip adress
    add(button_connect);
    add(text_ipAddress);
    add(text_port,constraints);

    //Adds console output 
    add(new JLabel("Console: "));
    add(output,constraints);

    add(button_guess);
    add(text_guess,constraints);


    //Adds listener
     button_connect.addActionListener(parentclass);
     button_connect.setActionCommand("button_connect");

     button_guess.setActionCommand("button_guess");
     button_guess.addActionListener(parentclass);

     text_guess.addKeyListener(parentclass);


    createAndShowFrame();
}
	
public void setConnectionStatus(boolean status){

    isConnected = status;
    if(isConnected == true) {
        button_connect.setText("Disconnect");
    }
    else{ 
        button_connect.setText("Connect  ");
        text_guess.setText("");
    }

    text_ipAddress.setEditable(!status);
    text_port.setEditable(!status);
    text_guess.setEditable(status);
    frame.pack();
}
        
public String getGivenIP(){
    return this.text_ipAddress.getText();
} 

public String getPort(){
    return this.text_port.getText();
}

//returns guess value and resets parameter
public String getGuess(){
    String out = this.text_guess.getText();
    this.text_guess.setText("");//resets value
    return out;
}

public void setTextOnConsole(String text){
    output.setText(text);
}


private void createAndShowFrame() {
    frame = new JFrame("Hangman Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.add(this);
    //frame.setIconImage(icon);
    //frame.setJMenuBar(menubar_top);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}

}



