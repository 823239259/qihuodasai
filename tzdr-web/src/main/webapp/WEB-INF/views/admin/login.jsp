<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="Content-Language" content="zh-CN"/> 
		<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>登录</title>
		<%@include file="../common/import-easyui-js.jspf"%>
		<style type="text/css">
			.login{margin-top: 13%;margin-left: 30%;}
			#loginForm table {padding: 10px;margin-left: 20%;}
			#loginForm table tr{height: 30px;}
			#loginForm div{text-align: center;border-top:1px dashed #777;padding:14px;}
		</style>
	</head>
	<body>
			<div id="loginPanel"  cls="login" class="easyui-panel" style="width:500px;height:200px;padding:10px;"
					data-options="title:'维胜',collapsible:false,closable:false">
				<form method="post" id="loginForm">
					<table>
						<tr>
							<td>Name:</td>
							<td><input class="easyui-validatebox" name="name" data-options="required:true,missingMessage:'不能为空',validType:['length[8,23]','CHS'],invalidMessage:'请输入8-23个字符，并且全是中文'"></td>
						</tr>
						<tr>
							<td>password:</td>
							<td><input class="easyui-validatebox" data-options="required:true,validType:'length[5,12]'"></td>
						</tr>
					</table>
					<div><a href="javascript:void(0)" id="login" class="easyui-linkbutton" data-options="iconCls:'icon-save'">登录</a></div>
				</form>
			</div>
			<%@ include file="../common/dsp.jsp"%>
	</body>
	<script type="text/javascript">
	    $("#login").click(function(){
	    	//return $("#loginForm").form('validate'); 
	    	ajaxPost({
				url: basepath+"login/main",
				cache: false,
				async:false,
				data: {"loginName": "jsdakkaka"},
				success: function(data){
					alert(data.message);
				}
				,error: function(){
					alert("xx");
				}
			}); 
	    	
	    	
	    });
		
	</script>
</html>