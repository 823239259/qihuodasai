<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

    <%
    String url=(String)request.getAttribute("url");
    if(url!=null && !"".equals(url)){
    	 response.sendRedirect(url);
    }
   
    %>