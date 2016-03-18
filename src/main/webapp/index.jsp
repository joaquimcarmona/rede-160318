<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.util.List" %>
<%@ page import="example.Str" %>
<%@ page import="example.Red" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>"
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	String echo = "/echo";
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Network Load in JSP and Servlet - Java web application</title>
    </head>
 
    <body> 
        <div>
            <h3> Choose File to Load Network... </h3>
          
          	<form action="<%= blobstoreService.createUploadUrl(echo) %>" 
                  method="post" 
                  enctype="multipart/form-data">
                <input type="file" name="file" />                
                <input type="submit" value="load" />
            </form>          

          	<%
    			// Create the correct Ancestor key
    			String nomeRede ="RedeMP";
      		Key<Red> net = Key.create(Red.class, nomeRede);

    			// Run an ancestor query to ensure we see the most up-to-date
    			// view of the RedeMP network.
      		List<Str> stretches = ObjectifyService.ofy()
          				.load()
         				.type(Str.class) 				// We want only stretches
          				.ancestor(net)    			// Stretches in RedeMP
          				.order("stretchNumber")   // Most recent first - date is indexed.
          				.list();

    			pageContext.setAttribute("display", 
                                           Integer.toString(stretches.size()));
				 %>
					<p>List has ${fn:escapeXml(display)} entries.</p>		
          		
				<%
               
				if (stretches.isEmpty()) {
				%>
					<p>Network RedeMP has no stretches.</p>
				<%
    			} else {
				%>
					<p>Stretches in RedeMP:</p>
				<%
      			// Look at all of our greetings
        			for (Str stretch : stretches) {
            		pageContext.setAttribute("display", 
                  	StringUtils.left(
                     	Integer.toString(stretch.getStretchNumber(stretch))+
                                           "    " + stretch.getStationA(stretch)),
                        30);
            %>
						<p>${fn:escapeXml(display)}</p>
				<%
        			}
    			}
				%>
    
      </div>
      
    </body>
</html>