package action;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import del4.*;
import del4.Database.ActionType;

public class DDLOption implements IActionOption{

	private Database database; 
	private String tableName;
	private JFormFrame formFrame;
	private ActionType actionType;
	
	final static String GO_BUTTON_TEXT = "go"; 
	
	public DDLOption(Database database, String tableName, ActionType actionType){
		
		this.database = database; 
		this.tableName = tableName;
		this.actionType = actionType;
		
	}
	
	/*
	 * getters and setters
	 */
	public String getTableName() {
		return this.tableName;
	}
	
	
	public JFormFrame getFormFrame() {
		return this.formFrame; 
	}

	public ActionType getActionType() {
		return this.actionType;
	}
	
	/*
	 * IActionOption Interface Function Implementations
	 */
	@Override
	public PreparedStatement runQuery() throws SQLException {
		String queryString = this.database.getDDLStatementFor(this.tableName, actionType);
		return this.database.runQuery(queryString, formFrame.getTextFieldArgs()); 
		
	}


	@Override
	public JFrame getActionFrame() throws SQLException {
		formFrame = null;
		
		DatabaseMetaData metaData; 
		try {
			metaData = database.getConnection().getMetaData();

			ResultSet rs = metaData.getColumns("closet", null, this.getTableName(), null);

			ArrayList<String> args = new ArrayList<String>(); 

			while (rs.next())
				args.add(rs.getString("COLUMN_NAME"));

			formFrame = new JFormFrame(args, tableName); 

		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return formFrame; 
	}
	

}
