package action;
import java.sql.PreparedStatement;
import del4.*; 

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class SearchOption implements IActionOption{

	protected String queryString; 
	protected Database database;
	ArrayList<Object> args; 
	
	public Database getDatabase() {
		return this.database;
	}
	
	public SearchOption(Database db, String queryString, ArrayList<Object> args) {
		this.database = db; 
		this.queryString = queryString; 
		this.args = args; 
	}
	
	@Override
	public PreparedStatement runQuery() throws SQLException {
		
		ArrayList<Object> finalizedArgs=null; 
		
		if(this.args!=null)
			finalizedArgs = getFinalizedArguments(); 
		
		return this.database.runQuery(queryString, finalizedArgs);  
	}
	

	@Override
	public JFrame getActionFrame() throws SQLException {
		
		JFrame frame = new JFrame(); 
		JListPanel listPanel = new JListPanel(this.runQuery(), null);
		
		frame.add(listPanel); 
		
		frame.setLocationRelativeTo(null); 
		frame.setResizable(false);

		frame.pack(); 
		
		return frame; 

	}
	
	private ArrayList<Object> getFinalizedArguments() {
		
		ArrayList<Object> f_args = new ArrayList<Object>(); 
		
		for(int i=0; i < args.size(); i++)
			f_args.add(foo(args.get(i))); 
		
		return f_args; 
	}

	
	private Object foo(Object arg) {
		
		// JTextField - returns text inside textfield argument
		if(arg instanceof JTextField) {
			return  ((JTextField) arg).getText();  
		}
		
		return null; 
	}
	

}