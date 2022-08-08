package action;
import java.sql.PreparedStatement;
import del4.*; 

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class SearchOption implements IActionOption{

	public String selectStatement; 
	public String ddl_deleteStatement;
	public Database database;
	public Table ddl_involved_table; 
	public ArrayList<Object> whereClauseArgs; 
	public JListPanel listPanel; 
	
	/**
	 * getters and setters
	 */
	public Database getDatabase() {
		return this.database;
	}
	
	public Table getTables() {
		return this.ddl_involved_table;
	}

	public String getQueryString() {
		return this.selectStatement;
	}
	
	public SearchOption(Database db, String queryString, ArrayList<Object> whereClauseArgs, String ddlInvolvedTableName, String ddl_deleteStatement) {
		this.database = db; 
		this.selectStatement = queryString; 
		this.whereClauseArgs = whereClauseArgs; 
		this.ddl_involved_table = new Table(ddlInvolvedTableName, this.database); 
		this.ddl_deleteStatement = ddl_deleteStatement; 
	}
	
	@Override
	public PreparedStatement runQuery() throws SQLException {
		
		ArrayList<Object> finalizedArgs=null; 
		
		if(this.whereClauseArgs!=null)
			finalizedArgs = getFinalizedArguments(); 
		
		return this.database.runQuery(selectStatement, finalizedArgs);  
	}
	

	@Override
	public JFrame getActionFrame() throws SQLException {
		
		JFrame frame = new JFrame(); 
		listPanel = new JListPanel(this.runQuery(), ddl_involved_table);
		
		frame.add(listPanel); 
		
		frame.setLocationRelativeTo(null); 
		frame.setResizable(false);

		frame.pack(); 
		
		return frame; 
	}
	

	/**
	 * gets all arguments from input JComponents
	 * @return
	 */
	private ArrayList<Object> getFinalizedArguments() {
		
		ArrayList<Object> f_args = new ArrayList<Object>(); 
		
		for(int i=0; i < whereClauseArgs.size(); i++)
			f_args.add(parseInputJComponent(whereClauseArgs.get(i))); 
		
		return f_args; 
	}

	
	public void delete(ArrayList<Object> deletionWhereClauseArgs){
		
		try {
			(this.database.runQuery(ddl_deleteStatement, deletionWhereClauseArgs)).executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * helps complete the WHERE clause in a selection 
	 * in that is gets the text field arguments for a 
	 * given selection
	 * @param arg
	 * @return
	 */
	private Object parseInputJComponent(Object arg) {
		
		// JTextField - returns text inside textfield argument
		if(arg instanceof JTextField) {
			return  ((JTextField) arg).getText();  
		}
		
		return null; 
	}


	

}