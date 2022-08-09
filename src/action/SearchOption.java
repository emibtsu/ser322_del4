package action;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import del4.*; 

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SearchOption implements IActionOption{

	public String selectStatement; 
	public String ddl_deleteStatement;
	public String ddl_updateStatement; 
	public Database database;
	public Table ddl_involved_table; 
	public ArrayList<Object> whereClauseArgs; 
	public JListPanel listPanel; 
	public JFrame currentActionFrame;
	public JFormFrame formFrame; 
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
	
	public SearchOption(Database db, String queryString, ArrayList<Object> whereClauseArgs, String ddlInvolvedTableName, 
						String ddl_deleteStatement, String ddl_updateStatement) {
		this.database = db; 
		this.selectStatement = queryString; 
		this.whereClauseArgs = whereClauseArgs; 
		this.ddl_involved_table = new Table(ddlInvolvedTableName, this.database); 
		this.ddl_deleteStatement = ddl_deleteStatement; 
		this.ddl_updateStatement = ddl_updateStatement;
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
		
		Boolean enableEdit =  (this.ddl_updateStatement==null) ? false : true; 
		
		currentActionFrame = new JFrame(); 
		
		
		listPanel = new JListPanel(this.runQuery(), ddl_involved_table, enableEdit);
	
		if(listPanel.getResultCount()==0) 
			 JOptionPane.showMessageDialog(null, "No results returned", "No results", JOptionPane.INFORMATION_MESSAGE);
		else {
			setupActionFrame();
			return currentActionFrame; 
		}
	
		return null;
		
	
	}
	

	private void setupActionFrame(){
		currentActionFrame.add(listPanel); 
		
		currentActionFrame.setLocationRelativeTo(null); 
		currentActionFrame.setResizable(false);

		currentActionFrame.pack(); 
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

	/**
	 * deletes a corresponding record from the result 
	 * @param whereClasue
	 */
	public void delete(ArrayList<Object> whereClause){
		
		
		WriteDelete(whereClause);
		
		currentActionFrame.dispose();
	}
	
	private void WriteDelete(ArrayList<Object> whereClause) {
		try {
			(this.database.runQuery(ddl_deleteStatement, whereClause)).executeUpdate();
		} catch (SQLException e) {
			Closet.showExceptionMessage("could not delete", e); 
		} 
	}
	
	public void edit(JDataButton jDataButton) {
		
		currentActionFrame.dispose();
		
		ArrayList<Object> whereClauseArgs = jDataButton.getGeneralizedData(); 
		ArrayList<String> savedTupleData = jDataButton.getStringData(); 
		
		String tableName = this.ddl_involved_table.getTableName();
		DatabaseMetaData metaData; 
		try {
			metaData = database.getConnection().getMetaData();

			ResultSet rs = metaData.getColumns("closet", null, tableName, null);

			ArrayList<String> args =this.database.getColumnNamesFor(rs); 
			
			formFrame = new JFormFrame(args, tableName, savedTupleData); 
			
			formFrame.commitDDLButton.addActionListener(e->writeUpdate(formFrame.getInputArgs(), whereClauseArgs));
			formFrame.setVisible(true);

		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * write an update to the database 
	 * using input args in order of query
	 * 
	 * side effects - everything must be in order with the query 
	 * TODO - fix this
	 * @param inputArgs
	 * @return
	 */
	private void writeUpdate(ArrayList<Object> inputArgs, ArrayList<Object>  whereClause) {
	
		for(int i = 0; i < whereClause.size(); i++)
		inputArgs.add(whereClause.get(i));  
		
		try {
			(this.database.runQuery(this.ddl_updateStatement, inputArgs)).executeUpdate();
		} catch (SQLException e) {
			Closet.showExceptionMessage("could not update", e); 
		}
		
		this.formFrame.dispose();
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