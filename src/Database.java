import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Database{

	private Connection connection; 
	
	public Database(String uri, String username, String password, String driver_cname) {
		this.connection = establishConnection(uri, username, password, driver_cname); 
	}
	
	// geters and setters 
	public Connection getConnection(){
		return this.connection; 
	}
   
    // queries
	final static String SELECT_SHIRTS_BY_OWNER_BRAND = "SELECT ITEM.SlotNumber, ITEM.ShelfNumber, OFirstName, OMiddleName, OLastName, Brand.BrandName, Type, CLOTHING.Material\n"
		
			+ "FROM OWNER \n"
			+ "JOIN OWNS ON OWNER.OID = OWNS.OID\n"
			+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\n"
			+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID\n"
			+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
			+ "JOIN SHIRT ON SHIRT.ClothingID = CLOTHING.ClothingID;";
	
	
    final static String SELECT_ALL_SHIRT = "SELECT ClothingID, Type FROM SHIRT";
    
    final public String SELECT_ALL_SHIRTS ="SELECT OFirstName, OMiddleName, OLastName, Brand.BrandName, Type, CLOTHING.Material\n"
    		+ "FROM OWNER \n"
    		+ "JOIN OWNS ON OWNER.OID = OWNS.OID\n"
    		+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID\n"
    		+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
    		+ "JOIN SHIRT ON SHIRT.ClothingID = CLOTHING.ClothingID;";
    
    final static String SELECT_ALL_PANTS = "SELECT OFirstName, OMiddleName, OLastName, Brand.BrandName, isLong, CLOTHING.Material\n"
    		+ "FROM OWNER\n" 
    		+ "JOIN OWNS ON OWNER.OID = OWNS.OID\n"
    		+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID\n"
    		+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
    		+ "JOIN PANTS ON PANTS.ClothingID = CLOTHING.ClothingID;";
    
    final static String SELECT_ALL_OUTERWEAR ="SELECT OFirstName, OMiddleName, OLastName, Brand.BrandName, isJacket, CLOTHING.Material\r\n"
    		+ "FROM OWNER \r\n"
    		+ "JOIN OWNS ON OWNER.OID = OWNS.OID\r\n"
    		+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\r\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID\r\n"
    		+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\r\n"
    		+ "JOIN OUTERWEAR ON OUTERWEAR.ClothingID = CLOTHING.ClothingID;";
    
    final static String SELECT_ALL_CLOTHING_BY_WORN = "SELECT OFirstName, OMiddleName, OLastName, CLOTHING.ClothingID, DateWorn\r\n"
    		+ "FROM OWNER \r\n"
    		+ "JOIN OWNS ON OWNS.OID = OWNER.OID\r\n"
    		+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\r\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID;";
    
    final static String SELECT_ITEM_WHERE_LOCATION = "SELECT OFirstName, OMiddleName, OLastName, CLOTHING.ClothingID, DateWorn\r\n"
    		+ "FROM OWNER \r\n"
    		+ "JOIN OWNS ON OWNS.OID = OWNER.OID\r\n"
    		+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\r\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID\r\n"
    		+ "WHERE ITEM.ShelfNumber = ? AND ITEM.SlotNumber = ?;";
    
    final static String SELECT_ALL_CLOTHING_OWNED = "SELECT OFirstName, OMiddleName, OLastName, S.ClothingID, ITEM.ShelfNumber, ITEM.SlotNumber\n"
    		+ "FROM\n"
    		+ "(( \n"
    		+ "SELECT CLOTHING.ClothingID\n"
    		+ "FROM CLOTHING\n"
    		+ " JOIN SHIRT ON SHIRT.ClothingID = CLOTHING.ClothingID)\n"
    		+ " UNION \n"
    		+ " (\n"
    		+ " SELECT CLOTHING.ClothingID\n"
    		+ "FROM CLOTHING\n"
    		+ " JOIN PANTS ON PANTS.ClothingID = CLOTHING.ClothingID\n"
    		+ " )\n"
    		+ " UNION\n"
    		+ "  (\n"
    		+ " SELECT CLOTHING.ClothingID\n"
    		+ "FROM CLOTHING\n"
    		+ " JOIN OUTERWEAR ON OUTERWEAR.ClothingID = CLOTHING.ClothingID\n"
    		+ " ) ) AS S\n"
    		+ " \r\n"
    		+ " JOIN ITEM ON S.ClothingID = ITEM.ClothingID\n"
    		+ " JOIN OWNS ON OWNS.SlotNumber = ITEM.SlotNumber AND OWNS.ShelfNumber = ITEM.ShelfNumber\n"
    		+ " JOIN OWNER ON OWNS.OID = OWNER.OID;";
    
    final static String SELECT_ALL_CLOTHING_NOT_OWNED = "SELECT Clothing.ClothingID, BRAND.BrandName, I.ShelfNumber, I.SlotNumber\n"
    		+ " FROM ITEM AS I\n"
    		+ " JOIN CLOTHING ON I.ClothingID = CLOTHING.ClothingID\n"
    		+ " JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
    		+ " WHERE NOT EXISTS \n"
    		+ "(\n"
    		+ "	SELECT * \n"
    		+ "    FROM OWNS \r\n"
    		+ "    WHERE I.SlotNumber = OWNS.SlotNumber \n"
    		+ "		AND I.ShelfNumber = OWNS.ShelfNumber\n"
    		+ ");";
    
    final static String SELECT_BRAND_WHERE_CLOTHINGID = "SELECT B.BrandName, year FROM CLOTHING JOIN BRAND B ON CLOTHING.BrandName = B.BrandName\n"
    		+ "WHERE CLOTHINGID = ?"; 
    
    //need to make method
    final static String SELECT_CLOTHING_WHERE_CLOTHINGID = "SELECT ClothingId, Material, BrandName\n"
    		+ "FROM CLOTHING \n"
    		+ "WHERE ClothingId = ?; "; 
    

    /**
     * 
     * @return
     * @throws SQLException
     */
    public PreparedStatement getShirtsByOwnerAndBrand() throws SQLException {

		PreparedStatement s = null;  

		try {
			
			s = connection.prepareStatement(SELECT_SHIRTS_BY_OWNER_BRAND); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    public PreparedStatement getAllPants() throws SQLException {

		PreparedStatement s = null;  

		try {
			
			s = connection.prepareStatement(SELECT_ALL_PANTS); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    public PreparedStatement getAllOuterwear() throws SQLException {

		PreparedStatement s = null;  

		try {
			
			s = connection.prepareStatement(SELECT_ALL_OUTERWEAR); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    public PreparedStatement getAllShirts() throws SQLException {

		PreparedStatement s = null;  

		try {
			
			s = connection.prepareStatement(SELECT_ALL_SHIRTS); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    public PreparedStatement getAllClothingByWorn() throws SQLException {

		PreparedStatement s = null;  

		try {
			
			s = connection.prepareStatement(SELECT_ALL_CLOTHING_BY_WORN); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    public PreparedStatement getAllClothingOwned() throws SQLException {

		PreparedStatement s = null;  

		try {
			
			s = connection.prepareStatement(SELECT_ALL_CLOTHING_OWNED); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @return
     * @throws SQLException
     */
    public PreparedStatement getAllClothingNotOwned() throws SQLException {

		PreparedStatement s = null;  

		try {
			
			s = connection.prepareStatement(SELECT_ALL_CLOTHING_NOT_OWNED); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @param slotNo
     * @param shelfNo
     * @return
     * @throws SQLException
     */
    public PreparedStatement getItemWhereLocation(String slotNo, String shelfNo) throws SQLException {

		PreparedStatement s = null;  
		
		try {
			String withVals = SELECT_ITEM_WHERE_LOCATION;
			withVals = withVals.replaceFirst("?", slotNo);
			withVals = withVals.replaceFirst("?", shelfNo);
			s = connection.prepareStatement(withVals); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @param clothingID
     * @return
     * @throws SQLException
     */
    public PreparedStatement getBrandWhereClothingID(String clothingID) throws SQLException {

		PreparedStatement s = null;  
		
		try {
			String withVals = SELECT_BRAND_WHERE_CLOTHINGID;
			withVals = withVals.replaceFirst("?", clothingID);
			s = connection.prepareStatement(withVals); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  

    }
    
    /**
     * 
     * @param clothingID
     * @return
     * @throws SQLException
     */
    public PreparedStatement getClothingWhereClothingID(String clothingID) throws SQLException {

		PreparedStatement s = null;  
		
		try {
			String withVals = SELECT_CLOTHING_WHERE_CLOTHINGID;
			withVals = withVals.replaceFirst("?", clothingID);
			s = connection.prepareStatement(withVals); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return s;  
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
                System.out.println("class not found "+driver_cname);
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
        
}

	
