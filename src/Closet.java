
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

	Database database; 

	public static void main(String args[]){

		Closet closet = new Closet(); 

		closet.createLogInWindow(); 
	}

	/**
	 * creates and displays the initial owner log in window
	 */
	private void createLogInWindow(){

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


	public void handleLoginButtonPressed(){

		// establish connection using a database object 
		String uri =  uriTextField.getText(); 
		String username = usernameTextField.getText(); 
		String password = passwordTextField.getText();
		String driver_cname = driverTextField.getText();  

		// initialize global database object
		database = new Database(uri, username, password, driver_cname); 
		promptType(database); 
	}


	private void promptType(Database database) {

		// initialize frame
		JFrame frame = new JFrame("Database options"); 
		JPanel p = new JPanel();
		JPanel buttons = new JPanel();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);  

		JLabel prompt = new JLabel("Prompt");
		prompt.setText("Would you like to search, insert, update, delete?");
		p.add(prompt);

		JDataButton searchButton = new JDataButton(JActionFrame.SEARCH, JActionFrame.ActionType.search);
		JDataButton insertButton = new JDataButton(JActionFrame.INSERT, JActionFrame.ActionType.insert); 
		JDataButton deleteButton = new JDataButton(JActionFrame.DELETE, JActionFrame.ActionType.delete); 
		JDataButton updateButton = new JDataButton(JActionFrame.UPDATE, JActionFrame.ActionType.update); 

		searchButton.addActionListener(e -> handleOptionButtonSelected(searchButton.data));
		insertButton.addActionListener(e -> handleOptionButtonSelected(insertButton.data));
		deleteButton.addActionListener(e -> handleOptionButtonSelected(deleteButton.data));
		updateButton.addActionListener(e -> handleOptionButtonSelected(updateButton.data));

		buttons.add(searchButton);
		buttons.add(insertButton);
		buttons.add(updateButton);
		buttons.add(deleteButton);

		frame.add(p, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.setSize(new Dimension(320,64));

		frame.pack(); 

		frame.setVisible(true); 


	}

	private void handleOptionButtonSelected(ArrayList<Object> data) {

		if(data==null)
			return;

		JActionFrame actionFrame = null; 

		switch ((JActionFrame.ActionType)data.get(0)) {
		case search: 
			actionFrame = new JActionFrame(JActionFrame.ActionType.search ,"search"); 
			break;
		case insert: 
			actionFrame = new JActionFrame(JActionFrame.ActionType.insert ,"insert"); 
			break; 
		case delete: 
			actionFrame = new JActionFrame(JActionFrame.ActionType.delete ,"delete");
			break;
		case update: 
			actionFrame = new JActionFrame(JActionFrame.ActionType.update ,"update");
			break;
		default:
			break;
		}


		this.setUpPageButtonActionListeners(actionFrame); 

		if(actionFrame!=null)
			actionFrame.setVisible(true);
	}


	private void setUpPageButtonActionListeners(JActionFrame af) {
		for(int i = 0; i < af.pageButtons.size(); i++) {
			JActionFrame.ActionOption actionOption = (JActionFrame.ActionOption) af.pageButtons.get(i).data.get(0); 
			af.pageButtons.get(i).addActionListener(e->handlePageButton(actionOption));
		}

	}

	private void handlePageButton(JActionFrame.ActionOption actionOption) {

		String tableName = ""; 

		switch(actionOption) {
		case clothing: 
			tableName = "Clothing"; 
			break; 

		case owner:
			tableName = "Owner";
			break; 

		case brand: 
			tableName = "Brand"; 
			break;

		case color:
			tableName = "Color"; 
			break;

		case item: 
			tableName = "item"; 
			break; 

		case owns:
			tableName = "owns"; 

		case pants:
			tableName = "pants"; 

		case shirt:
			tableName = "shirt"; 

		case outerwear:
			tableName = "outerwear"; 
		}
		if(!tableName.contains("get")) {
			DatabaseMetaData metaData; 
			try {
				metaData = database.getConnection().getMetaData();

				ResultSet rs = metaData.getColumns("closet", null, tableName, null);

				ArrayList<String> args = new ArrayList<String>(); 

				while (rs.next())
					args.add(rs.getString("COLUMN_NAME"));

				JFormFrame formFrame = new JFormFrame(args); 

				formFrame.setVisible(true);

			} catch (SQLException e) {
				showExceptionMessage("trouble getting meta data for database", e); 
			} 


		}
		else {
			String query = "";
			switch(actionOption) {

			case getOwner: 
				query = Database.SELECT_OWNERS; 
				break; 

			case getShirt:
				query = Database.SELECT_ALL_SHIRTS;
				break; 

			case getPants: 
				query = Database.SELECT_ALL_PANTS;
				break;

			case getClothingByCid:
				query = Database.SELECT_CLOTHING_WHERE_CLOTHINGID;
				break;

			case getBrandByCid: 
				query = Database.SELECT_BRAND_WHERE_CLOTHINGID;
				break; 

			case getUnowned:
				query = Database.SELECT_ALL_CLOTHING_NOT_OWNED;
				break;
			case getOwned:
				query = Database.SELECT_ALL_CLOTHING_BY_OWNED;
				break;
			case getClothingByWorn:
				query = Database.SELECT_ALL_CLOTHING_BY_WORN;
				break;
			case getOuterwear:
				query = Database.SELECT_ALL_CLOTHING_BY_WORN;
				break;
			case getItemAt:
				query = Database.SELECT_ALL_OUTERWEAR;
				break;
			}
			//TODO: need to make query display on formFrame
			ArrayList<String> args = new ArrayList<String>(); 



			JFormFrame formFrame = new JFormFrame(args); 

			formFrame.setVisible(true);

		}

	}

	public static void showExceptionMessage(String msg, Exception e){
		JOptionPane.showMessageDialog(null, msg, "Exception has occured: " + msg + "see stack trace for more detail", JOptionPane.INFORMATION_MESSAGE);

		if(e!=null)
			e.printStackTrace();
	}


}
