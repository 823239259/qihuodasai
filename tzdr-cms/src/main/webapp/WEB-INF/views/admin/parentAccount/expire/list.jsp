<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>母账户到期须平仓方案</title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/parentAccount/expire/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<div id="tabWindow" style="height:auto;">
    <div style="padding:1px;">
        <table id="dg003" class="easyui-datagrid" width="100%" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/parentAccount/expire/listData" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="groupId" data-options="checkbox:true"></th>
				<th field="mobile" width="150">手机号 </th>
                <th field="tname" width="150">真实姓名 </th>
				<th field="programNo" width="150">方案编号</th>
				<th field="account" width="150">恒生账号</th>
				<th field="parentAccountName" width="150">所属母账号名称 </th>
				<th field="allocationDateStr" width="150">母账号配资截止日期</th>
				<th field="starttimeStr" width="150">方案开始时间 </th>
				<th field="naturalDays" width="150" sortable="true">方案配资天数</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">手机号:</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">用户姓名:</td>
	                <td><input name="tname" type="text"  /></td>
	            </tr>
	            <tr>
	                <td class="label right">恒生账号:</td>
	                <td colspan="3">
		                <input name="account" type="text" />
	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endOfPlanOpen()">终结方案</a>
	    </div>
     </div>

 </div>
</body>
</html>