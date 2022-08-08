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
import del4.Database.ActionType; 

public class JActionFrame extends JFrame {
	
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
	
	ArrayList<JDataButton> pageButtons; 
	Database database; 
	JPanel buttonsPanel;
	
	public JActionFrame(ActionType at, String title, Database db) {
		
		super(title);
		this.database = db; 
		pageButtons = new ArrayList<JDataButton>(); 
		setUpFrame(at); 
	}
	
	private void setUpFrame(ActionType at) {
		
		JPanel p = new JPanel();
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS)); 
		
		Border padding = BorderFactory.createEmptyBorder(10, 40, 10, 40);

		buttonsPanel.setBorder(padding);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);  

		loadPageOptionbuttons(at); 

		
		this.add(p, BorderLayout.NORTH); 
		this.add(buttonsPanel,  BorderLayout.SOUTH); 

		this.setSize(new Dimension(320,64));
		this.pack(); 

	}
	

	/**
	 * 
	 * @return
	 */
	private void loadPageOptionbuttons(ActionType at) {
		
		// TODO - refactor to not do a switch statement here
		switch(at) {
		case search:
			loadPageButtonsAsSelect(); 
			break;
		case update: 
			loadPageButtonsAsDDL(at); 
			break; 
		case insert: 
			loadPageButtonsAsDDL(at);
		default:
			break; 
		}
	}
	


	private ArrayList<JDataButton> loadPageButtonsAsDDL(ActionType at) {
		ArrayList<JDataButton> buttons = new ArrayList<JDataButton>();
		
		loadButtonWithDDLOption(QueryStatements.OWNER_TABLE, OWNER, at);
		loadButtonWithDDLOption(QueryStatements.BRAND_TABLE, BRAND, at);
		loadButtonWithDDLOption(QueryStatements.CLOTHING_TABLE, CLOTHING, at);
		loadButtonWithDDLOption(QueryStatements.COLOR_TABLE, COLOR, at);
		loadButtonWithDDLOption(QueryStatements.ITEM_TABLE, ITEM, at);
	
		return buttons; 
	}

	// TODO - write this 
	private ArrayList<JDataButton> loadPageButtonsAsSelect() {
		
		ArrayList<JDataButton> buttons = new ArrayList<JDataButton>();
		
		
		loadButtonWithSearchOption(GET_OWNERS, QueryStatements.SELECT_OWNERS, QueryStatements.OWNER_TABLE, QueryStatements.DELETE_OWNER); 
		loadButtonWithSearchOption(GET_PANTS, QueryStatements.SELECT_ALL_PANTS, QueryStatements.PANTS_TABLE,  QueryStatements.DELETE_PANTS); 
		loadButtonWithSearchOption(GET_OUTERWEAR, QueryStatements.SELECT_ALL_OUTERWEAR, QueryStatements.SHIRT_TABLE, QueryStatements.DELETE_SHIRT); 
		loadButtonWithSearchOption(GET_CLOTHING_BY_WORN, QueryStatements.SELECT_ALL_CLOTHING_BY_WORN, QueryStatements.OUTERWEAR_TABLE, QueryStatements.DELETE_OUTERWEAR); 
		
		ArrayList<String> getItemArgs = new ArrayList<String>();
		
		getItemArgs.add("shelf no");
		getItemArgs.add("slot no");
		loadButtonWithSearchAndWhere(GET_ITEM_AT_LOCATION, QueryStatements.SELECT_ITEM_WHERE_LOCATION, getItemArgs,  QueryStatements.ITEM_TABLE, QueryStatements.DELETE_ITEM); 
		
		ArrayList<String> getBrandAgrs = new ArrayList<String>();
		getBrandAgrs.add("cid");
		
		loadButtonWithSearchAndWhere(GET_ALL_BRAND_BY_CID, QueryStatements.SELECT_BRAND_WHERE_CLOTHINGID, getBrandAgrs, QueryStatements.BRAND_TABLE, QueryStatements.DELETE_BRAND); 
		
		ArrayList<String> getClothingArgs = new ArrayList<String>();
		getBrandAgrs.add("cid");
		loadButtonWithSearchAndWhere(GET_CLOTHING_BY_ID, QueryStatements.SELECT_CLOTHING_WHERE_CLOTHINGID, getClothingArgs, QueryStatements.CLOTHING_TABLE, QueryStatements.DELETE_CLOTHING); 
		
		loadButtonWithSearchOption(GET_ALL_NOT_OWNED, QueryStatements.SELECT_ALL_CLOTHING_NOT_OWNED, QueryStatements.CLOTHING_TABLE, QueryStatements.DELETE_CLOTHING); 
		
		return buttons; 
	}
	
	/**
	 * sets up 
	 * @param tableName
	 * @param buttonText
	 * @param actionType
	 */
	private void loadButtonWithDDLOption(String tableName, String buttonText, ActionType actionType) {
		
		DDLOption option = new DDLOption(this.database, tableName, actionType); 
		JDataButton button = new JDataButton(buttonText, option); 
		this.buttonsPanel.add(button); 
		
		this.pageButtons.add(button);
	}
	
	/**
	 * sets up a SearchOption along with necessary references for textfield arguments to 
	 * @param buttonText
	 * @param databaseQuery
	 * @param tf_texts
	 */
	private void loadButtonWithSearchAndWhere(String buttonText, String databaseQuery, ArrayList<String> textFieldPlaceHolderTexts, String tableName, String ddl_deleteQuery) {
		JPanel searchItemsHorizontalPanel = new JPanel(new FlowLayout()); 

		ArrayList<Object> whereTextFields = new ArrayList<Object>();
		
		for(int i = 0; i < textFieldPlaceHolderTexts.size(); i++)
			foo(textFieldPlaceHolderTexts.get(i), whereTextFields, searchItemsHorizontalPanel); 
		
		SearchOption so = new SearchOption(this.database, databaseQuery, whereTextFields, tableName, ddl_deleteQuery); 
		JDataButton goButton = new JDataButton(buttonText, so); 
		

		
		searchItemsHorizontalPanel.add(goButton); 
		
		
		this.buttonsPanel.add(searchItemsHorizontalPanel); 
		
		pageButtons.add(goButton); 
	}

	private void loadButtonWithSearchOption(String buttonText, String selectQuery, String tableName, String ddl_deleteQuery) {
		JDataButton goButton = new JDataButton(buttonText, new SearchOption(this.database, selectQuery, null, tableName, ddl_deleteQuery)); 
		buttonsPanel.add(goButton); 
		pageButtons.add(goButton);
	}

	private void foo(String text, ArrayList<Object> list, JComponent superComponent) {
		JTextField tf = CustomSwing.getCustomTextField(text);
		list.add(tf); 
		superComponent.add(tf);
	}
	

}