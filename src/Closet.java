
import java.awt.*; 
import javax.swing.*;


public class Closet {

    final static String WINDOW_TITLE_LOGIN="Closet Database"; 
    final static String LABEL_HEADER = "Enter Database Details"; 
    final static String BUTTON_LOGIN = "Login"; 
    final static String IMG_LOGIN_PATH = "img/closet.jpg"; 

    private static JTextField uriTextField; 
    private static JTextField usernameTextField; 
    private static JTextField passwordTextField; 
    private static JTextField driverTextField;

    public static void main(String args[]){
        createLogInWindow(); 
    }

    /**
     * creates and displays the initial owner log in window
     */
    private static void createLogInWindow(){

        // initialize frame
        JFrame frame = new JFrame(WINDOW_TITLE_LOGIN); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // header label
        JLabel headerLabel = new JLabel(LABEL_HEADER, SwingConstants.CENTER); 
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20)); 
        headerLabel.setForeground(Color.WHITE); 
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));

        // textfield
        uriTextField = CustomSwing.getCustomTextField("uri");
        // username textfield
        usernameTextField =  CustomSwing.getCustomTextField("username");
        // textfield
        passwordTextField =  CustomSwing.getCustomTextField("password");

        // textfield
        driverTextField = CustomSwing.getCustomTextField("driver");
        // panel 
        JImagePanel panel = new JImagePanel(IMG_LOGIN_PATH); 
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        // button 
        JButton confirmButton = new JButton(BUTTON_LOGIN); 
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setForeground(new Color(117, 125, 138)); 
        confirmButton.addActionListener(e -> handleLoginButtonPressed());

        int top = 10; 
        int left = 70; 
        int bottom = 25; 
        int right = 70; 
        panel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));

        frame.add(panel); 
        panel.add(headerLabel); 
        panel.add(uriTextField); 
        panel.add(usernameTextField); 
        panel.add(passwordTextField); 
        panel.add(driverTextField); 
        panel.add(confirmButton); 



        panel.setSize(500, 500); 

        frame.setLocationRelativeTo(null); 
        frame.setResizable(false);

        frame.pack(); 

        frame.setVisible(true); 
    }

    
    public static void handleLoginButtonPressed(){

        String uri =  uriTextField.getText(); 
        String username = usernameTextField.getText(); 
        String password = passwordTextField.getText();
        String driver_cname = driverTextField.getText(); 

        Database.getConnection(uri, username, password, driver_cname); 
    }

}
