package del4;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import action.*; 
class JActionFrame extends JFrame {
	
	final static String SEARCH_OPTIONS = "<html>Would you like to search?<br/>"
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
			+ "11. Select Clothes by Owner ID<html>";
	
	final static String DDL_OPTIONS = "<html>Would you like to Delete?<br/>"
			+ "1. Owner<br/>"
			+ "2. Brand<br/>"
			+ "3. Clothing<br/>"
			+ "4. Item<br/>"
			+ "5. Color<br/>"
			+ "6. Type of Clothing<br/>"
			+ "7. Owns relationship<br/>"
			+ "8. Has color relationship<br/>";
	
	final static String TYPE_SELECTION_PROMPT = "Please type in selection";
	
	final static String SELECTION = "Selection"; 
	
	final static String SEARCH = "search"; 
	final static String DELETE = "delete"; 
	final static String UPDATE = "update"; 
	final static String INSERT = "insert"; 
	
	final static String OWNER = "Owner";
	final static String BRAND = "Brand"; 
	final static String CLOTHING = "Clothing";
	final static String COLOR = "Color";
	final static String ITEM = "Item";
	final static String OWNS = "Owns"; 
	
	final static String GET_OWNERS = "Get Owners";
	final static String GET_SHIRTS = "Get Shirts";
	final static String GET_PANTS = "Get Pants";
	final static String GET_OUTERWEAR = "Get Outerwear";
	final static String GET_CLOTHING_BY_WORN = "Get Clothing Worn";
	final static String GET_ITEM_AT_LOCATION = "Get Item at Location";
	final static String GET_ALL_OWNED = "Get clothing Owned";
	final static String GET_ALL_NOT_OWNED = "Get clothing unowned";
	final static String GET_ALL_BRAND_BY_CID = "Get Brand by CID";
	final static String GET_CLOTHING_BY_ID = "Get Clothing by CID";
	
	enum ActionType{
		insert, delete, update, search
	}
	
	private ActionType actionType; 
	
	ArrayList<JDataButton> pageButtons; 
	Database database; 
	
	public JActionFrame(ActionType at, String title, Database db) {
	
		super(title);
		this.database = db; 
		pageButtons = new ArrayList<JDataButton>(); 
		
		this.actionType = at; 
		setUpFrame(); 
	}
	
	private void setUpFrame() {
		
		JPanel p = new JPanel();
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS)); 
		
		Border padding = BorderFactory.createEmptyBorder(10, 40, 10, 40);

		buttonsPanel.setBorder(padding);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);  

		loadPageOptionbuttons(buttonsPanel); 

		
		this.add(p, BorderLayout.NORTH); 
		this.add(buttonsPanel,  BorderLayout.SOUTH); 

		this.setSize(new Dimension(320,64));
		this.pack(); 

	}
	

	/**
	 * 
	 * @return
	 */
	private void loadPageOptionbuttons(JPanel buttonsPanel) {
		
		if(this.actionType==ActionType.search)
			loadPageButtonsAsSelect(buttonsPanel); 
		else
			loadPageButtonsAsDDL(buttonsPanel); 
		
	}
	


	private ArrayList<JDataButton> loadPageButtonsAsDDL(JPanel buttonsPanel) {
		ArrayList<JDataButton> buttons = new ArrayList<JDataButton>();
		
		//buttonsPanel.add(new JDataButton(OWNER, ActionOption.owner)); 
		//buttonsPanel.add( new JDataButton(CLOTHING, ActionOption.clothing));
		//pageButtons.add(new JDataButton(BRAND, ActionOption.brand)); 
		//buttonsPanel.add(new JDataButton(COLOR, ActionOption.color)); 
		//buttonsPanel.add(new JDataButton(ITEM, ActionOption.item)); 
		//buttonsPanel.add(new JDataButton(OWNS, ActionOption.owns));  
	
		return buttons; 
	}

	// TODO - write this 
	private ArrayList<JDataButton> loadPageButtonsAsSelect(JPanel buttonsPanel) {
		
		ArrayList<JDataButton> buttons = new ArrayList<JDataButton>();
		
		
		initializeAndLoadDataButton(buttonsPanel, GET_OWNERS, Database.SELECT_ALL_CLOTHING_OWNED); 
		initializeAndLoadDataButton(buttonsPanel, GET_PANTS, Database.SELECT_ALL_PANTS); 
		initializeAndLoadDataButton(buttonsPanel, GET_OUTERWEAR, Database.SELECT_ALL_OUTERWEAR); 
		initializeAndLoadDataButton(buttonsPanel, GET_CLOTHING_BY_WORN, Database.SELECT_ALL_CLOTHING_BY_WORN); 
		
		initalizeAndLoadDataButton(buttonsPanel, GET_ITEM_AT_LOCATION, Database.SELECT_ITEM_WHERE_LOCATION, "shelf no", "slot no"); 
		initalizeAndLoadDataButton(buttonsPanel, GET_ALL_OWNED, Database.SELECT_ALL_CLOTHING_OWNED); 
		initalizeAndLoadDataButton(buttonsPanel, GET_ALL_BRAND_BY_CID, Database.SELECT_BRAND_WHERE_CLOTHINGID, "cid"); 
		initalizeAndLoadDataButton(buttonsPanel, GET_CLOTHING_BY_ID, Database.SELECT_CLOTHING_WHERE_CLOTHINGID, "cid"); 
		initalizeAndLoadDataButton(buttonsPanel, GET_ALL_NOT_OWNED, Database.SELECT_ALL_CLOTHING_NOT_OWNED); 
		
		return buttons; 
	}
	
	private void initalizeAndLoadDataButton( JPanel buttonsPanel, String buttonText, String databaseQuery, String... tf_texts) {
		JPanel searchItemsHorizontalPanel = new JPanel(new FlowLayout()); 

		ArrayList<Object> textFieldRefs = new ArrayList<Object>(); 
		
		SearchOption so = new SearchOption(this.database, databaseQuery, textFieldRefs); 
		JDataButton goButton = new JDataButton(buttonText, so); 
		searchItemsHorizontalPanel.add(goButton); 
		
		for (String text : tf_texts)
			foo(text, textFieldRefs, searchItemsHorizontalPanel); 

		
		buttonsPanel.add(searchItemsHorizontalPanel); 
		
		pageButtons.add(goButton); 
	}

	private void initializeAndLoadDataButton(JPanel buttonsPanel, String buttonText, String databaseQuery) {
		JDataButton goButton = new JDataButton(buttonText, new SearchOption(this.database, databaseQuery, null)); 
		buttonsPanel.add(goButton); 
		pageButtons.add(goButton);
	}

	private void foo(String text, ArrayList<Object> list, JComponent superComponent) {
		JTextField tf = CustomSwing.getCustomTextField(text);
		list.add(tf); 
		superComponent.add(tf);
	}
	

}