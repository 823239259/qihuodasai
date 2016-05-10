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
<title>登录</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js"></script>
<script type="text/javascript" src="${ctx}/static/script/userTrade/coverAuditList.js?version=20150625"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript">
function auditStatus(value,rowData,rowIndex) {
    if (value == 1){
    	return "通过"; 	
    }else if (value == 2){
    	return "未通过"; 
    }  
}
function timeConvert(value,rowData,rowIndex) {
    if (value != null && value != ''){
    	return getFormatDateByLong(value,'yyyy-MM-dd hh:mm:ss')
    }else if (value == 2){
    	return ""; 
    }  
} 
</script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:coveraudit:view">
	<div id="tabWindow" class="easyui-tabs" style="height:auto;">
		  <div title="待审核列表" style="padding:1px;">
				<div id="tb" style="padding: 5px; height: auto">
					<div>
						<form id="searchForm" method="post">
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
									 <td class="label right">
									 	<span>手机号： </span>
									 </td>
									 <td>
									 	<input class="easyui-validatebox" id="mobile" name="search_LIKE_mobile"> 
									 </td>
									<td class="label right">
										<span>用户名称：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="tname" name="search_LIKE_tname">
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span>恒生帐号名：</span>
									</td>
									<td >
										<input class="easyui-validatebox" id="accountName" name="search_LIKE_accountName">
									</td>
									<td class="label right">
										<span>恒生帐号：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="account" name="search_LIKE_account">
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span></span>
									</td>
									<td colspan="3">
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
									</td>
								</tr>
							</table>	
						</form>	
					</div>
					<div style="margin-bottom: 5px;margin-top: 10px;">
						<shiro:hasPermission name="sys:riskmanager:coveraudit:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="coverAudit.audit(1)">审核通过</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:coveraudit:audit"> 
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="coverAudit.audit(2)">审核不通过</a>
						</shiro:hasPermission>
					</div>
				</div>
			<table id="edatagrid"></table>
		 </div>
	     <div title="已审核记录" style="padding:1px;">
     			<div id="tb" style="padding: 5px; height: auto">
					<div>
						<form id="queryForm" method="post">
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td class="label right">
										<span>手机号： </span>
									</td>
									<td>
									 	<input class="easyui-validatebox" id="mobile" name="mobile"> 
									</td>
									<td class="label right">
										<span>用户姓名：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="tname" name="tname">
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span>恒生账户名：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="accountName" name="accountName">
									</td>
									<td class="label right">
										<span>恒生账户：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="account" name="account">
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span>审核状态：</span>
									</td>
									<td>
										<select id="status" class="easyui-combobox" name="status" style="width:100px;height:27px;">
										    <option value="">请选择</option>
										    <option value="1">通过</option>
										    <option  value="2">未通过</option>
										</select>
									</td>
									<td class="label right">
										<span></span>
									</td>
									<td >
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="$.easyui.datagridQuery('datagrid2','queryForm')">查询</a>
									</td>
								</tr>
							</table>	
						</form>	
					</div>
				</div>
				<div>
		        <table id="datagrid2" class="easyui-datagrid" width="100%" toolbar="#dg002Toolbar"
		             url="${ctx}/admin/coveraudit/auditListdata" pagination="true"
		            rownumbers="true" fitColumns="true" singleSelect="true">
		        <thead>
		            <tr>
		                <th field="id" data-options="checkbox:true"></th>
						<th field="accountName" width="150">恒生账户名 </th>
						<th field="account" width="150">恒生账号</th>
						<th field="mobile" width="150">手机号码</th>
						<th field="tname" width="150">用户姓名</th>
						<th field="coverMoney" width="150">补仓金额</th>
						<th field="status"  formatter="auditStatus" width="150">审核状态</th>
						<th field="longCtime" formatter="timeConvert" width="150">提交时间</th>
						<th field="upuName" width="150">审核人</th>
						<th field="longUptime" formatter="timeConvert"  width="150">审核时间</th>
		            </tr>
		        </thead>
		    </table>
		    </div>
		    <div id="dg002Toolbar">
		    
		    </div>
   		</div>
	</div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:coveraudit:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>