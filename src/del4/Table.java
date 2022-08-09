package del4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
	
	private String tableName;
	private ArrayList<String> primaryKeys; 
	
	/*
	 * getters and setters
	 */
	public String getTableName() {
		return tableName;
	}
	
	public ArrayList<String> getPrimaryKeys(){
		return primaryKeys;
	}
	
	public Table(String tableName, Database database) {
		this.tableName = tableName; 
		
		try {
			primaryKeys = loadPrimaryKeys(database);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	

	
	/**
	 * loads the primary objects from the 
	 * @param database
	 * @return
	 * @throws SQLException 
	 */
	private ArrayList<String> loadPrimaryKeys(Database database) throws SQLException {
		
		ArrayList<String> keys = new ArrayList<String>();
		
		ResultSet rs = database.getPrimaryKeysFor(this.tableName); 
		
		while(rs.next()) {
			keys.add(rs.getString("COLUMN_NAME"));  
		}
		
		return keys;
	}

}
