import java.awt.*; 
import javax.swing.*;

class CustomSwing{

    public static JTextField getCustomTextField(String text){

        JTextField uriTextField = new JTextField(5); 

        uriTextField.setFont(new Font("Arial", Font.BOLD, 18)); 
        uriTextField.setForeground(new Color(117, 125, 138)); 

        return uriTextField; 
    }
}