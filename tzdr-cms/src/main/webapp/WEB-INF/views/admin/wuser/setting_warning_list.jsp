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
<title>代理商列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/setting_warning_list.js"></script>
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<shiro:hasPermission name="sys:riskmanager:setWaring:view">
<div id="tabWindow" class="easyui-tabs" style="height:auto;">

   <div title="未设置预警值" id="NotSetting" style="padding:1px;">
        <table id="dg002" class="easyui-datagrid"
        toolbar="#dg002Toolbar" url="${ctx}/admin/setWaring/notSettingWarning" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
                <th field="groupId" data-options="checkbox:true"></th>
				<th field="account" width="150">恒生账号</th>
				<th field="leverMoney" width="150">配资保证金(元)</th>
				<th field="money" width="150">配资金额(元)</th>
				<th field="totalLeverMoney" width="150">总操盘资金(元) </th>
				<th field="warning" width="150">亏损补仓线(元) </th>
				<th field="open" width="150">亏损平仓线(元)</th>
				<th field="addtimeStr" width="150">配资创建时间</th>
				<th field="starttimeStr" width="150">方案生效日期</th>
            </tr>
       </thead>
    </table>
	    <div id="dg002Toolbar">
	        <form id="queryForm2" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td style="width: 200px;" class="label right">恒生账号:</td>
	                <td><input name="account" type="text" /></td>
	                <td style="width: 200px;" class="label right">开户类型:</td>
	                <td>
	                <select id="type" name="type" class="easyui-combobox"  style="width: 100px;">
	                <option value="1">钱江版</option>
	                <option value="2">涌金版</option>
	                </select>
	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg002','queryForm2')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="settingWarning()">已设置</a>
	    </div>
    </div>
    
         <!--  data-options="queryParams:Check.loadFormData($('#queryForm'))"  -->
    <div title="已设置预警值" id="proxy" style="padding:1px;">
        <table id="dg003" class="easyui-datagrid" 
        toolbar="#dg003Toolbar" url="${ctx}/admin/setWaring/settingWarning" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true"> 
        <thead>
            <tr>
				<th field="account" width="150">恒生账号</th>
				<th field="leverMoney" width="150">配资保证金(元)</th>
				<th field="money" width="150">配资金额(元)</th>
				<th field="totalLeverMoney" width="150">总操盘资金(元) </th>
				<th field="warning" width="150">亏损补仓线(元) </th>
				<th field="open" width="150">亏损平仓线(元)</th>
				<th field="warningProcessTimeStr" width="150">处理时间</th>
				<th field="addtimeStr" width="150">配资创建时间</th>
            </tr>
       </thead>
    </table>
	    <div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td style="width: 200px;" class="label right">恒生账号:</td>
	                <td><input name="account" type="text" /></td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	    </div>
    </div>
     
</div>


</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:setWaring:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>