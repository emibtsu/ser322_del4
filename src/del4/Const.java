package del4;

public final class Const {
	public final static String OWNER_TABLE = "owner"; 
	final static String BRAND_TABLE = "brand"; 
	final static String CLOTHING_TABLE = "clothing"; 
	final static String COLOR_TABLE = "color"; 
	final static String ITEM_TABLE = "item"; 
	final static String PANTS_TABLE = "pants";
	final static String SHIRT_TABLE = "shirt"; 
	final static String OUTERWEAR_TABLE = "outerwear";
	
	
	/**
	 * Selects
	 */
	
	final static String SELECT_OWNERS = "SELECT OID, OFirstName, OMiddleName, OLastName FROM OWNER;";
	final static String SELECT_SHIRTS_BY_OWNER_BRAND = "SELECT ITEM.SlotNumber, ITEM.ShelfNumber, OFirstName, OMiddleName, OLastName, Brand.BrandName, Type, CLOTHING.Material\n"
		
			+ "FROM OWNER \n"
			+ "JOIN OWNS ON OWNER.OID = OWNS.OID\n"
			+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\n"
			+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID\n"
			+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
			+ "JOIN SHIRT ON SHIRT.ClothingID = CLOTHING.ClothingID;";
	
	    
    final public static String SELECT_ALL_SHIRTS_OWNED ="SELECT SHIRT.ClothingID, SHIRT.Type, OLastname AS Name, Clothing.Material, BRAND.BrandName, ITEM.Size\n"
    		+ "FROM OWNS\n"
    		+ "JOIN OWNER ON OWNS.OID = OWNER.OID\n"
    		+ "JOIN ITEM ON OWNS.SlotNumber = ITEM.SlotNumber\n"
    		+ "	AND OWNS.ShelfNumber = ITEM.ShelfNumber\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.CLOTHINGID\n"
    		+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
    		+ "JOIN SHIRT ON SHIRT.ClothingID = Clothing.ClothingID;;";
    
    final static String SELECT_ALL_PANTS_OWNED = "SELECT PANTS.ClothingID AS ID, PANTS.IsLong, OLastname AS Name, Clothing.Material, BRAND.BrandName AS Brand, ITEM.Size\n"
    		+ "FROM OWNS\n"
    		+ "JOIN OWNER ON OWNS.OID = OWNER.OID\n"
    		+ "JOIN ITEM ON OWNS.SlotNumber = ITEM.SlotNumber\n"
    		+ "	AND OWNS.ShelfNumber = ITEM.ShelfNumber\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.CLOTHINGID\n"
    		+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
    		+ "JOIN PANTS ON PANTS.ClothingID = Clothing.ClothingID;";
    
    final static String SELECT_ALL_OUTERWEAR_OWNED ="SELECT OUTERWEAR.ClothingID, OUTERWEAR.isJacket, OLastname AS Name, Clothing.Material, BRAND.BrandName AS Brand, ITEM.Size\n"
    		+ "FROM OWNS\n"
    		+ "JOIN OWNER ON OWNS.OID = OWNER.OID\n"
    		+ "JOIN ITEM ON OWNS.SlotNumber = ITEM.SlotNumber\n"
    		+ "	AND OWNS.ShelfNumber = ITEM.ShelfNumber\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.CLOTHINGID\n"
    		+ "JOIN BRAND ON BRAND.BrandName = CLOTHING.BrandName\n"
    		+ "JOIN OUTERWEAR ON OUTERWEAR.ClothingID = Clothing.ClothingID;";
    
    final static String SELECT_ALL_CLOTHING_BY_WORN = "SELECT OFirstName, OMiddleName, OLastName, CLOTHING.ClothingID, DateWorn\r\n"
    		+ "FROM OWNER \r\n"
    		+ "JOIN OWNS ON OWNS.OID = OWNER.OID\r\n"
    		+ "JOIN ITEM ON ITEM.SlotNumber = OWNS.SlotNumber AND ITEM.ShelfNumber = OWNS.ShelfNumber\r\n"
    		+ "JOIN CLOTHING ON CLOTHING.ClothingID = ITEM.ClothingID;";
    
    final static String SELECT_ITEM_WHERE_LOCATION = "SELECT ITEM.ShelfNumber, ITEM.SlotNumber, OFirstName, OMiddleName, OLastName, DateWorn\r\n"
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
    
    
    final static String SELECT_ALL_CLOTHING_NOT_OWNED = "SELECT * FROM CLOTHING C\n"
    		+ "WHERE NOT EXISTS \n"
    		+ "\n"
    		+ "(\n"
    		+ "	SELECT * FROM OWNS\n"
    		+ "    JOIN ITEM ON OWNS.SlotNumber = ITEM.SlotNumber\n"
    		+ "		AND OWNS.ShelfNumber = ITEM.ShelfNumber\n"
    		+ "	JOIN OWNER ON OWNER.OID = OWNS.OID\n"
    		+ "    WHERE C.ClothingID = ITEM.ClothingID\n"
    		+ ") ";
    
