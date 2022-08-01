import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

class Database{

   
    // select every SHIRT by OWNER and list owner name, brand name, material, and type of shirt

    final static String SELECT_ALL_SHIRT = "SELECT OFirstName, OMiddleName, OLastName, Brand.BrandName, Type, CLOTHING.Material"
                                            + "FROM OWNER"
                                            + "JOIN OWNS ON OWNER.OID = OWNS.OID"
                                            + "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber"
                                                    + "AND ITEM.ShelfNumber = OWNS.ShelfNumber"
                                            + "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID"
                                            + "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName"
                                            + "JOIN SHIRT ON SHIRT.ClothingID = CLOTHING.ClothingID";

    
	/**
	 * openConnection 
	 * @desc opens the connection to the database
	 * @param uri - uri to database 
	 * @param username - user name for database 
	 * @param password - password for database
	 * @param driver_cname - database driver class name 
	 * @return reference pointer to connection object 
	 */
    public static Connection getConnection(String uri, String username, String password, String driver_cname){
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

        /**
        * given result set gather meta data and print the column headers
        * @param r - result set to be parsed
        * @throws SQLException
        */
        private static void printColumnHeaders(ResultSet r) throws SQLException {
            ResultSetMetaData metaData = r.getMetaData(); 
            
            int numColumns = metaData.getColumnCount(); 
            
            for(int i = 1; i <= numColumns; i++)
                System.out.print(metaData.getColumnLabel(i)+"\t");
            System.out.println("\n");
        }
        
}

	
