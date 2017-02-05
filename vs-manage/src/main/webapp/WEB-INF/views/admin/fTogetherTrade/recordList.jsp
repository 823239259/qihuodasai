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
<title>期货合买报表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js?v=20150625"></script>

<script type="text/javascript" src="${ctx}/static/script/wuser/public.js?v=20150625"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/end_list.js?v=20150625"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/activity_list.js?v=20150625"></script>
<script type="text/javascript" src="${ctx}/static/script/fTogetherTrade/record.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css?v=20150625">
</head>
<body>

	<shiro:hasPermission name="sys:accountant:togetherRecord:view">
	<table id="dg003"  class="easyui-datagrid" width="100%" style="height:500px;"
             url="${ctx}/admin/togetherRecord/getData" pagination="true" showFooter="true"
             toolbar="#dg003Toolbar"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>      
            <tr>
				<th field="tradeNo" width="150">方案号</th>
				<th field="tradeName" width="127">方案名称 </th>
				<th field="userName" width="183">姓名</th>
				<th field="mobile" width="183">手机</th>
				<th field="copies" width="120">份数</th>
				<th field="payMoney" width="156">支付本金</th>
				<th field="poundage" width="155">手续费</th>
				<th field="profitLossPointStr" width="155">盈亏点数</th>
				<th field="achieveProfitLossStr" width="138">实现盈亏</th>
				<th field="expectSettlementMoney" width="150">预计结算金额</th>
				<th field="actualSettlementMoney" width="150">实际结算金额</th>
				<th field="settlementTime" width="210" sortable="true">结算时间</th>
		
            </tr>
        </thead>
    </table>
    <div id="dg003Toolbar">
	        <form id="queryForm" action="${ctx}/admin/togetherRecord/getData" method="post">
		     <table border="0"  style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
	          
	            <tr>
	               
	                <td class="label right">期货品种:</td>
	                <td>
	                <input class="easyui-combobox" id="species"  name="species" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=FtogetherTradeType',
										valueField:'valueField',
										panelHeight:100,
										required:true,
										textField:'textField'">
	                </td>
	                
	                 <td class="label right">手机号码:</td>
	                <td><input name="mobile" type="text" /></td>
	            </tr>
	            
	              <tr>
	          
	                <td class="label right">结算日期:<span class="req">*</span></td>
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
	        <shiro:hasPermission name="sys:accountant:togetherRecord:export"> 
		     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.easyui.exportExcel('dg003','queryForm')">导出EXCEL</a>
	    	</shiro:hasPermission>
	    </div>
	   </shiro:hasPermission>
	<shiro:lacksPermission name="sys:accountant:togetherRecord:view">
			<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>