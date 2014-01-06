//This is the main JSP page for handling the add, delete and edit requests. It is responsible calling the appropriate back end functions , with parameters received from the displayer,
//and passing the data back to the displayer. This JSP receives parameters through AJAX calls

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = " java.io.*,java.util.*"%>
    <jsp:useBean id="header" scope="request" class="" /> 
<%

 String op=request.getParameter("oper");
 String keyval=request.getParameter("KEY");
 String f=request.getParameter("FID");

 if(op==null)
 {
	// System.out.println("op is null so AJAX");
	 
 	String done=request.getParameter("done");
 	if(done!=null)
 	{
 		//System.out.println("AfterSubmit form");
 		header.editDynamic(null, null, f,keyval,3);
 	}
 	
 	else
 	{
 		//System.out.println("AfterShow form");
 		header.editDynamic(null, null, f,keyval,2);

 	}
 	
 }
 if(op.equalsIgnoreCase("add"))
 {
	 Enumeration pNames = request.getParameterNames();
	
	 Map<String, String> map = new HashMap<String, String>();
	 
	 while(pNames.hasMoreElements()) {
	   String paramName = (String)pNames.nextElement();
	   //System.out.print("Name="+paramName+" ");
	   String paramValue = request.getParameter(paramName);
	   //System.out.println("Value="+paramValue);
	   map.put(paramName, paramValue);
	  }
	 String fnames=new String();
	 
	 String fvals=new String();
	 
	 String fid=map.get("FID");
	 fvals+="'"+fid+"',";
	 
	 map.remove("id");
	 map.remove("oper");
	 map.remove("keyval");
	 map.remove("mod");
	 map.remove("FID");
	 
	 int i=1;
	 for (Map.Entry<String, String> entry : map.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();

		    fnames+="'"+key+"'";
		    
		    fvals+="'"+value+"'";
		    
		    if(i!=map.size())
		    {
		    	fnames+=",";	
		    	fvals+=",";
		    }
		    i++;
		  }
	 //System.out.println("Filters =>"+fnames);
	 //System.out.println("Values =>"+fvals);
	 header.addDynamic(fnames, fvals, fid);
 }
 
 if(op.equalsIgnoreCase("del"))
{
	    String keys=request.getParameter("id");
		
		String[] key=keys.split(",");
		
		header.delDynamic(request.getParameter("FID"),key);
}
	
 if(op.equalsIgnoreCase("edit"))
 { 
	
	 Enumeration pNames = request.getParameterNames();
	
	 Map<String, String> map = new HashMap<String, String>();
	 
	 while(pNames.hasMoreElements()) {
	   String paramName = (String)pNames.nextElement();
	   //System.out.print("Name="+paramName+" ");
	   String paramValue = request.getParameter(paramName);
	   //System.out.println("Value="+paramValue);
	   map.put(paramName, paramValue);
	  }
	 
	 String fnames=new String();
	 
	 String fvals=new String();
	 
	 String k=map.get("id");
	 String fid=map.get("FID");
	 
	 map.remove("id");
	 map.remove("oper");
	 map.remove("keyval");
	 map.remove("mod");
	 map.remove("FID");
	 
	 int i=1;
	 for (Map.Entry<String, String> entry : map.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
	
		    fnames+="'"+key+"'";
		    fvals+="'"+value+"'";
		    
		    if(i!=map.size())
		    {
		    	fnames+=",";	
		    	fvals+=",";
		    }
		    i++;
		  }
	 //System.out.println("Filters =>"+fnames);
	 //System.out.println("Values =>"+fvals);
 	header.editDynamic(fnames, fvals, fid,k,1);
 }
 %>