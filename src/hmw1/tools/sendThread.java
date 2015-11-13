/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hmw1.tools;

/**
 *
 * @author Gustav
 */
public class sendThread extends Thread{
    
    private Connector connector;
    private String message;
    public sendThread(Connector c,String message){
        this.connector = c;
        this.message = message;
    }

    @Override
    public void run() {
        connector.sendMsg(message);
    }
    
    
    
}
