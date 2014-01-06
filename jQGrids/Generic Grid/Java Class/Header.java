import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;  

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

// As this is the Generic Grid, I have to handle the JSP creation, Data retrieveal and Data Conversion with a single class. There is also the added complexity of concurrency control

public class Header
{ 
	
	public static void main(String args[]) throws SQLException
	{
		Header db=new Header();
		String output = db.printHeader (/*Some parameters*/);
		System.out.println("" +output);
	}
	
	// Print the grid without concurrency controls in place
	public String printjQGRid(String filter_id) throws SQLException
	{
		StringBuffer grid = new StringBuffer(); // This StringBuffer represents the entire code for the JSP of the grid
		
		ArrayList<String> columns = new ArrayList<String>();
		
		String sSQL = null;
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;

			String id=filter_id;
		
			String x=new String();
		
		Connection objCon=DBConnection.getSybBPSOPS1Connection();
		
		sSQL="Some SQL Query"+String.valueOf(id)+"'"; // This query is responsible for linking to the appropriate table in the back end. I will create the grid based on this table
		
	
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		

		while (rs.next())
        {
          try
          {	
        	  x=rs.getString(2);
        	  columns.add(x);
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
		
		String cnms = "colNames:['KeyVal',";
		for (int i = 0; i < columns.size(); i++) 
		{
			cnms+="'"+columns.get(i)+"'";
			if (i != columns.size() - 1) {
		        cnms = cnms + ",";
		    }
		}
		cnms+="],\n";
		
		String cmdl = "colModel:[{name: 'keyval', index: 'keyval', width: 150, align: 'center', key:true, editable:true, hidden:true},";
		for (int i1 = 0; i1 < columns.size(); i1++) {
		    cmdl = cmdl + "{name:'" + columns.get(i1) + "', index:'" + columns.get(i1) + "', align: 'center', editable: true}";
		    if (i1 != columns.size() - 1) {
		        cmdl = cmdl + ",\n";
		    }
		}
		cmdl = cmdl + "],\n";
		
		grid.append("<script>\n");
		
		grid.append("jQuery(document).ready(function(){\n"); 

		grid.append("jQuery(\"#list\").jqGrid({\n");

		grid.append("url:'crazy.jsp"+"?ID="+id+"&Cols="+columns.size()+"',\n");
		grid.append("datatype: 'json',\n");
		
		grid.append(cnms);

		grid.append(cmdl);


		grid.append("pager: \"#pager\",\n");
		grid.append("rowNum: 500,\n");
		grid.append("rowList: [500,1000, 2000, 3000,4000],\n"); 
		grid.append("sortorder: \"desc\",\n"); 
		grid.append("viewrecords: true,\n");
		grid.append("gridview: true,\n");
		grid.append("autoencode: true,\n");
		grid.append("width:1250,\n");

		//grid.append("autowidth:true,\n");
		grid.append("height:400,\n");
		grid.append("shrinkToFit:true,\n");
		grid.append("multiselect:true,\n");
		grid.append("ignoreCase:true,\n");
		grid.append("loadonce:true,\n");
		grid.append("caption:\"This is me\"\n");
		grid.append("}).navGrid('#pager',{edit:true,add:true,del:true,search:true,refresh:true}, \n" +
				"" +
		"{height:280,mtype: \"POST\",closeAfterEdit: true,reloadAfterSubmit:true, url:'crazyedit.jsp?FID="+id+"',\n" +
		
		"recreateForm:true, \n" +
		"" +
		"afterSubmit: function () {\n" +
		"" +
				"$(this).jqGrid('setGridParam', {datatype:'json'});\n" +
				"" +
					"return [true,''];\n" +
					"" +
					"}\n" +
					"" +
					"},\n" +
		"{height:280,mtype:\"POST\", closeAfterAdd:true, reloadAfterSubmit:true, url:'crazyedit.jsp?FID="+id+"', \n" +
		"" +
				"recreateForm:true, \n" +
		
				" afterSubmit: function () {\n" +
				"" +
						"$(this).jqGrid(\"setGridParam\", {datatype: 'json'});	\n" +
						"" +
						"return [true, \"\"];\n" +
						"" +
								"}" +
						"},\n" +
		
		"{height:180,mtype:\"POST\",closeAfterDel:true, url:'crazyedit.jsp?FID="+id+"',\n" +
				"" +
				"reloadAfterSubmit:true,\n" +
		"" +
		" afterSubmit: function () {\n" +
		"" +
		"$(this).jqGrid('setGridParam', {datatype:'json'}); \n" +
		"" +
		"return [true,'']; }\n " +
		"" +
		"}\n" +
		"" +
		");\n");
		
		grid.append("jQuery(\"#refresh_list\").click(function(){\n");
	    grid.append("jQuery(\"#list\").setGridParam({datatype: 'json'});\n");
	    grid.append("jQuery(\"#list\").trigger(\"reloadGrid\");\n");
	    grid.append("});\n");
	    
	    grid.append("});\n");   
		grid.append("</script>\n");
		
		return grid.toString();
	}
		
	// Print the grid with concurrency controls in place
	public String printjQgrid_ConCon(String filter_id) throws SQLException
	{
		StringBuffer grid = new StringBuffer();
		
		ArrayList<String> columns = new ArrayList<String>();
		
		String sSQL = null;
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;

			String id=filter_id;
		
			String x=new String();
		
		Connection objCon=DBConnection.getSybBPSOPS1Connection();
		
		sSQL="Some SQL Query"+String.valueOf(id)+"'"; // This query is responsible for linking to the appropriate table in the back end. I will create the grid based on this table
		
	
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		

		while (rs.next())
        {
          try
          {	
        	  x=rs.getString(2);
        	  columns.add(x);
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
		
		String cnms = "colNames:['KeyVal','Modified',";
		for (int i = 0; i < columns.size(); i++) 
		{
			cnms+="'"+columns.get(i)+"'";
			if (i != columns.size() - 1) {
		        cnms = cnms + ",";
		    }
		}
		cnms+="],\n";
		
		String cmdl = "colModel:[{name: 'keyval', index: 'keyval', width: 150, align: 'center', key:true, editable:true, hidden:true}," +
				"{name: 'mod', index: 'mod', width: 150, align: 'center', editable:true, hidden:true},";
		
		for (int i1 = 0; i1 < columns.size(); i1++) {
		    cmdl = cmdl + "{name:'" + columns.get(i1) + "', index:'" + columns.get(i1) + "', align: 'center', editable: true}";
		    if (i1 != columns.size() - 1) {
		        cmdl = cmdl + ",\n";
		    }
		}
		cmdl = cmdl + "],\n";
		
		grid.append("<script>\n");
		
		grid.append("jQuery(document).ready(function(){\n"); 

		grid.append("jQuery(\"#list\").jqGrid({\n");

		grid.append("url:'crazy.jsp"+"?ID="+id+"&Cols="+columns.size()+"',\n");
		grid.append("datatype: 'json',\n");
		
		grid.append(cnms);

		grid.append(cmdl);


		grid.append("pager: \"#pager\",\n");
		grid.append("rowNum: 500,\n");
		grid.append("rowList: [500,1000, 2000, 3000,4000],\n"); 
		grid.append("sortorder: \"desc\",\n"); 
		grid.append("viewrecords: true,\n");
		grid.append("gridview: true,\n");
		grid.append("autoencode: true,\n");
		grid.append("width:1250,\n");

		//grid.append("autowidth:true,\n");
		grid.append("height:400,\n");
		grid.append("shrinkToFit:true,\n");
		grid.append("multiselect:true,\n");
		grid.append("ignoreCase:true,\n");
		grid.append("loadonce:true,\n");
		grid.append("caption:\"This is me\",\n" +
				"" +
				"" +
		" beforeSelectRow: function(rowid, e) {\n " +
		"" +
				"if( $(\"#jqg_list_\"+rowid).attr(\"disabled\") ){\n " +
		"" +
						"return false;\n}\n  " +
		"" +
						"return true; \n },\n" +
		"" +
		
		"loadComplete: function (data) {\n" +
		"" +
					"var ids =jQuery(\"#list\").jqGrid('getDataIDs');\n" +
		"" +
					"for(var i=0;i < ids.length;i++){\n" +
		"" +
							"var rowId = ids[i];\n" +
		"" +
							"var mod = jQuery(\"#list\").jqGrid('getCell',ids[i],'mod');\n" +
		"" +
							"if(mod=='y'){\n" +
		"" +
									"jQuery(\"#jqg_list_\"+rowId).attr(\"disabled\", true);\n" +
		"" +
				"$(\"#list\").jqGrid('setRowData',ids[i],false, {weightfont:'bold',color:'silver'});\n" +
		"" +
					"}\n" +
				"}\n" +
			"}\n" +
		"" +
		"\n");
		
		grid.append("}).navGrid('#pager',{edit:true,add:true,del:true,search:true,refresh:true}, \n" +
				"" +
		"{height:280,mtype: \"POST\",closeAfterEdit: true,reloadAfterSubmit:true, url:'crazyedit.jsp?FID="+id+"',\n" +
		
		"recreateForm:true,\n" +
		"" +
		"afterShowForm: function(form){\n " +
		"" +
			"var sel_id = $(\"#list\").jqGrid('getGridParam', 'selrow');\n" +
		"" +
			"var key=$(\"#list\").jqGrid('getCell', sel_id, 'keyval');\n" +
		"" +
		"$.ajax({\n " +	//AJAX Call, lock this row as someone is editing it
		"" +
			"type: \"POST\",\n" +
		"" +
			"url: \"crazyedit.jsp\"," +
		"" +
			"data:{KEY:key,FID:"+id+"}\n" +
		"" +
		"}).done(function() {\n" +
		"" +
		"alert( \"Data Saved: \");\n" +
		"" +
			"});\n" +
		"},\n" +
		"" +
		
		"afterSubmit: function () {\n" +
			
			" var sel_id = $(\"#list\").jqGrid('getGridParam', 'selrow');\n" +
		"" +
			"var key=$(\"#list\").jqGrid('getCell', sel_id, 'keyval');\n" +
		"" +
		"$.ajax({\n" +	//AJAX Call, unlock this row as editing is done. 
		"" +
			"type: \"POST\",\n" +
		"" +
			"url: \"crazyedit.jsp\",\n" +
		"" +
			"data:{KEY:key,done:'d',FID:"+id+"}\n" +
		
		"}).done(function() {\n" +
		
		"alert( \"Data Saved: \");\n" +

		"});\n" +
		
		"$(this).jqGrid('setGridParam', {datatype:'json'});\n" +
				"" +
					"return [true,''];\n" +
					"" +
					"},\n" +
					"" +
					"onClose: function () {\n" +
					"" +
						"var sel_id = $(\"#list\").jqGrid('getGridParam', 'selrow');\n" +
					"" +
						"var key=$(\"#list\").jqGrid('getCell', sel_id, 'keyval');\n" +
					"" +
					"	 			$.ajax({\n" +	//AJAX Call, unlock this row as editing is done.
					"" +
					"		           		  type: \"POST\",\n" +
					
					"		           		  url: \"crazyedit.jsp\",\n" +
					
					"		           		  data:{KEY:key,done:'d',FID:"+id+"}\n" +
					
					"		           		}).done(function() {\n" +
					
					"		           		  alert( \"Data Saved: \");\n" +
					
					"		           		});\n" +
					"	        	}\n" +
					"" +
					"},\n" +
		"{height:280,mtype:\"POST\", closeAfterAdd:true, reloadAfterSubmit:true, url:'crazyedit.jsp?FID="+id+"', \n" +
		"" +
				"recreateForm:true, \n" +
		
				" afterSubmit: function () {\n" +
				"" +
						"$(this).jqGrid(\"setGridParam\", {datatype: 'json'});	\n" +
						"" +
						"return [true, \"\"];\n" +
						"" +
								"}\n" +
						"},\n" +
		
		"{height:180,mtype:\"POST\",closeAfterDel:true, url:'crazyedit.jsp?FID="+id+"',\n" +
				"" +
				"reloadAfterSubmit:true,\n" +
		"" +
		" afterSubmit: function () {\n" +
		"" +
		"$(this).jqGrid('setGridParam', {datatype:'json'}); \n" +
		"" +
		"return [true,'']; } \n" +
		"" +
		"}\n" +
		"" +
		");\n");
		
		grid.append("jQuery(\"#refresh_list\").click(function(){\n");
	    grid.append("jQuery(\"#list\").setGridParam({datatype: 'json'});\n");
	    grid.append("jQuery(\"#list\").trigger(\"reloadGrid\");\n");
	    grid.append("});\n");
	    
	    grid.append("});\n");   
		grid.append("</script>\n");
		
		return grid.toString();
	}
	
	public String getJSON_ConCon(String ID, String Cols) throws SQLException
	{
		String craze=new String();
		int size=Integer.parseInt(Cols);
		String sSQL = null;
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;

		Connection objCon=DBConnection.getSybBPSOPS1Connection();
		String cols=new String();
		
		for (int g=0;g<size;g++)
		{
			cols+="filter_"+(g+1);
			if (g != size-1) {
		        cols+=",";
		    }
			
		}
		
		//System.out.println("Filter is "+cols);
		
		sSQL="SELECT key_value,modified,"+cols+" FROM somewhere filter_id='"+ID+"'";
		//System.out.println("SQL is "+sSQL);
		objPreStmt = objCon.prepareStatement(sSQL);
		
		rs = objPreStmt.executeQuery();
		
		JSONObject root = new JSONObject();
		JSONArray l = new JSONArray();
		
		int p=1;
		while (rs.next())
        {
		try
          {
		
			JSONArray list = new JSONArray();
	    	JSONObject rows = new JSONObject();
			while(p<=size+2)
			{
				
				list.add(rs.getString(p));
				p++;
			}
			
			rows.put("cell",list);
	        l.add(rows); 
			p=1;
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
		
		root.put("page","");
		root.put("total","");
		root.put("records","");
		root.put("rows",l);
		
		craze=root.toString();
		//System.out.println("JSON ConCon is "+craze);
		return craze;
		
	}
	public String getJSON(String ID, String Cols) throws SQLException
	{
		
		String craze=new String();
		int size=Integer.parseInt(Cols);
		String sSQL = null;
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;

		Connection objCon=DBConnection.getSybBPSOPS1Connection();
		String cols=new String();
		
		for (int g=0;g<size;g++)
		{
			cols+="filter_"+(g+1);
			if (g != size-1) {
		        cols+=",";
		    }
			
		}
		
		//System.out.println("Filter is "+cols);
		
		sSQL="SELECT key_value,"+cols+" FROM somewhere filter_id='"+ID+"'";
		//System.out.println("SQL is "+sSQL);
		objPreStmt = objCon.prepareStatement(sSQL);
		
		rs = objPreStmt.executeQuery();
		
		JSONObject root = new JSONObject();
		JSONArray l = new JSONArray();
		
		int p=1;
		while (rs.next())
        {
		try
          {
		
			JSONArray list = new JSONArray();
	    	JSONObject rows = new JSONObject();
			while(p<=size+1)
			{
				
				list.add(rs.getString(p));
				p++;
			}
			
			rows.put("cell",list);
	        l.add(rows); 
			p=1;
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
		
		root.put("page","");
		root.put("total","");
		root.put("records","");
		root.put("rows",l);
		
		craze=root.toString();
		//System.out.println("JSON is "+craze);
		return craze;
		
	}
	
	// Add a row
	public void addDynamic(String names, String vals, String fid) throws SQLException
	{
		String[] n=names.split(",");
		
		String filters="filter_id,";
		String sSQL = null;
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;

		Connection objCon=DBConnection.getSybBPSOPS1Connection();
		int c=0;
		while(c<n.length)
		{
		
			sSQL="Some SQL Query'"+n[c];
			objPreStmt = objCon.prepareStatement(sSQL);
			//System.out.println(sSQL);
			rs = objPreStmt.executeQuery();
		
		while (rs.next())
        {
		try
          {
			filters+=rs.getString(1);
			
          } 
          catch (SQLException e) 
          {
          	// TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        
		rs.close();
		objPreStmt.close();    
		
		if(c!=(n.length-1))
		{filters+=",";}
		c++;
		}
				
		sSQL="Some SQL Query";
		//System.out.println(sSQL);
		Statement Stmt = null;
		Stmt = objCon.createStatement();
		Stmt.executeUpdate(sSQL);
		
		objCon.close();
	}
	
	//Delete a row
	public void delDynamic(String FID,String[] key) throws SQLException
	{
		String sSQL = null;
		Statement Stmt = null;
		Connection objCon=DBConnection.getSybBPSOPS1Connection();
		Stmt = objCon.createStatement();
		
		for (int i = 0; i<key.length; i++){	

			sSQL ="Some SQL Query";
		
		Stmt.executeUpdate(sSQL);
			
		}
		objCon.close();
	}
	
	//Edit a row
	public void editDynamic(String names, String vals, String fid, String key, int mod) throws SQLException
	{
		//System.out.println("Inside editDynamic");
		
		String filters=new String();
		String sSQL = null;
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;

		Connection objCon=DBConnection.getSybBPSOPS1Connection();
		
		if(mod==1)
		{
			//System.out.println("Mod is "+mod);
			String[] n=names.split(",");
			int c=0;
		while(c<n.length)
		{
		
			sSQL="Some SQL Query"+n[c];
			objPreStmt = objCon.prepareStatement(sSQL);
			//System.out.println(sSQL);
			rs = objPreStmt.executeQuery();
		
		while (rs.next())
        {
		try
          {
			filters+=rs.getString(1);
			
          } 
          catch (SQLException e) 
          {
          	// TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        
		rs.close();
		objPreStmt.close();    
		
		if(c!=(n.length-1))
		{filters+=",";}
		c++;
		}
		
		String [] val=vals.split(",");
		
		String[] filts=filters.split(",");
		
		System.out.println(filters);
		System.out.println(vals);
		
		
		for (int f=0;f<filts.length;f++)
		{
			
		sSQL="Some SQL Query";
		//System.out.println(sSQL);
		Statement Stmt = null;
		Stmt = objCon.createStatement();
		Stmt.executeUpdate(sSQL);
		}
		
			objCon.close();
	}
		
			if(mod==2)
			{
				//System.out.println("Mod is "+mod);
				sSQL="Some SQL Query";
				//System.out.println(sSQL);
				Statement Stmt = null;
				Stmt = objCon.createStatement();
				Stmt.executeUpdate(sSQL);
				objCon.close();
			}
			
			if(mod==3)
			{
				//System.out.println("Mod is "+mod);
				sSQL="Some SQL Query";
				//System.out.println(sSQL);
				Statement Stmt = null;
				Stmt = objCon.createStatement();
				Stmt.executeUpdate(sSQL);
				objCon.close();
			}
		
	}
	
	public String printHeader(String sAppId,String sAppType, String sUserId,String sRptType) throws SQLException 
	{
		StringBuffer sb=new StringBuffer();
		String sSQL = null;
		String sAppName=null;
		String sAppLink=null;
		String sUserFullName=null;
		String sGreeting=null;
		
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		sSQL="Some SQL Query";
	
		objPreStmt = objCon.prepareStatement(sSQL);
		objPreStmt.setString(1, sAppId);
		objPreStmt.setString(2, sUserId);

		rs = objPreStmt.executeQuery();

		try
		{
			while (rs.next())
			{		
				sAppName = rs.getString(1);
				sAppLink = rs.getString(2);
				sUserFullName = rs.getString(3);				
			}
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ========================================== //
		// Get Day time to set up Greeting for header //
		// ========================================== //
		
		Calendar calendar = new GregorianCalendar();   
        //String am_pm;   
		//int minute = calendar.get( Calendar.MINUTE );
		int hour = calendar.get( Calendar.HOUR_OF_DAY );   
        if( calendar.get( Calendar.AM_PM ) == 0 )
        {   
            //am_pm = "AM";   
            if(hour >=4 && hour <=11)
            {	
            	sGreeting="Good Morning";
            }
            if(hour<4)
            {	
            	sGreeting="Good Evening";
            }
        }               
        else
        {   
            //am_pm = "PM";   
            if(hour>11 && hour<=16)
            {	
            	sGreeting="Good Afternoon";
            }   
            if(hour>16)
            {	
            	sGreeting="Good Evening";
            }
        }  
               
        //String time = "Current Time : " + hour + ":" + minute + " " + am_pm;   
		
        sb.append("<table width=1260 bgcolor='000066'>\n");
		sb.append("<tr>\n");
		sb.append("<td><img src=''></td>\n");
		sb.append("<td width=1200 bgcolor='000066' align='center'><font size=5 color='white' >");
		if (sAppType == null)
		{
			sb.append(sAppName);			
		}
		else if (sAppType.equals("rpt"))
		{
			sb.append(sAppName+" Report");			
		}
		
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>\n");
		sb.append("</tr>\n");
		sb.append("</table>\n");
		sb.append("<table width=1260 bgcolor='000066'>\n");
		sb.append("<tr>\n");
		sb.append("<td width=57 ><font alt=\"\" color='white'><b>Home > </b></font></td>\n");
		sb.append("<td width=105 ><a href=\"opsdashboard.jsp\"><font color='white'><b>Reports</a> > </b></font></td>\n");
		sb.append("<td width=950 align=left><a href=\""+ sAppLink +"\"><font color='white'><b>"+ sAppName +"</a></b></font></td>\n");
		if (sRptType.equals("cal"))
		{
			sb.append("<td align='right'><a href=\"help.jsp\" onClick=\"return ContactInfoPopup(this,'ContactInfo')\"><font color='white'><b>Help</b></a></td>\n");			
		}		
		//sb.append("<td align='right'><a href=\"help.jsp\" onClick=\"return ContactInfoPopup(this,'ContactInfo')\"><font color='white'><b>Help</b></a></td>\n");
		sb.append("<td align='right'><a href=\"contactus.jsp\" onClick=\"return ContactInfoPopup(this,'ContactInfo')\"><font color='white'><b>Contact Info</b></a></td>\n");
		sb.append("</tr>\n");
		sb.append("</table>\n");
		sb.append("<table width=1260 border=0 bgcolor='#C2F2FE'><tr><td align=right ><font color='000066' size=1.5 face=\"Verdana\"><b>"+ sGreeting +",&nbsp;"+sUserFullName+"</b></font></td></tr></table>");		
		sb.append("<br>\n");        
		return sb.toString();
	} 

	public String printMainHeader(String sAppId,String sUserId) throws SQLException 
	{
		StringBuffer sb=new StringBuffer();
		String sSQL = null;
		String sAppName=null;
		String sAppLink=null;
		String sUserFullName=null;
		String sGreeting="Welcome";
		
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		sSQL="Some SQL Query";

		objPreStmt = objCon.prepareStatement(sSQL);
		objPreStmt.setString(1, sAppId);
		objPreStmt.setString(2, sUserId);
		rs = objPreStmt.executeQuery();
		
		try
		{
			while (rs.next())
			{		
				sAppName = rs.getString(1);
				sAppLink = rs.getString(2);
				sUserFullName = rs.getString(3);				
			}
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb.append("<table width=1260 bgcolor='000066'>\n");
		sb.append("<tr>\n");
		sb.append("<td><img src='' alt=\"\" align='left'></td>\n");
		sb.append("<td width=1200 bgcolor='000066' align='center'><font size=5 color='white' >");
		sb.append(sAppName);
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td>\n");
		sb.append("</tr>\n");
		sb.append("</table>\n");
		sb.append("<table width=1260 bgcolor='000066'>\n");
		sb.append("<tr>\n");
		sb.append("<td width=105 ><a href=\""+ sAppLink +"\"><font color='white'><b>Home</a></b></font></td>\n");
		sb.append("<td align='right'><a href=\"contactus.jsp\" onClick=\"return ContactInfoPopup(this,'ContactInfo')\"><font color='white'><b>Contact Info</b></a></td>\n");
		sb.append("</tr>\n");
		sb.append("</table>\n");
		sb.append("<table width=1260 border=0 bgcolor='#C2F2FE'><tr><td align=right ><font color='000066' size=1.5 face=\"Verdana\"><b>"+ sGreeting +",&nbsp;"+sUserFullName+"</b></font></td></tr></table>");		
		return sb.toString();
	} 
	
	
	public String printSafeHarborHead(String sAppId) throws SQLException 
	{
		StringBuffer sb=new StringBuffer();
		String sSQL = null;
		String sAppName=null;
		String sAppLink=null;
		String sUserFullName=null;
		String sGreeting="Welcome";
		
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		
		sSQL="Some SQL Query"+sAppId + "'";
		
		
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		
		try
		{
			while (rs.next())
			{		
				sAppName = rs.getString(1);
			}
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb.append("<title>" + sAppName + "</title>\n");
		sb.append(getJavascriptFunctions(sAppId,sAppName));
		return sb.toString();
	} 
	private String getJavascriptFunctions(String sAppId, String sAppName) throws SQLException 
	{
		StringBuffer sbjs=new StringBuffer();
		String sSQL = null;
		String sMsgBoxTxt = null;
		sbjs.append("<script type=\"text/javascript\">\n");
		sbjs.append("var isChoice = 0;\n");
		sbjs.append("function gup(turl, name){\n");
		sbjs.append("var regexS = \"[\\\\?&]\"+name+\"=([^&#]*)\";\n");
		sbjs.append("var regex = new RegExp( regexS );\n");
		sbjs.append("var tmpURL = turl;\n");
		sbjs.append("var results = regex.exec( tmpURL );\n");
		sbjs.append("if( results == null )\n");
		sbjs.append("return \"\";\n");
		sbjs.append("else\n");
		sbjs.append("return results[1];}\n\n");
		
		
		sbjs.append("function shlWin(url){\n");
		sbjs.append("WREF = window.open(url,'welcome', 'width=500,height=400,menubar=no,status=yes, location=no,toolbar=no,scrollbars=yes');\n");
		sbjs.append("if(!WREF.opener){ WREF.opener = this.window; }\n}\n");
		
		
		
		
		sbjs.append("function ValidateForm(oLink){\n");
		sbjs.append("var answer = \"\";\n");
		sbjs.append("var element = document.getElementById(oLink);\n");
		sbjs.append("var the_inner_html = element.href;\n");
		///********** Get fields
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		sSQL="Some SQL Query"+sAppId + "'";
		
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		sMsgBoxTxt = "\"Please confirm that you would like to delete the following: \\n";
		try
		{
			while (rs.next())
			{		
				sbjs.append("var " + rs.getString(1) + " = gup(the_inner_html, '" + rs.getString(1) + "' );\n");
				sMsgBoxTxt = sMsgBoxTxt + "\\n" + rs.getString(2) + ": \" + " + rs.getString(1) + " + \""; 
			}
			sbjs.append("vbMsg(" + sMsgBoxTxt + "\",'" + sAppName + "');\n");
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sbjs.append("if (isChoice == 6){\n");
		sbjs.append("return true;}\n");
		sbjs.append("else{\n");
		sbjs.append("return false;}}\n");
		return sbjs.toString();
   }

	public String printSafeHarborHeadUpd(String sAppId) throws SQLException 
	{
		StringBuffer sb=new StringBuffer();
		String sSQL = null;
		String sAppName=null;
		String sAppLink=null;
		String sUserFullName=null;
		String sGreeting="Welcome";
		
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		sSQL="";
	
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		
		try
		{
			while (rs.next())
			{		
				sAppName = rs.getString(1);
			}
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb.append("<title>Modify " + sAppName + "</title>\n");
		sb.append(getJavascriptFunctionsUpd(sAppId,sAppName));
		return sb.toString();
	} 
	private String getJavascriptFunctionsUpd(String sAppId, String sAppName) throws SQLException 
	{
		StringBuffer sbjs=new StringBuffer();
		String sSQL = null;
		String sMsgBoxTxt = null;
		ArrayList<String> arFilter_Display_Name = new ArrayList<String>();
		ArrayList<String> arFilter_Name = new ArrayList<String>();
		sbjs.append("<script type=\"text/javascript\">\n");
		sbjs.append("var isChoice = 0;\n");
		sbjs.append("function ValidateForm(){\n");
		sbjs.append("var answer = \"\";\n");
		sbjs.append("document.getElementsByName('key_value')[0].value = document.getElementsByName('key_value')[0].value.toUpperCase();\n");
		sbjs.append("document.getElementsByName('filter_1')[0].value = document.getElementsByName('filter_1')[0].value.toUpperCase();\n");
		sbjs.append("var key_value = document.getElementsByName('key_value')[0].value;\n");
		///********** Get fields
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		ResultSet rs2 = null;
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		sSQL="select distinct(field_name), field_meaning "+
	         "from safe_harbor..sh_filter_definitions d "+
		     "where filter_id='"+sAppId + "'";
		
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		try
		{
			while (rs.next())
			{
				arFilter_Name.add(rs.getString(1));
				arFilter_Display_Name.add(rs.getString(2));
				sbjs.append("var " + rs.getString(1) + " = document.getElementsByName('" + rs.getString(1) + "')[0].value;\n");
				sbjs.append("if (" + rs.getString(1) + " == ''){\n");
				sbjs.append("alert('Please enter a " + rs.getString(2) +"');\n");
				sbjs.append("return false;}\n");
			    
			}
			
			sbjs.append("if (document.getElementsByName('Action')[0].value == 'Add')\n{\n");
		    sbjs.append("txt = \"Please confirm that you would like to add the following:\\n\\n" + arFilter_Display_Name.get(0) + ": \" + " + arFilter_Name.get(0) + "\n}\n else{\n");
		    sbjs.append("txt = \"Please confirm that you would like to modify the following:\\n\\n" + arFilter_Display_Name.get(0) + ": \" + " + arFilter_Name.get(0) + "\n}\n");
		    for (int f = 1; f<arFilter_Display_Name.size(); f++)
        	{
		    	sbjs.append("txt = txt + \"\\n" +   arFilter_Display_Name.get(f) + ": \" + " + arFilter_Name.get(f) + "; \n");
        	}
			//sbjs.append("vbMsg(" + sMsgBoxTxt + "\",'" + sAppName + "');\n");
		    sbjs.append("vbMsg(txt,\"" + sAppName + "\");\n");	
		    sbjs.append("if (isChoice == 6){\n");
		   		 //alert("safe_harbor_blotter_xmlhttp.jsp?Action=" + document.getElementsByName('BLOT_Action')[0].value + "&blotter_ID=" + document.getElementsByName('BLOT_ID')[0].value + "&blotter_code=" + document.getElementsByName('BLOT_CD')[0].value + "&blotter_market_cd=" + document.getElementsByName('BLOT_Market_CD')[0].value + "&blotter_cap_cd=" + document.getElementsByName('BLOT_Cap_CD')[0].value + "&blotter_capacity=" + document.getElementsByName('BLOT_Capacity')[0].value + "&blotter_description=" + document.getElementsByName('BLOT_Description')[0].value + "&username=" + document.getElementsByName('USRNAME')[0].value);
		    sbjs.append("makeRequest(\"safe_harbor_xmlhttp.jsp?Action=\" + document.getElementsByName('Action')[0].value + \"&key_value=\" + document.getElementsByName('key_value')[0].value+ \"&filter_id=\" + document.getElementsByName('SafeHarborAppID')[0].value");
		    for (int a = 0; a<arFilter_Name.size(); a++)
        	{
		    	sbjs.append(" + \"&" + arFilter_Name.get(a) + "=\" + encodeURIComponent(document.getElementsByName('" + arFilter_Name.get(a) + "')[0].value)");
		    	
        	}
		    sbjs.append(", null)\n");
		   //		 makeRequest("safe_harbor_blotter_xmlhttp.jsp?Action=" + document.getElementsByName('BLOT_Action')[0].value + "&blotter_ID=" + document.getElementsByName('BLOT_ID')[0].value + "&blotter_code=" + document.getElementsByName('BLOT_CD')[0].value + "&blotter_market_cd=" + document.getElementsByName('BLOT_Market_CD')[0].value + "&blotter_cap_cd=" + document.getElementsByName('BLOT_Cap_CD')[0].value + "&blotter_capacity=" + document.getElementsByName('BLOT_Capacity')[0].value + "&blotter_description=" + document.getElementsByName('BLOT_Description')[0].value + "&username=" + document.getElementsByName('USRNAME')[0].value, null)
		    sbjs.append(" \nreturn false;\n}\nelse\n{\nself.close();\nreturn false;\n}\n}");
		   
		    
		    sbjs.append("function loadForm()\n{");
		    sSQL="select field_name from safe_harbor..sh_filter_definitions "+
		     "where filter_id='"+sAppId + "' and column_type = 'Select'";
		
		    objPreStmt = null;
		    objPreStmt = objCon.prepareStatement(sSQL);
		    rs2 = objPreStmt.executeQuery();
		    while (rs2.next())
			{
		    	sbjs.append(rs2.getString(1) + " = gup(window.location, \"" + rs2.getString(1) + "\")\n");
		    	//SHL_Country = gup(window.location, "SHL_Country");
		    	sbjs.append("setVals(" + rs2.getString(1) + ", \"" + rs2.getString(1) + "\")\n");
		    	//setVals(SHL_Country, "SHL_Country");
			}
		    sbjs.append("}");
		    
		    sbjs.append("function setVals(str, fieldname)\n{\n");
		    sbjs.append("sel = eval(\"document.modBlotterForm.\" + fieldname);\n");
		    sbjs.append("for (i=0; i<sel.options.length; i++) {\n");
		    sbjs.append("if (sel.options[i].value == str) {\n");
		    sbjs.append("sel.selectedIndex = i;\n");
		    sbjs.append("}\n");
		    sbjs.append("}\n");
		    sbjs.append("}\n");
		    
            objCon.close();
            rs.close();
            rs2.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sbjs.toString();
   }
	

	public String printGenericOpsHead(String sAppId) throws SQLException 
	{
		StringBuffer sb=new StringBuffer();
		String sSQL = null;
		String sAppName=null;
		String sAppLink=null;
		String sUserFullName=null;
		String sGreeting="Welcome";
		
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		
		sSQL="";
		
		
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		
		try
		{
			while (rs.next())
			{		
				sAppName = rs.getString(1);
			}
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb.append("<title>" + sAppName + "</title>\n");
		sb.append(getGenericOpsJavascriptFunctions(sAppId,sAppName));
		return sb.toString();
	} 
	private String getGenericOpsJavascriptFunctions(String sAppId, String sAppName) throws SQLException 
	{
		StringBuffer sbjs=new StringBuffer();
		String sSQL = null;
		String sMsgBoxTxt = null;
		sbjs.append("<script type=\"text/javascript\">\n");
		sbjs.append("var isChoice = 0;\n");
		sbjs.append("function gup(turl, name){\n");
		sbjs.append("var regexS = \"[\\\\?&]\"+name+\"=([^&#]*)\";\n");
		sbjs.append("var regex = new RegExp( regexS );\n");
		sbjs.append("var tmpURL = turl;\n");
		sbjs.append("var results = regex.exec( tmpURL );\n");
		sbjs.append("if( results == null )\n");
		sbjs.append("return \"\";\n");
		sbjs.append("else\n");
		sbjs.append("return results[1];}\n\n");
		
		
		sbjs.append("function shlWin(url){\n");
		sbjs.append("WREF = window.open(url,'welcome', 'width=500,height=400,menubar=no,status=yes, location=no,toolbar=no,scrollbars=yes');\n");
		sbjs.append("if(!WREF.opener){ WREF.opener = this.window; }\n}\n");
		
		
		
		
		sbjs.append("function ValidateForm(oLink){\n");
		sbjs.append("var answer = \"\";\n");
		sbjs.append("var element = document.getElementById(oLink);\n");
		sbjs.append("var the_inner_html = element.href;\n");
		///********** Get fields
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		sSQL="";
		
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		sMsgBoxTxt = "\"Please confirm that you would like to delete the following: \\n";
		try
		{
			while (rs.next())
			{		
				sbjs.append("var " + rs.getString(1) + " = gup(the_inner_html, '" + rs.getString(1) + "' );\n");
				sMsgBoxTxt = sMsgBoxTxt + "\\n" + rs.getString(2) + ": \" + " + rs.getString(1) + " + \""; 
			}
			sbjs.append("vbMsg(" + sMsgBoxTxt + "\",'" + sAppName + "');\n");
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sbjs.append("if (isChoice == 6){\n");
		sbjs.append("return true;}\n");
		sbjs.append("else{\n");
		sbjs.append("return false;}}\n");
		return sbjs.toString();
   }

	public String printGenericOpsHeadUpd(String sAppId) throws SQLException 
	{
		StringBuffer sb=new StringBuffer();
		String sSQL = null;
		String sAppName=null;
		String sAppLink=null;
		String sUserFullName=null;
		String sGreeting="Welcome";
		
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		sSQL="";
	
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		
		try
		{
			while (rs.next())
			{		
				sAppName = rs.getString(1);
			}
            objCon.close();
    		rs.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb.append("<title>Modify " + sAppName + "</title>\n");
		sb.append(getGenericOpsJavascriptFunctionsUpd(sAppId,sAppName));
		return sb.toString();
	} 
	private String getGenericOpsJavascriptFunctionsUpd(String sAppId, String sAppName) throws SQLException 
	{
		StringBuffer sbjs=new StringBuffer();
		String sSQL = null;
		String sMsgBoxTxt = null;
		ArrayList<String> arFilter_Display_Name = new ArrayList<String>();
		ArrayList<String> arFilter_Name = new ArrayList<String>();
		sbjs.append("<script type=\"text/javascript\">\n");
		sbjs.append("var isChoice = 0;\n");
		sbjs.append("function ValidateForm(){\n");
		sbjs.append("var answer = \"\";\n");
		//sbjs.append("document.getElementsByName('key_value')[0].value = document.getElementsByName('key_value')[0].value.toUpperCase();\n");
		//sbjs.append("document.getElementsByName('filter_1')[0].value = document.getElementsByName('filter_1')[0].value.toUpperCase();\n");
		sbjs.append("var key_value = document.getElementsByName('key_value')[0].value;\n");
		///********** Get fields
		PreparedStatement objPreStmt = null;
		ResultSet rs = null;	        
		ResultSet rs2 = null;
		Connection objCon = DBConnection.getSybBPSOPS1Connection();
		
		sSQL="";
		
		objPreStmt = objCon.prepareStatement(sSQL);
		rs = objPreStmt.executeQuery();
		try
		{
			while (rs.next())
			{
				arFilter_Name.add(rs.getString(1));
				arFilter_Display_Name.add(rs.getString(2));
				sbjs.append("var " + rs.getString(1) + " = document.getElementsByName('" + rs.getString(1) + "')[0].value;\n");
				sbjs.append("if (" + rs.getString(1) + " == ''){\n");
				sbjs.append("alert('Please enter a " + rs.getString(2) +"');\n");
				sbjs.append("return false;}\n");
				//  Do screen unique javascript
				if (rs.getString(3) != null)
	 			{
					String[] generic_javascript=rs.getString(3).split(";");
		    		for(int i=0;i<generic_javascript.length;i++)
		            {
		    			sbjs.append("if(" + generic_javascript[i] + "('" + rs.getString(2) + "', "  + rs.getString(1) + ") == true)\n{\n" );
		    			sbjs.append("return false;\n}\n");
		            }
	 				
	 			}
			    
			}
			
			sbjs.append("if (document.getElementsByName('Action')[0].value == 'Add')\n{\n");
		    sbjs.append("txt = \"Please confirm that you would like to add the following:\\n\\n" + arFilter_Display_Name.get(0) + ": \" + " + arFilter_Name.get(0) + "\n}\n else{\n");
		    sbjs.append("txt = \"Please confirm that you would like to modify the following:\\n\\n" + arFilter_Display_Name.get(0) + ": \" + " + arFilter_Name.get(0) + "\n}\n");
		    for (int f = 1; f<arFilter_Display_Name.size(); f++)
        	{
		    	sbjs.append("txt = txt + \"\\n" +   arFilter_Display_Name.get(f) + ": \" + " + arFilter_Name.get(f) + "; \n");
        	}
			//sbjs.append("vbMsg(" + sMsgBoxTxt + "\",'" + sAppName + "');\n");
		    sbjs.append("vbMsg(txt,\"" + sAppName + "\");\n");	
		    sbjs.append("if (isChoice == 6){\n");
		   		 //alert("safe_harbor_blotter_xmlhttp.jsp?Action=" + document.getElementsByName('BLOT_Action')[0].value + "&blotter_ID=" + document.getElementsByName('BLOT_ID')[0].value + "&blotter_code=" + document.getElementsByName('BLOT_CD')[0].value + "&blotter_market_cd=" + document.getElementsByName('BLOT_Market_CD')[0].value + "&blotter_cap_cd=" + document.getElementsByName('BLOT_Cap_CD')[0].value + "&blotter_capacity=" + document.getElementsByName('BLOT_Capacity')[0].value + "&blotter_description=" + document.getElementsByName('BLOT_Description')[0].value + "&username=" + document.getElementsByName('USRNAME')[0].value);
		    sbjs.append("makeRequest(\"generic_ops_mapping_xmlhttp.jsp?Action=\" + document.getElementsByName('Action')[0].value + \"&key_value=\" + document.getElementsByName('key_value')[0].value+ \"&filter_id=\" + document.getElementsByName('GenericOpsAppID')[0].value");
		    for (int a = 0; a<arFilter_Name.size(); a++)
        	{
		    	sbjs.append(" + \"&" + arFilter_Name.get(a) + "=\" + encodeURIComponent(document.getElementsByName('" + arFilter_Name.get(a) + "')[0].value)");
		    	
        	}
		    sbjs.append(", null)\n");
		   //		 makeRequest("safe_harbor_blotter_xmlhttp.jsp?Action=" + document.getElementsByName('BLOT_Action')[0].value + "&blotter_ID=" + document.getElementsByName('BLOT_ID')[0].value + "&blotter_code=" + document.getElementsByName('BLOT_CD')[0].value + "&blotter_market_cd=" + document.getElementsByName('BLOT_Market_CD')[0].value + "&blotter_cap_cd=" + document.getElementsByName('BLOT_Cap_CD')[0].value + "&blotter_capacity=" + document.getElementsByName('BLOT_Capacity')[0].value + "&blotter_description=" + document.getElementsByName('BLOT_Description')[0].value + "&username=" + document.getElementsByName('USRNAME')[0].value, null)
		    sbjs.append(" \nreturn false;\n}\nelse\n{\nself.close();\nreturn false;\n}\n}");
		   
		    
		    sbjs.append("function loadForm()\n{");
		    sSQL="";
		
		    objPreStmt = null;
		    objPreStmt = objCon.prepareStatement(sSQL);
		    rs2 = objPreStmt.executeQuery();
		    while (rs2.next())
			{
		    	sbjs.append(rs2.getString(1) + " = gup(window.location, \"" + rs2.getString(1) + "\")\n");
		    	//SHL_Country = gup(window.location, "SHL_Country");
		    	sbjs.append("setVals(" + rs2.getString(1) + ", \"" + rs2.getString(1) + "\")\n");
		    	//setVals(SHL_Country, "SHL_Country");
			}
		    sbjs.append("}");
		    
		    sbjs.append("function setVals(str, fieldname)\n{\n");
		    sbjs.append("sel = eval(\"document.modBlotterForm.\" + fieldname);\n");
		    sbjs.append("for (i=0; i<sel.options.length; i++) {\n");
		    sbjs.append("if (sel.options[i].value == str) {\n");
		    sbjs.append("sel.selectedIndex = i;\n");
		    sbjs.append("}\n");
		    sbjs.append("}\n");
		    sbjs.append("}\n");
		    
            objCon.close();
            rs.close();
            rs2.close();
        }
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sbjs.toString();
   }
	
 } 
