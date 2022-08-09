package del4;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database{
	
	final static String CATALOG_NAME = "closet"; 
	
	public enum ActionType{
		insert, delete, update, search
	}
	
	private Connection connection; 
	
	public Database(String uri, String username, String password, String driver_cname) {
		this.connection = establishConnection(uri, username, password, driver_cname); 
		
	}
	
	// geters and setters 
	public Connection getConnection(){
		return this.connection; 
	}
   
    
    /**
     * runs a query based on in file constant
     * @param query
     * @return
     * @throws SQLException
     */
    public PreparedStatement runQuery(String query, ArrayList<Object> args) throws SQLException{
    	
    	PreparedStatement s = null;  
	
		s = connection.prepareStatement(query); 
		
		if(args!=null)
			setPreparedStatementArgs(args, s); 

    	return s; 

    }
    
   
    /**
     * given a list of any size args, set the statment up with the appropriate 
     * ordered values
     * @param args
     * @param s
     * @throws SQLException
     */
    private void setPreparedStatementArgs(ArrayList<Object> args, PreparedStatement s) throws SQLException {
		for(int i = 0; i < args.size(); i++)
			s.setString(i+1, String.valueOf(args.get(i))); // Indexes starting @ 1 not 0 
    }
 
   
	/**
	 * establishConnection 
	 * @desc opens the connection to the database
	 * @param uri - uri to database 
	 * @param username - user name for database 
	 * @param password - password for database
	 * @param driver_cname - database driver class name 
	 * @return reference pointer to connection object 
	 */
    private Connection establishConnection(String uri, String username, String password, String driver_cname){
            Connection connection  = null; 
            try {
                // Load driver 
                Class.forName(driver_cname); 
                
                // get connection
                connection = DriverManager.getConnection(uri, username, password);

            }catch(SQLException e) {
                e.printStackTrace();
            }catch (ClassNotFoundException e) {
                System.exit(1);
            }
            
            return connection;
    }

    public void closeConnection() {
    	
    	try {
        	this.connection.close();
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}

    }
    
    /**
     * returns the appropriate ddl statement for the tablename and action type
     * @param tableName
     * @param actionType
     * @return
     */
    public String getDDLStatementFor(String tableName, ActionType actionType) {
    	
    	if(actionType==ActionType.update)
    		return determineDDLForUpdate(tableName); 
    	
    	if(actionType==ActionType.insert)
    		return determineDDLForInsert(tableName); 
    	return null; 
    	
    }
    
    /**
     * Get the referencing columns i.e., primary keys for given queries
     * note that SQL indexes starting at 1 not 0
     * @param query
     * @return
     */
    public ArrayList<Object> getSignificantColumnsFor(String query) {
    	
    	ArrayList<Object> columns = new ArrayList<Object>(); 
    	
    	switch(query) {
    	case Const.SELECT_OWNERS:
    		columns.add(1); 
    		break; 
    	}
    	
    	return columns; 
    }
    
    /**
     * given a table name, retrieve the appropriate insert SQL statement 
     * @param tableName
     * @return query string 
     */
    private String determineDDLForInsert(String tableName) {
    	String query = ""; 
    	
    	switch(tableName) {
    	case Const.OWNER_TABLE:
    		query = Const.INSERT_OWNER; 
    		break;
    	case Const.BRAND_TABLE: 
    		query = Const.INSERT_BRAND; 
    		break;
    	case Const.CLOTHING_TABLE: 
    		query = Const.INSERT_CLOTHING; 
    		break;
    	case Const.COLOR_TABLE: 
    		query = Const.INSERT_COLOR; 
    		break;
    	case Const.ITEM_TABLE: 
    		query = Const.INSERT_ITEM;
    		break;
    	}
    	
    	return query; 
    }
    
    
    /**
     * given a table name retrieve the appropriate update SQL statement
     * @param tableName
     * @return query string 
     */
    private String determineDDLForUpdate(String tableName) {
    	String query = ""; 
    	
    	switch(tableName) {
    	case Const.OWNER_TABLE:
    		query = Const.UPDATE_OWNER; 
    		break;
    	case Const.BRAND_TABLE: 
    		query = Const.UPDATE_BRAND; 
    		break;
    	case Const.CLOTHING_TABLE: 
    		query = Const.UPDATE_CLOTHING; 
    		break;
    	case Const.COLOR_TABLE: 
    		query = Const.UPDATE_COLOR; 
    	case Const.ITEM_TABLE: 
    		query = Const.UPDATE_ITEM;
    	case Const.OUTERWEAR_TABLE:
    		query = Const.UPDATE_OUTERWEAR;
    		
    	}
    	
    	return query; 
    }
    
    /**
     * given a set of tables get the delete query associated with them
     * @param ddl_involved_tables
     * @return
     */

	public static String getDDLDeleteQueryFor(String queryString) {
		
		String ddlString=""; 
		switch(queryString) {
    	case Const.SELECT_OWNERS:
    		ddlString = Const.DELETE_OWNER;
    		break;
    	case Const.SELECT_ALL_PANTS_OWNED: 
    		ddlString = Const.DELETE_PANTS; 
    		break;
      	case Const.SELECT_ALL_SHIRTS_OWNED: 
    		ddlString = Const.DELETE_SHIRT; 
    		break;
      	case Const.SELECT_ALL_OUTERWEAR_OWNED: 
    		ddlString = Const.DELETE_OUTERWEAR; 
    		break;
    	case Const.SELECT_ALL_CLOTHING_BY_WORN: 
    		ddlString = Const.DELETE_CLOTHING;
    		break;
    	case Const.SELECT_ITEM_WHERE_LOCATION: 
    		ddlString = Const.DELETE_ITEM;
    		break;
    	case Const.SELECT_ALL_CLOTHING_OWNED: 
    		ddlString = Const.DELETE_CLOTHING;
    		break;
    	case Const.SELECT_BRAND_WHERE_CLOTHINGID: 
    		ddlString = Const.DELETE_BRAND;
    		break;
    	case Const.SELECT_CLOTHING_WHERE_CLOTHINGID: 
    		ddlString = Const.DELETE_CLOTHING;
    		break;
    	case Const.SELECT_ALL_CLOTHING_NOT_OWNED: 
    		ddlString = Const.DELETE_CLOTHING;
    		break;
    	}
		
		return ddlString;
	}


        
    
	public ResultSet getPrimaryKeysFor(String tableName) {
		DatabaseMetaData metaData; 
		try {
			metaData = this.getConnection().getMetaData();

			ResultSet rs = metaData.getPrimaryKeys(CATALOG_NAME, null, tableName); 

			return rs;
			

		} catch (SQLException e) {
			e.printStackTrace();
		} 
	
		return null; 
	}

	public ArrayList<String> getColumnNamesFor(ResultSet rs) throws SQLException{
		
		ArrayList<String> columnNames = new ArrayList<String>();
		while (rs.next())
			columnNames.add(rs.getString("COLUMN_NAME"));

		return columnNames;
	}


}
