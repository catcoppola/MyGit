//This is the main JSP page for feeding data to our displayer JSP. It is responsible calling the appropriate back end functions , with parameters received from the displayer,
//and passing the data back to the displayer

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.io.*,java.util.*, org.json.simple.*,java.text.DateFormat,java.util.Date,java.text.SimpleDateFormat" %>
<jsp:useBean id="eucv" scope="request" class="/*Import the back end class here*/" />
	<%
	
		response.setContentType("text/json");
	 String date = request.getParameter("ctlSelectedDate"); 
   
      String aAccBallist=null;
        
		  if (date ==null || "null".equals(date))
		  {
			  DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			  Date dt = new Date();
			  date=String.valueOf(dateFormat.format(dt));
			 
			  aAccBallist=eucv.displayABTGrid(date);    // Calling back end function  
		  }
		  else
		  {
			  aAccBallist=eucv.displayABTGrid(date);     // Calling back end function 
		  }
		
		  out.println(aAccBallist);
					
%>
