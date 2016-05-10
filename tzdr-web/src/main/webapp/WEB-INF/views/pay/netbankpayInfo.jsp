<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

    <%
    String url=(String)request.getAttribute("url");
    String message=(String)request.getAttribute("message");
  
    if("".equals(message)){
    	out.print("url----------------->"+url);
    	 response.sendRedirect(url);
    }else{
    	out.print("message"+message);
    }
   
    %>