    final static String SELECT_BRAND_WHERE_CLOTHINGID = "SELECT B.BrandName, year FROM CLOTHING JOIN BRAND B ON CLOTHING.BrandName = B.BrandName\n"
    		+ "WHERE CLOTHINGID = ?"; 
    
    final static String SELECT_CLOTHING_WHERE_CLOTHINGID = "SELECT ClothingId, Material, BrandName\n"
    		+ "FROM CLOTHING \n"
    		+ "WHERE ClothingId = ?; "; 
    
    final static String SELECT_ALL_CLOTHING_BY_OWNED = "SELECT OFirstName, OMiddleName, OLastName, S.ClothingID, ITEM.ShelfNumber, ITEM.SlotNumber\n"
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
    		+ " JOIN OWNER ON OWNS.OID = OWNER.OID;"
    		+ "	WHERE OWNS.OID =?";
    
    final static String SELECT_ALL_OWNS = "";
    
    final static String SELECT_ALL_COLOR = "SELECT ColorName FROM COLOR;";
    
    /**
     * Inserts
     */
    final static String INSERT_OWNER = "INSERT INTO OWNER \n"
    		+ "VALUES (?, ?, ?, ?, ?);";
    
    final static String INSERT_BRAND = "INSERT INTO BRAND\r\n"
    		+ "VALUES(?, ?);";
    
    final static String INSERT_CLOTHING = "INSERT INTO CLOTHING\n"
    		+ "VALUES(?,?, ?);";
    
    final static String INSERT_ITEM = "INSERT INTO ITEM\n"
    		+ "VALUES(?, ?, ?, ?, ?, ?);";
    
    //need to make method for
    final static String INSERT_COLOR = "INSERT INTO COLOR\n"
    		+ "VALUES(?);";
    
    final static String INSERT_PANTS = "INSERT INTO PANTS\n"
    		+ "VALUES(?, ?);";
    
    final static String INSERT_SHIRT = "INSERT INTO SHIRT\n"
    		+ "VAlUES (?, ?);";
    
    final static String INSERT_OUTERWEAR = "INSERT INTO OUTERWEAR\n"
    		+ "VALUES(?, ?);";
    
    final static String INSERT_OWNS = "INSERT INTO OWNS\n"
    		+ "VALUES(?, ?, ?, ?, ?;";
    
    final static String INSERT_HAS_COLOR = "INSERT INTO HAS_COLOR\n"
    		+ "VAlUES(?, ?, ?);";
    
    /**
     * Deletes 
     */
    final static String DELETE_OWNER = "DELETE FROM OWNER\n"
    		+ "WHERE OID=?;";
    
    final static String DELETE_BRAND = "DELETE FROM BRAND WHERE BrandName=?";
    
    final static String DELETE_OWNS = "DELETE FROM OWNS\n"
    		+ "WHERE OID=? AND ShelfNumber=? AND SlotNumber=?;";
    
    final static String DELETE_CLOTHING = "DELETE FROM CLOTHING\n"
    		+ "WHERE ClothingID=?;";
    
    final static String DELETE_COLOR = "DELETE FROM COLOR\n"
    		+ "WHERE ColorName =?;";
    
    final static String DELETE_HAS_COLOR = "DELETE FROM COLOR\n"
    		+ "WHERE \"?\"=ColorName AND ?=ShelfNumber AND ?=SlotNumber;";
    
    final static String DELETE_ITEM = "DELETE FROM ITEM\n"
    		+ "WHERE ShelfNumber=? AND SlotNumber=?;";
    
    final static String DELETE_OUTERWEAR = "DELETE FROM OUTERWEAR\n"
    		+ "WHERE ClothingID=?";
    
    final static String DELETE_SHIRT = "DELETE FROM SHIRT\n"
    		+ "WHERE ClothingID=?";
    
    final static String DELETE_PANTS = "DELETE FROM OUTERPANTS\n"
    		+ "WHERE ClothingId=?";
    
    // TODO implement these update statements
    final static String UPDATE_OWNER = "UPDATE OWNER SET OID = ?, OFirstName = ?, OMiddleName ="
    		+ " ?, OLastName = ?, Size = ? WHERE OID = ?;"; 
    
    final static String UPDATE_BRAND = "UPDATE BRAND SET BrandName = ?, Year = ? WHERE BrandName = ?;"; 
   
    final static String UPDATE_ITEM = ""; 
    
    final static String UPDATE_COLOR = "UPDATE Color SET ColorName = ? WHERE ColorName = ?"; 
    
    final static String UPDATE_OWNS = ""; 
    
    final static String UPDATE_PANTS = "UPDATE PANTS SET CLOTHINGID=?, isLong=? WHERE CLOTHINGID=?"; 
    
    final static String UPDATE_SHIRT = "UPDATE SHIRT SET CLOTHINGID=?, TYPE=? WHERE CLOTHINGID=?";
    
    final static String UPDATE_OUTERWEAR = "UPDATE OUTERWEAR SET CLOTHINGID=?, isJacket=? WHERE CLOTHINGID=?";
    
    final static String UPDATE_CLOTHING = ""; 
    
    
}
