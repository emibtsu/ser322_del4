package del4;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * It's a JPanel but for lists
 * All objects that extend clothing are okay to go here
 * hence the t extends Clothing 
 * @author paulgates
 *
 */
public class JListPanel extends JPanel{

	public PreparedStatement statement; 
	public ResultSet resultSet; 
	public ArrayList<Integer>specialColumns; 
	public Table table; 
	public ArrayList<JDataButton> editButtons; 
	public ArrayList<JDataButton> deleteButtons; 
	public ArrayList<ArrayList<String>> all_tupleData; 
	private int resultCount; 

	
	/**
	 * constants
	 */
	final static String UPDATE_BUTTON_IMG_PATH = "img/edit.png"; 
	final static String DELETE_BUTTON_IMG_PATH = "img/delete.png"; 
	/**
	 * Create a new list panel with clothing objects
	 * Add each item of clothing using clothing objects
	 * note that since Shirt, Outerwear, and Pants are all 
	 * subclasses of clothing these will be what is passed 
	 * @param arrayList - list of objects that inherit from clothing
	 * @throws SQLException 
	 */
	public JListPanel(PreparedStatement statement, Table table, Boolean enableEdit) throws SQLException {

		beautify(); 
		 
		all_tupleData = new ArrayList<ArrayList<String>>();
		
		editButtons = new ArrayList<JDataButton>();
		deleteButtons = new ArrayList<JDataButton>();
		
		this.table = table; 
		
		resultSet = statement.executeQuery(); 

		String headerLabelText = "";
		
		for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++)
			headerLabelText += resultSet.getMetaData().getColumnName(i+1)+"\t";
		
		this.add(CustomSwing.getCustomlabel(headerLabelText, 11, SwingConstants.LEFT, Color.gray));
		
	
		while(this.resultSet.next()) {
			resultCount++;
			addPanel(this.resultSet, enableEdit); 
		}
				
		
		closeDatabaseComponents();
			
	}
	
	/**
	 * getters and setters
	 */
	public int getResultCount(){
		return resultCount;
	}
	
	/**
	 * saves the results of every tuple being loaded into the list panel
	 * @param rs
	 */
	private ArrayList<String> saveAllTupleData(ResultSet rs) {
		
		ArrayList<String> tupleData = new ArrayList<String>();
		
		try {
			for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)		
					tupleData.add(rs.getString(i+1));	
			
			all_tupleData.add(tupleData); 
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tupleData; 
	}
	
	/**
	 * just make things look a little nicer
	 */
	private void beautify() {

		int top = 10; 
		int left = 70; 
		int bottom = 25; 
		int right = 70; 
		
		this.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
		
		// make it a box layout (this is a vertical list-like layout) 
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	/**
	 * adds the inherited clothing item to the list
	 * @param clothing
	 * @throws SQLException 
	 */
	private void addPanel(ResultSet rs, Boolean enableEdit) throws SQLException {

		JPanel containerPanel = new  JPanel(); 
		containerPanel.setLayout(new FlowLayout());
		
		JLabel label = addResultSetLabel(rs);  
		
		
		ArrayList<Object> savedData = getSpecialDataFor(rs);
		

	

		JDataButton deleteButton = new JDataButton("", savedData); 
		Icon icon2 = new ImageIcon(DELETE_BUTTON_IMG_PATH);
		deleteButton.setIcon(icon2);
	
		containerPanel.add(label);

		containerPanel.add(deleteButton); 
		
		if(enableEdit) {
			containerPanel.add(setupEditButton(savedData));
		}
		
		deleteButtons.add(deleteButton); 
		
		this.add(containerPanel); 
	}
	
	private JDataButton setupEditButton(ArrayList<Object> savedData) {
		
		ArrayList<String> tupleStringData = saveAllTupleData(resultSet);
		
		JDataButton editButton = new JDataButton("", savedData, tupleStringData); 
		
		Icon icon1 = new ImageIcon(UPDATE_BUTTON_IMG_PATH);
		editButton.setIcon(icon1);
		

		
		editButtons.add(editButton); 
		
		return editButton;
		
	}

	private ArrayList<Object> getSpecialDataFor(ResultSet rs) throws SQLException {
		
		ArrayList<Object> data = new ArrayList<Object>();
		
		for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
			String memberString = rs.getString(i+1);
			

		for(int k = 0; k < table.getPrimaryKeys().size(); k++) {
			if(table.getPrimaryKeys().get(k).equals(rs.getMetaData().getColumnName(i+1))) {
				data.add(memberString); 
			}
		}
					
	
		
		}
		
		return data; 
	}


	/**
	 * cleans up using database components such as the result set and statement
	 * @throws SQLException
	 */
	private void closeDatabaseComponents() throws SQLException {
		if(resultSet!=null)
			resultSet.close();
		if(statement!=null)
			statement.close();
	}
	
	/**
	 * takes a clothing item and creates a label from it 
	 * @param clothing
	 * @return 
	 * @throws SQLException 
	 */
	private JLabel addResultSetLabel(ResultSet rs) throws SQLException {
		
		String labelText = parseResultSet(rs); 
		
		JLabel label = CustomSwing.getCustomlabel(labelText, 12, SwingConstants.LEFT, Color.gray); 
		
		return label; 
	}
	
	/**
	 * determine what kind of clothing is this 
	 * @param clothing
	 * @return
	 * @throws SQLException 
	 */
	private String parseResultSet(ResultSet rs) throws SQLException {
		
		String descString = ""; 
		
		for(int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
			descString += formatString(rs.getString(i+1));
		}
		
		return descString; 
	}

	

	/**
	 * TODO put this inside some kind of utility class if
	 * this kind of logic is needed again 
	 * formats the string to item1\titem2\t 
	 * just makes things look nice really
	 * @param args
	 */
	public String formatString(String... args) {
		String str=""; 
	    for (String arg : args) {
	      str += arg+"\t"; 
	    }
	    
	    return str; 
	}
			
}




