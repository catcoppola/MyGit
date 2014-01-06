//This is the main JSP page for feeding data to our displayer JSP. It is responsible calling the appropriate back end functions , with parameters received from the displayer,
//and passing the data back to the displayer

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="header" scope="request" class="" />  
<%
response.setContentType("text/json");

String d=new String();

String genid=request.getParameter("ID");
String cols=request.getParameter("Cols");

//System.out.println("ID is "+genid);

//System.out.println("Number of columns is"+cols);
//d=header.getJSON(genid,cols);
d=header.getJSON_ConCon(genid, cols); // Calling back end function
out.println(d);

%>