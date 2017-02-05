<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.*" %>
<%@page import="org.springframework.validation.ObjectError" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
 This is Welcome JSP page. <br> 
 
  <%
 List<ObjectError> list =(List<ObjectError>)request.getAttribute("errors");
 for(ObjectError o:list){
	out.println(o.getDefaultMessage()); 
 }
 %>
</body>
</html>