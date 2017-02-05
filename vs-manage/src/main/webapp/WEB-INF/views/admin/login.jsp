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
		<%@include file="../common/import-fileupload-js.jspf"%>
		<style type="text/css">
			.login{margin-top: 13%;margin-left: 30%;}
			#loginForm table {padding: 8px;margin-left: 20%;}
			#loginForm table tr{height: 30px;}
			#loginForm div{text-align: center;padding-top: 6px;}
			#loginForm div p{color: red;margin-top: 2px;}
		</style>
	</head>
	<body>
			<div id="loginPanel"  cls="login" class="easyui-panel" style="width:500px;height:187px;padding:10px;"
					data-options="title:'维胜',collapsible:false,closable:false">
				<form method="post" id="loginForm">
					<table>
						<tr>
							<td>用户名:</td>
							<td><input class="easyui-validatebox" name="username"  style="width: 170px;" data-options="required:true"></td>
						</tr>
						<tr>
							<td>密   码:</td>
							<td><input class="easyui-validatebox" type="password" style="width: 170px;" name="password" data-options="required:true,validType:'safepass'"></td>
						</tr>
					</table>
					<div>
					<a href="javascript:void(0)" id="login" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="login()">登录</a>
					<c:if test="${not empty error}">
									<p>${error.message}</p>
					</c:if>
					</div>
				</form>
			</div>
			 <input type="hidden" ENCTYPE="multipart/form-data" name="files[]" id="fileupload_input" />
	</body>
	
	<script type="text/javascript">	
	// 回车事件
	$("#loginForm").on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$("#login").trigger("click");
			return false;
		}
	});
	   /*  $("#login").click(function(){
	    	if ($("#loginForm").form('validate') == false) {
				return;
			}
	    	$("#loginForm").submit();
	    }); */
		
	   /**
	   $("#fileupload_input").fileupload({
	        url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
	        formData:{dir:"testUplaod",fileType:'img',limitSize:'3'},//如果需要额外添加参数可以在这里添加
	        dataType: 'json', //返回值类型 一般设置为json
	        done:function(e,result){
	        	result = result.result;
	        	 alert(result.name);
	        	 
	        	result = result.result;
	        	var url = result.url;
	        	var errorCode = result.errorCode;
	        	alert(errorCode+":"+url);
	        	var deleteURL = result.deleteUrl; 
	        	//alert(url);
	        	//alert(url);
	        }
	    })*/
	</script>
</html>