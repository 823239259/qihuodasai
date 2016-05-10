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
<title>提现金额设置</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript" src="${ctx}/static/script/freediff/freediff.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:finance:drawMoneyData:view">
<!-- toolbar="#toolbar" -->
       <div id="edatagridToolbar">
	        <form id="queryForm" method="post">
		    
	        </form>
	        	<shiro:hasPermission name="sys:finance:drawMoneyData:create">  
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddIframeWin(700,300,'增加','drawMoneyData')">设置提现审核规则</a>
		     </shiro:hasPermission>
		     <shiro:hasPermission name="sys:finance:drawMoneyData:update">  
		      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openEditIframeWin(700,300,'修改','drawMoneyData')">修改提现审核规则</a>
		     </shiro:hasPermission>
		    </div>
	<table id="edatagrid" title="提现金额设置" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/drawMoneyData/listData" pagination="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="id" data-options="checkbox:true"></th>
				<th field="type" width="100">审核方式</th>
				<th field="money" width="100">提现审核金额</th>
				<th field="firstauditname" width="100">初审人</th>
				<th field="reauditname" width="100">复审人</th>
				<th field="createUser" width="100">创建人</th>
				<th field="createDate" width="100">创建时间</th>
				<th field="finalAuditname" width="100">最终修改人</th>
				<th field="finalDate" width="100">最终修改时间</th>
            </tr>
        </thead>
    </table>
    	<div id="addWin" style="padding:10px;top: 20px;"></div>
    	
    	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:finance:drawMoneyData:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>