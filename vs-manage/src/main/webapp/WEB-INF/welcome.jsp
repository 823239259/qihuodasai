<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>
</head>
<body>
 This is Welcome JSP page. <br> 
<shiro:hasPermission name="showcase:test:create">  
    测试[<shiro:principal/>]权限:test:create<br/>
</shiro:hasPermission>

</body>
</html>