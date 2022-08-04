
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;


public class Closet {

	final static String WINDOW_TITLE_LOGIN="Closet"; 
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
		JLabel headerLabel = CustomSwing.getCustomlabel(LABEL_HEADER, 20, SwingConstants.CENTER, Color.WHITE); 
		headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));

		// textfield
		uriTextField = CustomSwing.getCustomTextField("uri");
		JLabel uriLabel = new JLabel("URI");
		uriTextField.setText("jdbc:mysql://localhost:3306/closet");
		// username textfield
		usernameTextField =  CustomSwing.getCustomTextField("username");
		JLabel usernameLabel = new JLabel("Username");
		usernameTextField.setText("root");
		// textfield
		passwordTextField =  CustomSwing.getCustomTextField("password");
		JLabel passwordLabel = new JLabel("Password");

		// textfield
		driverTextField = CustomSwing.getCustomTextField("driver");
		JLabel driverLabel = new JLabel("Driver");
		driverTextField.setText("com.mysql.cj.jdbc.Driver");
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
		panel.add(uriLabel);
		panel.add(uriTextField); 
		panel.add(usernameLabel);
		panel.add(usernameTextField); 
		panel.add(passwordLabel);
		panel.add(passwordTextField); 
		panel.add(driverLabel);
		panel.add(driverTextField); 
		panel.add(confirmButton); 



		panel.setSize(500, 500); 

		frame.setLocationRelativeTo(null); 
		frame.setResizable(false);

		frame.pack(); 

		frame.setVisible(true); 
	}


	public static void handleLoginButtonPressed(){

		// establish connection using a database object 
		String uri =  uriTextField.getText(); 
		String username = usernameTextField.getText(); 
		String password = passwordTextField.getText();
		String driver_cname = driverTextField.getText();  

		Database database = new Database(uri, username, password, driver_cname); 
		promptType(database); 
	}

	public static void handleSearchButtonPressed(){

		// establish connection using a database object 
		String uri =  uriTextField.getText(); 
		String username = usernameTextField.getText(); 
		String password = passwordTextField.getText();
		String driver_cname = driverTextField.getText();  

		Database database = new Database(uri, username, password, driver_cname); 
		searchSelected(database); 
	}

	public static void handleInsertButtonPressed(){

		// establish connection using a database object 
		String uri =  uriTextField.getText(); 
		String username = usernameTextField.getText(); 
		String password = passwordTextField.getText();
		String driver_cname = driverTextField.getText();  

		Database database = new Database(uri, username, password, driver_cname); 
		insertSelected(database); 
	}

	public static void handleUpdateButtonPressed(){

		// establish connection using a database object 
		String uri =  uriTextField.getText(); 
		String username = usernameTextField.getText(); 
		String password = passwordTextField.getText();
		String driver_cname = driverTextField.getText();  

		Database database = new Database(uri, username, password, driver_cname); 
		updateSelected(database); 
	}

	public static void handleDeleteButtonPressed(){

		// establish connection using a database object 
		String uri =  uriTextField.getText(); 
		String username = usernameTextField.getText(); 
		String password = passwordTextField.getText();
		String driver_cname = driverTextField.getText();  

		Database database = new Database(uri, username, password, driver_cname); 
		deleteSelected(database); 
	}


	public static void createAllClothingFrame(Database database) {

		// initialize frame
		JFrame frame = new JFrame("all clothes or something"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		try {
			int[] specialColumns = {1, 2}; 
			frame.add(new JListPanel(database.getShirtsByOwnerAndBrand(), specialColumns));

			database.closeConnection();  

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		frame.pack(); 

		frame.setVisible(true); 


	}

	public static void promptType(Database database) {

		// initialize frame
		JFrame frame = new JFrame("Database options"); 
		JPanel p = new JPanel();
		JPanel buttons = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		JLabel prompt = new JLabel("Prompt");
		prompt.setText("Would you like to search, insert, update, delete?");
		p.add(prompt);

		JButton searchButton = new JButton("Search"); 
		searchButton.setFont(new Font("Arial", Font.BOLD, 14));
		searchButton.setForeground(new Color(117, 125, 138)); 
		searchButton.addActionListener(e -> handleSearchButtonPressed());

		JButton insertButton = new JButton("Insert"); 
		insertButton.setFont(new Font("Arial", Font.BOLD, 14));
		insertButton.setForeground(new Color(117, 125, 138)); 
		insertButton.addActionListener(e -> handleInsertButtonPressed());

		JButton updateButton = new JButton("Update"); 
		updateButton.setFont(new Font("Arial", Font.BOLD, 14));
		updateButton.setForeground(new Color(117, 125, 138)); 
		updateButton.addActionListener(e -> handleUpdateButtonPressed());

		JButton deleteButton = new JButton("Delete"); 
		deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
		deleteButton.setForeground(new Color(117, 125, 138)); 
		deleteButton.addActionListener(e -> handleDeleteButtonPressed());

		buttons.add(searchButton);
		buttons.add(insertButton);
		buttons.add(updateButton);
		buttons.add(deleteButton);
		frame.add(p, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.setSize(new Dimension(320,64));
		database.closeConnection(); 
		frame.pack(); 

		frame.setVisible(true); 


	}

	public static void searchSelected(Database database) {

		// initialize frame
		JFrame frame = new JFrame("Search Options"); 
		JPanel p = new JPanel();
		JPanel buttons = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		JLabel prompt = new JLabel("Prompt");
		prompt.setText("<html>Would you like to search?<br/>"
				+ "1. Select All Shirts by Owner Brand<br/>"
				+ "2. Select All Shirts<br/>"
				+ "3. Select All Pants<br/>"
				+ "4. Select All Outerwear<br/>"
				+ "5. Select All Clothing by Worn<br/>"
				+ "6. Select Item by Location<br/>"
				+ "7. Select All Owned Clothes<br/>"
				+ "8. Select All Clothes not Owned<br/>"
				+ "9. Select Brand by Clothing ID<br/>"
				+ "10. Select Clothes by Clothing ID<br/>"
				+ "11. Select Clothes by Owner ID<html>");
		JTextField selection = new JTextField("Selection");
		JLabel selectLabel = new JLabel("Please type in selection");

		p.add(prompt);
		p.add(selectLabel);
		p.add(selection);


		frame.add(p, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.setSize(new Dimension(320,64));
		database.closeConnection(); 
		frame.pack(); 

		frame.setVisible(true); 


	}
	
	public static void insertSelected(Database database) {

		// initialize frame
		JFrame frame = new JFrame("Insert Options"); 
		JPanel p = new JPanel();
		JPanel buttons = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		JLabel prompt = new JLabel("Prompt");
		prompt.setText("<html>Would you like to insert?<br/>"
				+ "1. Owner<br/>"
				+ "2. Brand<br/>"
				+ "3. Clothing<br/>"
				+ "4. Item<br/>"
				+ "5. Color<br/>"
				+ "6. Type of Clothing<br/>"
				+ "7. Owns relationship<br/>"
				+ "8. Has color relationship<br/>");
		JTextField selection = new JTextField("Selection");
		JLabel selectLabel = new JLabel("Please type in selection");

		p.add(prompt);
		p.add(selectLabel);
		p.add(selection);


		frame.add(p, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.setSize(new Dimension(320,64));
		database.closeConnection(); 
		frame.pack(); 

		frame.setVisible(true); 


	}
	
	public static void updateSelected(Database database) {

		// initialize frame
		JFrame frame = new JFrame("Update Options"); 
		JPanel p = new JPanel();
		JPanel buttons = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		JLabel prompt = new JLabel("Prompt");
		prompt.setText("<html>Would you like to Update?<br/>"
				+ "1. Owner<br/>"
				+ "2. Brand<br/>"
				+ "3. Clothing<br/>"
				+ "4. Item<br/>"
				+ "5. Color<br/>"
				+ "6. Type of Clothing<br/>"
				+ "7. Owns relationship<br/>"
				+ "8. Has color relationship<br/>");
		JTextField selection = new JTextField("Selection");
		JLabel selectLabel = new JLabel("Please type in selection");

		p.add(prompt);
		p.add(selectLabel);
		p.add(selection);


		frame.add(p, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.setSize(new Dimension(320,64));
		database.closeConnection(); 
		frame.pack(); 

		frame.setVisible(true); 


	}
	
	public static void deleteSelected(Database database) {

		// initialize frame
		JFrame frame = new JFrame("Delete Options"); 
		JPanel p = new JPanel();
		JPanel buttons = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		JLabel prompt = new JLabel("Prompt");
		prompt.setText("<html>Would you like to Delete?<br/>"
				+ "1. Owner<br/>"
				+ "2. Brand<br/>"
				+ "3. Clothing<br/>"
				+ "4. Item<br/>"
				+ "5. Color<br/>"
				+ "6. Type of Clothing<br/>"
				+ "7. Owns relationship<br/>"
				+ "8. Has color relationship<br/>");
		JTextField selection = new JTextField("Selection");
		JLabel selectLabel = new JLabel("Please type in selection");

		p.add(prompt);
		p.add(selectLabel);
		p.add(selection);


		frame.add(p, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.setSize(new Dimension(320,64));
		database.closeConnection(); 
		frame.pack(); 

		frame.setVisible(true); 


	}


}
