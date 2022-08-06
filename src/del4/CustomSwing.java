package del4;
import java.awt.*; 
import javax.swing.*;

public class CustomSwing{

    public static JTextField getCustomTextField(String text){

        JTextField tf = new JTextField(5); 
        
        tf.setText(text);

        tf.setFont(new Font("Arial", Font.BOLD, 18)); 
        tf.setForeground(new Color(117, 125, 138)); 

        return tf; 
    }
    
    public static JLabel getCustomlabel(String text, int fontSize, int alignment, Color color){

    	JLabel label = new JLabel(text, alignment); 
		label.setFont(new Font("Arial", Font.BOLD, fontSize)); 
		label.setForeground(color);
		
		return label;  
    }
    
}