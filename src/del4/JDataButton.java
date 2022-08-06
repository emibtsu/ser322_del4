package del4;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;

	/**
	 * Its a button, but with data! 
	 * 
	 * useful for storing things when yo uhave to create a list 
	 * of itmes. e.g., set the data=clothingID for a delete button
	 * @author paulgates
	 *
	 */
public class JDataButton extends JButton{
	private ArrayList<Object> data;  

	/**
	 * Constructor - takes that text that appears on the button and any data to be saved 
	 * for later, i.e., the entire point of making this class
	 * @param text
	 * @param specialData
	 * @throws SQLException
	 */
	public JDataButton(String text,  ArrayList<Object> specialData) {
		
		this.setText(text);
		this.data = specialData; 
		
		beautify(); 
	}
	
	
	 /**
	 * Constructor - takes that text that appears on the button and any data to be saved 
	 * for later, i.e., the entire point of making this class
	 * this overloaded constructor excepts a single argument for data, just to make things
	 * nice and neat :)
	 * @param text
	 * @param specialData
	 * @throws SQLException
	 */
	public JDataButton(String text,  Object specialData) {
		

		this.data = new ArrayList<Object>(); 
		data.add(specialData); 
		
		this.setText(text);
		beautify(); 
	}
	
	
	/**
	 * use this method to get data when you only loaded the button with a single 
	 * piece of data (basically what is used for single attribute primary key data objects) 
	 * @return
	 */
	public Object getDataSingle() {
		return this.data.get(0);
	}
	
	public ArrayList<Object> getData() {
		return this.data; 
	}
	
	/**
	 * make the darn button look nice  
	 */
	private void beautify() {
		this.setFont(new Font("Arial", Font.BOLD, 14));
		this.setForeground(new Color(117, 125, 138)); 
	}
}