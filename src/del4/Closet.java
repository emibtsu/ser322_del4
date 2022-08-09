package del4;

import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import action.*;
import del4.Database.ActionType;

public class Closet {

	final static String WINDOW_TITLE_LOGIN="Closet"; 
	final static String LABEL_HEADER = "Enter Database Details"; 
	final static String BUTTON_LOGIN = "Login"; 
	final static String IMG_LOGIN_PATH = "img/closet.jpg"; 

	private static JTextField uriTextField; 
	private static JTextField usernameTextField; 
	private static JPasswordField passwordTextField; 
	private static JTextField driverTextField;
	
	private JPanel referencePanel; 

	private Database database; 
	private JFrame mainFrame; 

	public static void main(String args[]){

		Closet closet = new Closet(); 

		closet.createLogInWindow(); 
	}

	/**
	 * creates and displays the initial owner log in window
	 */
	private void createLogInWindow(){

		// initialize frame
		mainFrame = new JFrame(WINDOW_TITLE_LOGIN); 
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
		passwordTextField =  new JPasswordField("");
		JLabel passwordLabel = new JLabel("Password");

		// textfield
		driverTextField = CustomSwing.getCustomTextField("driver");
		JLabel driverLabel = new JLabel("Driver");
		driverTextField.setText("com.mysql.cj.jdbc.Driver");
		// panel 
		JImagePanel loginPanel = new JImagePanel(IMG_LOGIN_PATH); 
		loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.PAGE_AXIS));
		// button 
		JButton confirmButton = new JButton(BUTTON_LOGIN); 
		confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
		confirmButton.setForeground(new Color(117, 125, 138)); 
		confirmButton.addActionListener(e -> handleLoginButtonPressed());

		int top = 10; 
		int left = 70; 
		int bottom = 25; 
		int right = 70; 
		loginPanel.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));

		mainFrame.add(loginPanel); 
		loginPanel.add(headerLabel); 
		loginPanel.add(uriLabel);
		loginPanel.add(uriTextField); 
		loginPanel.add(usernameLabel);
		loginPanel.add(usernameTextField); 
		loginPanel.add(passwordLabel);
		loginPanel.add(passwordTextField); 
		loginPanel.add(driverLabel);
		loginPanel.add(driverTextField); 
		loginPanel.add(confirmButton); 



		loginPanel.setSize(500, 500); 
		
		setMainFrameRefPanel(loginPanel); 

		mainFrame.setLocationRelativeTo(null); 
		mainFrame.setResizable(false);

		mainFrame.pack(); 

		mainFrame.setVisible(true); 
	}


	private void setMainFrameRefPanel(JPanel ref) {
		if(referencePanel!=null)
			mainFrame.remove(referencePanel);

		mainFrame.add(ref); 
	}

	public void handleLoginButtonPressed(){

		// establish connection using a database object 
		String uri =  uriTextField.getText(); 
		String username = usernameTextField.getText(); 
		@SuppressWarnings("deprecation")
		String password = passwordTextField.getText();
		String driver_cname = driverTextField.getText(); 

		// initialize global database object
		database = new Database(uri, username, password, driver_cname); 
		
		if(database.getConnection()==null) 
			 JOptionPane.showMessageDialog(null, "Could not log in.\n\nPlease check provided info\nand stack trace.", "Login error", JOptionPane.INFORMATION_MESSAGE);
		
		else
			showPromptFrame(database); 
	}


	private void showPromptFrame(Database database) {

		// initialize frame
		JFrame frame = new JFrame("Database options"); 
		JPanel promptPanel = new JPanel();
		JPanel buttons = new JPanel();
		frame.setLocationRelativeTo(null);  

		JLabel prompt = new JLabel("Prompt");
		prompt.setText("Choose Option");
		promptPanel.add(prompt);

		JDataButton searchButton = new JDataButton(JActionFrame.SEARCH, ActionType.search);
		JDataButton insertButton = new JDataButton(JActionFrame.INSERT, ActionType.insert); 

		searchButton.addActionListener(e -> handleOptionButtonSelected(searchButton.getGeneralizedData()));
		insertButton.addActionListener(e -> handleOptionButtonSelected(insertButton.getGeneralizedData()));

		buttons.add(searchButton);
		buttons.add(insertButton);

		frame.add(promptPanel, BorderLayout.NORTH);
		frame.add(buttons, BorderLayout.SOUTH);

		frame.setSize(new Dimension(320,64));

		frame.pack(); 

		frame.setVisible(true); 
		
		setMainFrameRefPanel(promptPanel); 


	}

	private void handleOptionButtonSelected(ArrayList<Object> data) {

		if(data==null)
			return;

		JActionFrame actionFrame = null; 

		switch ((ActionType)data.get(0)) {
		case search: 
			actionFrame = new JActionFrame(ActionType.search ,"search", this.database); 
			break;
		case insert: 
			actionFrame = new JActionFrame(ActionType.insert ,"insert", this.database); 
			break;
		default:
			break; 
		}


		this.setUpPageButtonActionListeners(actionFrame); 

		if(actionFrame!=null)
			actionFrame.setVisible(true);
		
	}

	/**
	 * for every button set up in the frame, get the corresponding action options 
	 * and pass to handler
	 * @param af
	 */
	private void setUpPageButtonActionListeners(JActionFrame af) {
		for(int i = 0; i < af.pageButtons.size(); i++) {
			IActionOption actionOption =  (IActionOption) af.pageButtons.get(i).getDataSingle(); 
			af.pageButtons.get(i).addActionListener(e->handlePageButton(actionOption));
		}

	}

	/**
	 * handles a page button being pressed
	 * @param actionOption
	 */
	private void handlePageButton(IActionOption actionOption) {

		JFrame frame = null;
		try {
			frame = actionOption.getActionFrame(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		if(actionOption instanceof DDLOption)
			beginDDLSetUp((DDLOption)actionOption); 
		
		if(actionOption instanceof SearchOption)
			beginSearchSetUp((SearchOption) actionOption);
		
		if(frame!=null)
			frame.setVisible(true);
	}
	

	private void beginSearchSetUp(SearchOption option) {
		
		ArrayList<JDataButton> editButtons = option.listPanel.editButtons;
		ArrayList<JDataButton> deleteButtons = option.listPanel.deleteButtons;
		
		for(int i = 0; i < editButtons.size(); i++) 
			setAllEditButtonHandlers(editButtons.get(i), option);
		
		for(int i = 0; i < deleteButtons.size(); i++) 
			setAllDeleteButtonHandlers(deleteButtons.get(i), option);
		
	}
	
	private void setAllDeleteButtonHandlers(JDataButton button, SearchOption option) {
		ArrayList<Object> whereClauseArgs = button.getGeneralizedData(); 
		button.addActionListener(e->option.delete(whereClauseArgs));
		
	}

	private void setAllEditButtonHandlers(JDataButton button, SearchOption option) {
		button.addActionListener(e->option.edit(button));
	}


	private void beginDDLSetUp(DDLOption option) {
		option.getFormFrame().commitDDLButton.addActionListener(e->handleGoDDLButton(option));
	}

	private void handleGoDDLButton(DDLOption option) {
		
		option.getFormFrame().dispose();
		
		try {
			
			executeDDL(option.runQuery());

			
		} catch (SQLException e) {
			showExceptionMessage("Data could not be modified/inserted", e);
		} 
	}

	public static void showExceptionMessage(String msg, Exception e){
		JOptionPane.showMessageDialog(null, msg + "\n" + e.getLocalizedMessage() + "\nsee stack trace for more detail", "Exception has occured: " , JOptionPane.INFORMATION_MESSAGE);

		if(e!=null)
			e.printStackTrace();
	}
	
	private void executeDDL(PreparedStatement statement) throws SQLException {
		if(statement.executeUpdate()>0)
			JOptionPane.showMessageDialog(null, "Success!", "" , JOptionPane.INFORMATION_MESSAGE);

		
	}


}
