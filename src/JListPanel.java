import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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

	PreparedStatement statement; 
	ResultSet resultSet; 
	
	/**
	 * Create a new list panel with clothing objects
	 * Add each item of clothing using clothing objects
	 * note that since Shirt, Outerwear, and Pants are all 
	 * subclasses of clothing these will be what is passed 
	 * @param arrayList - list of objects that inherit from clothing
	 * @throws SQLException 
	 */
	public JListPanel(PreparedStatement statement, int[] specialColumns) throws SQLException {

		beautify(); 
		
		resultSet = statement.executeQuery(); 

		while(this.resultSet.next())
			addPanel(this.resultSet, specialColumns); 
		
		closeDatabaseComponents();
			
	}
	
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
	private void addPanel(ResultSet rs, int[] specialColumns) throws SQLException {

		JPanel containerPanel = new  JPanel(); 
		containerPanel.setLayout(new FlowLayout());
		
		JLabel label = addResultSetLabel(rs);  
	
		containerPanel.add(label);
		
		this.add(containerPanel); 
		 
	}
	
	
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
		
		JLabel label = CustomSwing.getCustomlabel(labelText, 14, SwingConstants.LEFT, Color.gray); 
		
		return label; 
	}
	
	/**
	 * determine what kind of clothing is this 
	 * @param clothing
	 * @return
	 * @throws SQLException 
	 */
	private String parseResultSet(ResultSet rs) throws SQLException {
		
		String descString = "Item\t"; 
		
		for(int i = 0; i < rs.getMetaData().getColumnCount(); i++)
			descString += formatString(rs.getString(i+1)); // result set starts at 1
		
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




