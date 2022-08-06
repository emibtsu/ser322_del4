import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
	
	enum ActionOption{
		owner, clothing, brand, color, item, owns, shirt, pants, outerwear, 
		getOwner, getShirt, getPants, getClothingByCid, getBrandByCid, getUnowned, getOwned, getClothingByWorn, getOuterwear, getItemAt
	}
	private ActionType actionType; 
	
	ArrayList<JDataButton> pageButtons; 
	Database database; 
	
	public JActionFrame(ActionType at, String title) {
	
		super(title);
		
		pageButtons = new ArrayList<JDataButton>(); 
		
		this.actionType = at; 
		setUpFrame(); 
	}
	
	private void setUpFrame() {
		
		JPanel p = new JPanel();
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS)); 
		
		Border padding = BorderFactory.createEmptyBorder(10, 40, 10, 40);

		buttons.setBorder(padding);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);  

		loadPageOptionbuttons(); 

		
		addComponents(p); 
		
		for(int i = 0; i < pageButtons.size(); i++)
			buttons.add(pageButtons.get(i)); 
		
		this.add(p, BorderLayout.NORTH); 
		this.add(buttons,  BorderLayout.SOUTH); 

		this.setSize(new Dimension(320,64));
		this.pack(); 

	}
	

	/**
	 * 
	 * @return
	 */
	private void loadPageOptionbuttons() {
		
		if(this.actionType==ActionType.search)
			loadPageButtonsAsSelect(); 
		else
			loadPageButtonsAsDDL(); 
		
	}
	
	private ArrayList<JDataButton> loadPageButtonsAsDDL() {
		ArrayList<JDataButton> buttons = new ArrayList<JDataButton>();
		
		pageButtons.add(new JDataButton(OWNER, ActionOption.owner)); 
		pageButtons.add( new JDataButton(CLOTHING, ActionOption.clothing));
		pageButtons.add(new JDataButton(BRAND, ActionOption.brand)); 
		pageButtons.add(new JDataButton(COLOR, ActionOption.color)); 
		pageButtons.add(new JDataButton(ITEM, ActionOption.item)); 
		pageButtons.add(new JDataButton(OWNS, ActionOption.owns));  
	
		return buttons; 
	}

	// TODO - write this 
	private ArrayList<JDataButton> loadPageButtonsAsSelect() {
		
		ArrayList<JDataButton> buttons = new ArrayList<JDataButton>();
		
		pageButtons.add(new JDataButton(GET_OWNERS, ActionOption.getOwner)); 
		pageButtons.add( new JDataButton(GET_SHIRTS, ActionOption.getShirt));
		pageButtons.add(new JDataButton(GET_PANTS, ActionOption.getPants)); 
		pageButtons.add(new JDataButton(GET_OUTERWEAR, ActionOption.getOuterwear)); 
		pageButtons.add(new JDataButton(GET_CLOTHING_BY_WORN, ActionOption.getClothingByWorn)); 
		pageButtons.add(new JDataButton(GET_ITEM_AT_LOCATION, ActionOption.getItemAt));  
		
		pageButtons.add(new JDataButton(GET_ALL_OWNED, ActionOption.getOwned)); 
		pageButtons.add(new JDataButton(GET_ALL_NOT_OWNED, ActionOption.getUnowned)); 
		pageButtons.add(new JDataButton(GET_ALL_BRAND_BY_CID, ActionOption.getBrandByCid)); 
		pageButtons.add(new JDataButton(GET_CLOTHING_BY_ID, ActionOption.getClothingByCid));  
		
		return buttons; 
	}

	private void addComponents(JComponent sup, JComponent ...components) {
		
		for (JComponent component : components) {
		      sup.add(component); 
		 }
	}
	

}