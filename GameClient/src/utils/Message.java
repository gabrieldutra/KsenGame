package utils;

import javax.swing.JOptionPane;

/**
 *
 * @author strudel
 */
public class Message {
    public static String input(String msg){
        return JOptionPane.showInputDialog(msg);
    }
    
    public static void error(String title, String msg){
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void info(String title, String msg){
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
