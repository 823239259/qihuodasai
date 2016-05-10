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
<title>追加保证金列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/activity_list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<!-- toolbar="#toolbar" -->
	<shiro:hasPermission name="sys:customerService:appLeverMoney:view">
	<table id="dg003" title="追加保证金" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/userFundMoney/listData?type=17" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true,sortable:true"  ></th>
				<th field="mobile" width="150">手机号码 </th>
				<th field="tname" width="150">真实姓名</th>
				<th field="lid" width="150">方案编号</th>
				<th field="account" width="150">交易账号</th>
				<th field="aname" width="150">交易账户名</th>
				<th field="money" width="150">追加保证金金额</th>
				<th field="adtime" width="150">追加时间</th>
				
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">追加保证金时间:</td>
	                <td><input name="starttime" type="text" class="easyui-datetimebox" />
	                	至&nbsp;&nbsp;
	               <input name="endtime" type="text" class="easyui-datetimebox" />
	                &nbsp;&nbsp;&nbsp;&nbsp;
	                	<a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" 
	                	class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	                </td>
	            </tr>
	          </table>
	        </form>
	    </div>
	   </shiro:hasPermission>
	<shiro:lacksPermission name="sys:customerService:appLeverMoney:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>