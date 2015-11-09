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

public class Gui {

	private JFrame root_window;
	private JPanel window_top;
	private JPanel window_midd;
	private JPanel window_down;
	
	//private Menu connection
	private Button button_connect;
	private Button button_guess;
	private TextArea output;
	private TextField text_ipAdress;
	private TextField text_portField;
	private TextField text_guees;
	private GridBagConstraints constraints;
	 
	
	public Gui() {
		
		//Init frames
		root_window = new JFrame("aa");
		window_top = new JPanel();
		window_midd = new JPanel();
		window_down = new JPanel();
		
		//Set layout in each
		root_window.setLayout(new GridBagLayout());
		
		//Create constraints 
		constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
 
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
		//window_top.setLayout(new FlowLayout(FlowLayout.LEFT));
		//window_midd.setLayout(new FlowLayout(FlowLayout.CENTER));
		//window_midd.setLayout(new FlowLayout());
		
		//Init variables		
		text_ipAdress = new TextField("localhost",20);
		text_portField = new TextField("1337",4);
		button_connect = new Button("Connect");
		output = new TextArea("test",10,10);
		text_guees = new TextField(10);
		button_guess = new Button("Guess");
		root_window.setSize(500, 500);
		
		//Adding components at top
		window_top.add(text_ipAdress,constraints);
		window_top.add(text_portField,constraints);
		window_top.add(button_connect,constraints);
		
		//Adding components at midd
		window_midd.add(output,constraints);
		
		//Adding components at buttom
		window_down.add(text_guees,constraints);
		window_down.add(button_guess,constraints);
		
		root_window.add(window_top,constraints);
		root_window.add(window_midd,constraints);
		root_window.add(window_down,constraints);
		
		

		root_window.setVisible(true);
		
	}

}
