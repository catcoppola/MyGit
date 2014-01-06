import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

//This class is responsible for converting the back-end data to JSON format

public class ABTGridView {
	
	@SuppressWarnings("unchecked")
	public String displayABTGrid(String date) throws SQLException
	{
		
		ABTGridDAO adpfiddao = new ABTGridDAO();
		ArrayList<ABTGrid> fidlist=adpfiddao.showABTGrid(date); // Submit the query to back-end and retrieve the data
		
		JSONObject root = new JSONObject();
		JSONArray rows = new JSONArray();
		
		String BusinessDate;
		String BookId;
		String Symbol;
		int Account;
		int Age;
		float ADPposition;
		float IDposition;
		float Difference;
		String output="";

		int i=0;
        
		//Create JSON Data
        for (ADPFidessa af : fidlist)
        {
		   	i++;
        	
        JSONArray cell = new JSONArray();
    	JSONObject record = new JSONObject();
    		
    	 BusinessDate=af.getBusinessDate();
		 BookId=af.getBookId();
		 Symbol=af.getSymbol();
		 Account=af.getAccount();
		 Age=af.getAge();
		 ADPposition=af.getADPposition();
		 IDposition=af.getIDposition();
		 Difference=af.getDifference();
        
        cell.add(BusinessDate);
        cell.add(BookId);
        cell.add(Account);
        cell.add(Symbol);
        cell.add(ADPposition);
        cell.add(IDposition);
        cell.add(Difference);
        cell.add(Age);
 
        record.put("id",String.valueOf(i));
  		record.put("cell",cell);
        
  		rows.add(record); 
        
        }
        
		
		root.put("page","");
		root.put("total","");
		root.put("records","");
		root.put("rows",rows);
		
		output=root.toString();
	
		return output;
	}

}
