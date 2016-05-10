<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script language="javascript" src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script> 
<%@ include file="import-easyui-js.jspf"%>
<div  style="height:200px;padding:10px;">
	<script>
		$.messager.alert('权限提示','您没有权限，请联系管理员为您开通权限!','warning',function(){
			var topJq = top.jQuery;
			var tabName = topJq('#tabPanel').tabs('getSelected').panel('options').title;
			topJq('#tabPanel').tabs('close',tabName);
		});
	</script>
</div>
