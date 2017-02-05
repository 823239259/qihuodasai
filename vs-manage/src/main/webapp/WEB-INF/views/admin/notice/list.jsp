<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/notice/list.js?version=20160114"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:operationalConfig:notice:view">
	<div id="noticeTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="维胜公告" data-options="tools:'#p-tools'" style="padding:20px;">
	<div id="tb" style="padding: 5px; height: auto">
		<shiro:hasPermission name="sys:operationalConfig:notice:update">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit"
			onclick="noticetest.openEditIframeWin(600,400,'公告内容','notice','noticeTab')">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-no" 
			onclick="option.stop()">停用</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" 
			onclick="option.open()">启用</a> 
		</shiro:hasPermission>
	</div>
	<table id="edatagrid" style="margin-top: 5px;"></table>
		</div>
		<!-- 配股宝公告 -->
				<div title="配股宝公告" data-options="tools:'#p-tools'" style="padding:20px;">
	<div id="tb_pgb" style="padding: 5px; height: auto">
		<shiro:hasPermission name="sys:operationalConfig:notice:update">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-edit"
			onclick="noticetest.openEditIframeWin(600,400,'公告内容','notice','noticeTab')">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-no" 
			onclick="option.stop()">停用</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-ok" 
			onclick="option.open()">启用</a> 
		</shiro:hasPermission>
	</div>
	<table id="edatagrid_pgb" style="margin-top: 5px;"></table>
		</div>
		
	</div>
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	</shiro:hasPermission>
	<!-- window -->
	<shiro:lacksPermission name="sys:operationalConfig:notice:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>

<script type="text/javascript">

var noticetest={
/**
 * 通用弹出iframe修改框
 * @param width  弹出框宽度
 * @param height 高度
 * @param title 名称
 * @param modelName  模块名称  如：user、org、role。。。。
 * @param TabsId easyui-tabs的id
 */
openEditIframeWin:function(width,height,title,modelName,TabsId){
	
	var rowData=$('#edatagrid').datagrid('getSelected');
	var $title=$("#"+TabsId).tabs('getSelected').panel('options').title;

			if($title.indexOf("配股宝")>-1){
				rowData=$('#edatagrid_pgb').datagrid('getSelected');
			}
	if (null == rowData){
		eyWindow.walert("修改提示",'请选择要修改的行', 'info');
		return;
	}
	
	var maximizableValue=false;
	if ("config/news"==modelName){ 
		 maximizableValue=true;
	}
	
	var html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=edit&type=4&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
	if($title.indexOf("配股宝")>-1){
		html = '<iframe scrolling="yes" frameborder="0"  src="'+basepath+'admin/'+modelName+'/edit?fromType=edit&type=8&id='+rowData.id+'" style="width:100%;height:100%;"></iframe>';
	}
	$('#addWin').window({collapsible:false,minimizable:false,maximizable:maximizableValue,width:width,height:height,title:title,loadingMessage:'正在加载,请等待......',iconCls:'icon-edit',closed:true,modal:true,content:html});
	$('#addWin').window('open');
	
}
}
</script>
</html>