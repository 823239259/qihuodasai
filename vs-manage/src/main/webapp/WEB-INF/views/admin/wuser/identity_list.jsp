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
<title>身份验证明细-报表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/activity_list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<!-- toolbar="#toolbar" -->
	<table id="dg003"  class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/identity/data" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>      
            <tr>
				<th field="idCard" width="150">身份证号码</th>
				<th field="name" width="120">姓名 </th>
				<th field="sex" width="150">性别</th>
				<th field="birthday" width="150">出生日期</th>
				<th field="result" width="150">认证结果</th>
				<th field="createTimeString" width="150">认证时间</th>
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	            <input type="hidden" name="first" value="1">
	                <td class="label right">认证时间:<span class="req">*</span></td>
	                <td>
	                    <table class="many">
	                        <tr>
	                            <td><input class="easyui-datebox" name="starttimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datebox" name="starttimeStr_end"></td>
	                        </tr>
	                    </table>
	                </td>
	                <td colspan="2">&nbsp;</td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.easyui.exportExcel('dg003','queryForm')">导出EXCEL</a>
	    </div>
</body>
</html>