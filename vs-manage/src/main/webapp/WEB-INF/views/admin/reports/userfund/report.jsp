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
<title>恒生资金表-报表</title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<%-- <script type="text/javascript" src="${ctx}/static/script/filejs/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload.js"></script>
<script type="text/javascript" src="${ctx}/static/script/filejs/jquery.fileupload-ui.js"></script> --%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js?v=20150625"></script>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js?v=20150625"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js?v=20150625"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/activity_list.js?v=20150625"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css?v=20150625">
</head>
<body>

	<shiro:hasPermission name="sys:accountant:userFund:view">
<!-- toolbar="#toolbar" -->
	<table id="dg003"  class="easyui-datagrid" width="100%" style="height:auto;"
             url="${ctx}/admin/userFund/data" pagination="true" showFooter="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>      
            <tr>
				<th field="mobile" width="173">用户名</th>
				<th field="realName" width="127">真实姓名 </th>
				<th field="cardCapitalMargin" width="183">保证金(卡劵)</th>
				<th field="balanceCapitalMargin" width="183">保证金(余额)</th>
				<th field="amountCapital" width="150">配资金额</th>
				<th field="lastdayBalance" width="156">前一日余额</th>
				<th field="incomeRecharge" width="155">收入(充值)</th>
				<th field="incomeRebate" width="155">收入(返利)</th>
				<th field="incomeOther" width="155">收入(其它)</th>
				<th field="profit" width="138">实现盈亏</th>
				<th field="managementFee" width="120">管理费</th>
				<th field="profitMoney" width="120">盈利分成</th>
				<th field="revokeManagerMoney" width="210">配资管理费撤回</th>
				<th field="interestFee" width="150">应收利息</th>
				<th field="revokeInterest" width="210">配资利息撤回</th>
				<th field="deductionFee" width="150">抵扣金额</th>
				<th field="actualFee" width="150">实际利息</th>
				<th field="drawFee" width="150">提现成功</th>
				<th field="drawingFee" width="156">提现处理中</th>
				<th field="coverMoney" width="156">补仓欠费</th>
				<th field="allMoney" width="156">账户总资产</th>
				<th field="platBalance" width="180">平台余额</th>
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" action="${ctx}/admin/userFund/data" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	            <tr>
	            <input type="hidden" name="first" value="1">
	                <td class="label right">起止日期:<span class="req">*</span></td>
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
	                <td class="label right">用户名:</td>
	                <td><input name="mobile" type="text" /></td>
	                <td class="label right">实名:</td>
	                <td>
	                <input name="realName" type="text" />
	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td class="label" colspan="3"><a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
	            </tr>
	          </table>
	        </form>
	        <shiro:hasPermission name="sys:accountant:userFund:export"> 
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.easyui.exportExcel('dg003','queryForm')">导出EXCEL</a>
	    	</shiro:hasPermission>
	    </div>
	   </shiro:hasPermission>
	<shiro:lacksPermission name="sys:accountant:userFund:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>