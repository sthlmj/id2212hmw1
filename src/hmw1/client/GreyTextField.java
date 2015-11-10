/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmw1.client;

/**
 *
 * @author joehulden
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
public class GreyTextField {
 
   private static void createAndShowUI() {
      JTextField tField1 = new JTextField("foo", 10);
      JTextField tField2 = new JTextField("bar", 10);
      tField1.setEnabled(false);
       
      JPanel panel = new JPanel();
      panel.add(tField1);
      panel.add(tField2);
       
      JFrame frame = new JFrame("GreyTextField");
      frame.getContentPane().add(panel);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }
 
   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            createAndShowUI();
         }
      });
   }
}