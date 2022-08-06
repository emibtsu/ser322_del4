package action;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import del4.*; 

public interface IActionOption {
	
	/**
	 * runs the query that this action is responsible for
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement runQuery() throws SQLException; 
	
	public JFrame getActionFrame() throws SQLException;
}
