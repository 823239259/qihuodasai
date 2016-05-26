<%@ page contentType="text/html; charset=UTF-8"%>
<html>
	<head>
		<title>正在登录....</title>
	</head>
	<body>
		<script type="text/javascript">
			<%
				Boolean isFrame = (Boolean)request.getAttribute("isFrame");
				Boolean isLogin = (Boolean)request.getAttribute("isLogin");
				// 登录成功
				if(isLogin){
					if(isFrame){%>
						parent.location.replace('${service}')
					<%} else{%>
						location.replace('${service}')
					<%}
				} else { %>
					alert("账号或密码错误.");
				<%}
			%>
			
			// 回调
			// ${callback}({'login':${isLogin ? '"success"': '"fails"'}, 'msg': ${isLogin ? '""': '"用户名或密码错误！"'}});
		</script>
	</body>
</html>
