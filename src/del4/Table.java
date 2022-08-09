package del4;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Table {
	
	String tableName;
	ArrayList<String> primaryKeys; 
	

	public Table(String tableName, Database database) {
		this.tableName = tableName; 
		
		try {
			primaryKeys = loadPrimaryKeys(database);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public ArrayList<String> getPrimaryKeys(){
		return primaryKeys;
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

	/**
	 * prints out the primary keys 
	 * mainly used for testing
	 */
	public void printPrimaryKeys() {
		for(int i = 0; i < primaryKeys.size(); i++)
			System.out.println(primaryKeys.get(i));
	}
}
