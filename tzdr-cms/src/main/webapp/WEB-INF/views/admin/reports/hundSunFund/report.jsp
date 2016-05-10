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
<title>活动用户</title>
<%@include file="../../../common/import-easyui-js.jspf"%>
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
	<table id="dg003" title="恒生资金表" class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/hundSundFund/data" pagination="false"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
        
            <tr>
                <!-- <th field="id" data-options="checkbox:true"></th> -->
				<th field="hsBelongBroker" width="150">所属劵商户</th>
				<th field="uname" width="180">用户名 </th>
				<th field="realName" width="150">实名</th>
				<th field="accountName" width="150">恒生账户名</th>
				<th field="groupId" width="150">方案号</th>
				<th field="leverMoney" width="150">保证金</th>
				<th field="lever" width="100">倍数</th>
				<th field="naturalDays" width="100">天数</th>
				<th field="stockAssets" width="200">股票成交金额</th>
				<th field="stockNumber" width="200">股票成交股数</th>
				<th field="yestodayBalance" width="200">前一日账户余额</th>
				<th field="changeFund" width="150">资金划拨</th>
				<th field="commission" width="150">佣金差</th>
				<th field="transferFee" width="150">过户费差</th>
				<th field="accrual" width="200">当日累计盈亏</th>
				<th field="currentBalance" width="150">账户余额</th>
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" action="${ctx}/admin/hundSundFund/data" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	                <td class="label right">日期:</td>
	                
	                <td><input id="search_date" class="Wdate" name="search_date" type="text" onfocus="WdatePicker({maxDate:'%y-%M-{%d-1}'})"/></td>
	                <td class="label right">所属券商户:</td>
	                <td>
	                <input name="hsBelongBroker" type="text" />
	                </td>
	            </tr>
	            <tr>
	                <td class="label right">用户名:</td>
	                <td><input name="uname" type="text" /></td>
	                <td class="label right">恒生账户名:</td>
	                <td>
	                <input name="accountName" type="text" />
	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="javascript:void(0);" onclick="queryData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="$.easyui.exportExcel('dg003','queryForm')">导出</a>
		     <!--a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="changeStatusActivity()">平仓</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="resetPassword()">重置密码</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateWuserToPain()">变为普通账户</a>
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="excelImportWuser()">活动用户导入</a-->
	    </div>
</body>
<script type="text/javascript">
	function queryData(){
		   $('#dg003').datagrid('loadData', { total: 0, rows: [] });
		   $.easyui.datagridQuery('dg003','queryForm');
	}
</script>
</html>