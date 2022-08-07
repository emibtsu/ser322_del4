package action;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;

import del4.*; 

public class DDLOption implements IActionOption{
	
	private String queryString; 
	private Database database; 
	private String tableName;
	
	public String getTableName() {
		return this.tableName;
	}

	@Override
	public PreparedStatement runQuery() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JFrame getActionFrame() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	} 
	
}
