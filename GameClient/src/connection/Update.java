/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connection;

import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class Update implements Runnable {

    @Override
    public void run() {
        while(true){
            try {
                 Client.requestAll();
                 Thread.sleep(50);
             } catch (IOException | InterruptedException  ex) {
                 //Logger.getLogger(Play.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        }
    }
    
}
