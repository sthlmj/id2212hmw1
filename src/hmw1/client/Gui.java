package hmw1.client;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import java.awt.*;
import java.util.StringTokenizer;

public class Gui extends JPanel {

private JFrame frame;
private JButton button_connect;
private JButton button_guess;
private JTextArea output;
private JTextField text_ipAddress;
private JTextField text_port;
private JTextField text_guess;
private GridBagConstraints constraints;
private HangmanClient parentclass;
private boolean isConnected = false;
public static final String DEFAULT_CONSOLE_MESSAGE = "Connect to a server to play";
private JDialog showMessage;


/**
 * Constructor for the Gui of client, a new instance of this is enough to display the full game
 * @param hangmanclient - the owner(parent) of this Gui
 */        
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
    output = new JTextArea(DEFAULT_CONSOLE_MESSAGE,5,5);
    
    output.setLineWrap(true);
    output.setWrapStyleWord(true);
    output.setEditable(false);
    output.setFont(new Font(Font.SANS_SERIF, 3, 10));


    button_guess = new JButton("Guess");
    text_guess = new JTextField("",10);
    text_guess.setEditable(false);

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

/**
 * Creates and shows the main window(frame) that this game uses 
 */
private void createAndShowFrame() {
    frame = new JFrame("Hangman Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.add(this);
    
    ImageIcon img = new ImageIcon("src/hmw1/Hangman-icon.png");
    frame.setIconImage(img.getImage());
    
    //frame.setJMenuBar(menubar_top);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}


/**
 * Sets text to be displayed in console, with automatic new line for delimiter ","
 * @param text - to be added on console
 */
public void setTextOnConsole(String text){
    output.setText("");
    StringTokenizer token = new StringTokenizer(text, ",");
    
    while(token.hasMoreTokens()){
        output.append(token.nextToken() + "\n");
    }
    frame.pack();
}

/**
 * Change the button_connect displayed content and others fields editability
 * @param status - true if connected to a server else false
 */
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

/**
 * A popup message dialog that is a non-blocking function (used instead of a JDialog for instance)
 * @param title - frame title
 * @param message - message to show user
 */
public void showMessage(String message, String text) {
    showMessage = new JDialog(frame,message);
    JButton b = new JButton("Ok");
    b.setActionCommand("button_showMessage_ok");
    b.addActionListener(parentclass);
    showMessage.setLayout(new FlowLayout(HEIGHT));
    showMessage.add(new JTextArea(text));
    showMessage.add(b);
    showMessage.pack();
    showMessage.setLocationRelativeTo(frame);
    showMessage.setVisible(true);
}

/**
 * Dispose of the message box (i.e. closes it)
 */
public void exitShowMessage() {
    if(showMessage != null) {
        showMessage.dispose();
    }
}

/**
 * Getter for text_ipAddress value
 * @return 
 */
public String getGivenIP(){
    return this.text_ipAddress.getText();
} 
/**
 * Getter for text_port value
 * @return 
 */
public String getPort(){
    return this.text_port.getText();
}

/**
 * Getter for text_guess value
 * returns guess value and resets parameter
 * */
public String getGuess(){
    String out = this.text_guess.getText();
    this.text_guess.setText("");//resets value
    return out;
}



/**
 * Tells if client is connected to server (getter)
 * @return true if connected to server, false otherwise
 */
public boolean isConnected() {
    return isConnected;
}

}



