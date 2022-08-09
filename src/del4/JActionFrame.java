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
	
	final static String GET_OWNERS = "Owners";
	final static String GET_SHIRTS_OWNED = "Owned Shirts";
	final static String GET_PANTS_OWNED = "Owned Pants";
	final static String GET_COLOR = "Colors"; 
	final static String GET_OUTERWEAR_OWNED = "Owned Outerwear";
	
	
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
		buttonsPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		Border padding = BorderFactory.createEmptyBorder(100, 100, 150, 100);

		buttonsPanel.setBorder(padding);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);  
		this.setResizable(false);

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
			loadPageButtonsAsSearch(); 
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
		
		loadButtonWithDDLOption(Const.OWNER_TABLE, OWNER, at);
		loadButtonWithDDLOption(Const.BRAND_TABLE, BRAND, at);
		loadButtonWithDDLOption(Const.CLOTHING_TABLE, CLOTHING, at);
		loadButtonWithDDLOption(Const.COLOR_TABLE, COLOR, at);
		loadButtonWithDDLOption(Const.ITEM_TABLE, ITEM, at);
	
		return buttons; 
	}

	// TODO - write this 
	private ArrayList<JDataButton> loadPageButtonsAsSearch() {
		
		ArrayList<JDataButton> buttons = new ArrayList<JDataButton>();
		
		// TODO - make this one magic object 
		loadButtonWithSearchOption(GET_OWNERS, Const.SELECT_OWNERS, Const.OWNER_TABLE, Const.DELETE_OWNER, Const.UPDATE_OWNER); 
		
		loadButtonWithSearchOption(GET_PANTS_OWNED, Const.SELECT_ALL_PANTS_OWNED, Const.PANTS_TABLE,  Const.DELETE_PANTS, Const.UPDATE_PANTS); 
		loadButtonWithSearchOption(GET_OUTERWEAR_OWNED, Const.SELECT_ALL_OUTERWEAR_OWNED, Const.OUTERWEAR_TABLE, Const.DELETE_OUTERWEAR, Const.UPDATE_OUTERWEAR); 
		loadButtonWithSearchOption(GET_SHIRTS_OWNED, Const.SELECT_ALL_SHIRTS_OWNED, Const.SHIRT_TABLE, Const.DELETE_SHIRT, Const.UPDATE_SHIRT); 
		
		
		loadButtonWithSearchOption(GET_COLOR, Const.SELECT_ALL_COLOR, Const.COLOR_TABLE, Const.DELETE_COLOR, Const.UPDATE_COLOR); 
		
		ArrayList<String> getItemArgs = new ArrayList<String>();
		
		getItemArgs.add("shelf no");
		getItemArgs.add("slot no");
		loadButtonWithSearchAndWhere(GET_ITEM_AT_LOCATION, Const.SELECT_ITEM_WHERE_LOCATION, getItemArgs,  Const.ITEM_TABLE, Const.DELETE_ITEM, null); 
		
		ArrayList<String> getBrandAgrs = new ArrayList<String>();
		getBrandAgrs.add("cid");
		
		loadButtonWithSearchAndWhere(GET_ALL_BRAND_BY_CID, Const.SELECT_BRAND_WHERE_CLOTHINGID, getBrandAgrs, Const.BRAND_TABLE, Const.DELETE_BRAND, null); 
		
		ArrayList<String> getClothingArgs = new ArrayList<String>();
		getClothingArgs.add("cid");
		loadButtonWithSearchAndWhere(GET_CLOTHING_BY_ID, Const.SELECT_CLOTHING_WHERE_CLOTHINGID, getClothingArgs, Const.CLOTHING_TABLE, Const.DELETE_CLOTHING, null); 
		
		loadButtonWithSearchOption(GET_ALL_NOT_OWNED, Const.SELECT_ALL_CLOTHING_NOT_OWNED, Const.CLOTHING_TABLE, Const.DELETE_CLOTHING, null); 
		
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
	private void loadButtonWithSearchAndWhere(String buttonText, String databaseQuery, ArrayList<String> textFieldPlaceHolderTexts, String tableName, 
											String ddl_deleteQuery, String ddl_updateStatement) {
		JPanel searchItemsHorizontalPanel = new JPanel(new FlowLayout()); 
		searchItemsHorizontalPanel.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		ArrayList<Object> whereTextFields = new ArrayList<Object>();
		
		for(int i = 0; i < textFieldPlaceHolderTexts.size(); i++)
			setUpTextField(textFieldPlaceHolderTexts.get(i), whereTextFields, searchItemsHorizontalPanel); 
		
		SearchOption so = new SearchOption(this.database, databaseQuery, whereTextFields, tableName, ddl_deleteQuery, ddl_updateStatement); 
		JDataButton goButton = new JDataButton(buttonText, so); 
		
		searchItemsHorizontalPanel.add(goButton); 
		
		
		this.buttonsPanel.add(searchItemsHorizontalPanel); 
		
		pageButtons.add(goButton); 
	}

	private void loadButtonWithSearchOption(String buttonText, String selectQuery, String tableName, String ddl_deleteQuery, String ddl_updateStatement) {
		JDataButton goButton = new JDataButton(buttonText, new SearchOption(this.database, selectQuery, null, tableName, ddl_deleteQuery, ddl_updateStatement)); 
		buttonsPanel.add(goButton); 
		pageButtons.add(goButton);
	}

	private void setUpTextField(String text, ArrayList<Object> list, JComponent superComponent) {
		JTextField tf = CustomSwing.getCustomTextField(text);
		list.add(tf); 
		superComponent.add(tf);
	}
	

}