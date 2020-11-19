<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import ="utils.ErrorPage"

    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error page</title>
<script src="resources/error-script.js"></script>
<link rel="stylesheet" href="resources/error-style.css">
</head>
<body onload="displayError()">
<% 
//Obatain needed data from the request 
int errorCode = response.getStatus();
String errorMsg = (String) request.getAttribute("errorMsg");
String htmlErrorElem = "<div class=\"error-desc\" data-text=\""; 
htmlErrorElem += ErrorPage.DEFAULT_MSG + ErrorPage.SEPARATOR + "ERROR " + errorCode + "   REF: " + errorMsg + "\">";
out.println(htmlErrorElem);
%>
    <spaguetti>
    <fork></fork>
    <meat></meat>
    <pasta></pasta>
    <plate></plate>
  </spaguetti>

	<a class="home-button" href="./home.jsp" >Go Back Home </a>
</body>
</html>