//This is the main JSP page for displaying our grid. It is responsible for displaying the data it receives from the feeder JSP while implementing the various buttons (functions)
//necessary

 <%@ page language="java" import="java.sql.*,java.util.ArrayList,/*Import the back end class here*/" %>   
    <jsp:useBean id="header" scope="request" class="" /> 
    <jsp:useBean id="footer" scope="request" class="" />
    <jsp:useBean id="ft" scope="request" class="/*Import the back end class here*/" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    
<html>  
  <head>
  <meta charset="utf-8" />
    <title>ABTGrid</title>
    <link href="./css/opsdashboard.css" rel="stylesheet" type="text/css" />
    <table class="ds_box" cellpadding="0" cellspacing="0" id="ds_conclass" style="display: none;">
      <tr>
        <td id="ds_calclass"></td>
      </tr>
    </table>

  <jsp:useBean id="access" scope="request" class="com.rbccm.common.util.GetAccess" />
    
    	<script src="./scripts/common.js" type="text/javascript"></script>
    	<script src="./scripts/jquery-1.10.1.js"  type="text/javascript"></script>
        <script src="./scripts/grid.locale-en.js"  type="text/javascript"></script> 
        <script src="./scripts/jquery.jqGrid.min.js" type="text/javascript"></script>
      
        <link href="./css/redmond/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css"/>
        <link href="./css/ui.jqgrid.css" rel="stylesheet" type="text/css" /> 
       
      <style type="text/css">
        html, body { font-size: 100%; }
        .ui-datepicker {
            font-size: 11px;
            
        }
    </style>
    
    <script>
      function fUserAccess()
    {
      <% 
      String userLoginId = request.getHeader("user");
      String sAppAccess = access.getAppAccess(); 
      if (sAppAccess.equals("NA") ) 
      {      
        response.sendRedirect("");    
      }
      %>
    } 

    </script>
    
  <script>

  jQuery(document).ready(function(){ 

  jQuery("#list").jqGrid({

	  datatype: 'json',
		
url:'adpfidfeeder.jsp?ctlSelectedDate=<%= request.getParameter("ctlSelectedDate")%>', // Call the feeder JSP with appropriate parameters

colNames: ['Business Date', 'Book ID', 'Account ', 'Symbol', 'ADP Position', 'FID Position', 'Difference', 'Age'],
  
colModel: [
             
{name: 'fdate', index: 'fdate', width: 100, sorttype: 'date', align: 'center',datefmt: 'Y-m-d',
	search:true, stype:'text'},
{name: 'book', index: 'book', width: 300, sorttype: 'text', align: 'center',
	search:true, stype:'text'},
{name: 'account', index: 'account', width: 100, align: 'center', sorttype: 'float',key:true,
	search:true, stype:'text'},
{name: 'symbol', index: 'symbol', width: 100, align: 'center', sorttype: 'text',
	 search:true, stype:'text'},
{name: 'adp', index: 'adp', width: 100, align: 'center', sorttype: 'float',
	search:true, stype:'text'},
{name: 'fid', index: 'fid', width: 100, align: 'center', sorttype: 'float',
	search:true, stype:'text'},
{name: 'diff', index: 'diff', width: 100, align: 'center', sorttype: 'float',
	search:true, stype:'text'},
{name: 'age', index: 'age', width: 100, align: 'center', sorttype: 'float',
	search:true, stype:'text'}
  ],

  pager: "#pager",		//Identifying the navigation bar
  rowNum: 500,			// how many rows to display on page 1
  rowList: [500,1000, 2000, 3000,4000],	//values for the dropdown which specifies how many rows to show 
  sortorder: "desc", //the order of sorting by default
  viewrecords: true, // displays total number of rows
  gridview: true,
  autoencode: true,
  height:400, //height of the grid
  multiselect:true,	// checkboxes before each row
  multiboxonly: true,
  loadonce:true, //for client side sorting, searching and pagination
  caption:"This is me" // caption for the grid header
  
  }).navGrid('#pager',{edit:false,add:false,del:false,search:true,refresh:true});
  
  	  jQuery("#refresh_list").click(function(){
      jQuery("#list").setGridParam({datatype: 'json'});
      jQuery("#list").trigger("reloadGrid");
  });
}); 
  
         </script>
  </head>  
  
  <body text='000066' align='center' onload="fUserSelection('<%= request.getParameter("ctlSelectedDate") %>')" style ="vertical-align : top; margin-top : 0px; margin-left : 0px; margin-right: 0px" bgcolor='#EFFBFB'> 
   
    <% 
        String output_Header =header.printHeader("116","rpt",userLoginId,"");
        out.print(output_Header);        
    %>   
    
    <!
    -------------------------------
    -- Display Calendar
    -------------------------------->

    <center>
      <form method= "post" >  
        <table>
          <tr>
            <td nowrap><font color='blue'><b>Select Date:</b></font></td>
            <td>
              <input onclick="fDisplayCalendar(this);" readonly="readonly" id="ctlSelectedDate" name="ctlSelectedDate" style="cursor: text" size=12 />
            </td>
            <td >
              <input type="image" value="go" align=right src="./images/btn_display_o.gif" border=0 type="submit" onclick="setFocus()" value="Set focus" >
              <input type="hidden" name="GO" value="go">
              <input type="hidden" name="date" >
              <input type="hidden" name="month" >
              <input type="hidden" name="year" >
            </td>   
          </tr>
        </table> 
      </form>
   
    <br>
     <%String sBsnsDate = request.getParameter("ctlSelectedDate"); %>
    
    <table id="list" class="scroll"></table> 
	<div id="pager" class="scroll"></div> 

 	</center>
    <% 
        String sOutputFooter =footer.printFooter();
        out.print(sOutputFooter);        
    %>    
  </body> 
</html> 