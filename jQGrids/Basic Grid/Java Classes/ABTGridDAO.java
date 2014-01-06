import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.rbccm.common.util.DBConnection;

// This class is responsible for retrieving data from the table and passing it on to the ABTGridView class. There, this data would be converted to JSON format
public class ABTGridDAO {
	
	public ArrayList<ABTGrid> showABTGrid(String date) throws SQLException
	{
		
		ArrayList<ABTGrid> fidlist = new ArrayList<ABTGrid>();
		String sSQL = null;
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;

		Connection objCon=DBConnection.getSybBPSOPS1Connection();

		
		sSQL ="SQL Query comes here";
		
			objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();

		while (rs.next())
        {
          try
          {
        	  ABTGrid adpfid = new ABTGrid();
        	  try{
        	  adpfid.setBusinessDate(rs.getString(1));
        	  adpfid.setBookId(rs.getString(2));
        	  adpfid.setAccount(Integer.parseInt(rs.getString(3)));
        	  adpfid.setSymbol(rs.getString(4));
        	  adpfid.setADPposition(Float.parseFloat(rs.getString(5)));
        	  adpfid.setIDposition(Float.parseFloat(rs.getString(6)));
        	  adpfid.setDifference(Integer.parseInt(rs.getString(7)));
        	  adpfid.setAge(Integer.parseInt(rs.getString(8)));
        	  }
        	 catch(NumberFormatException e){}
      		  
        	  fidlist.add(adpfid);
          } 
          catch (SQLException e) 
          {
          	// TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        
		rs.close();
		objPreStmt.close();    
		objCon.close();

		return fidlist;

	}

}
