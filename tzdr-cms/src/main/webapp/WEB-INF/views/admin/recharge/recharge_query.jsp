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
<title>充值记录列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/recharge/recharge_list.js"></script>
<style type="text/css">
    .frontImg {
        width: 200px;
    }
</style>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
<shiro:hasPermission name="sys:finance:recharge:view">
<div id="tabWindow" class="easyui-tabs" style="height:auto;">
    <div title="支付宝充值记录列表" style="padding:1px;">
        <table id="dg001" class="easyui-datagrid"
              width="100%"
             url="${ctx}/admin/recharge/alibaPaylistData" pagination="true"
             data-options="checkOnSelect:true"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
					<th field="mobileNo" width="150">手机号 </th>
					<th field="tname" width="150">用户姓名</th>
					<th field="account" width="150">支付宝账号</th>
					<th field="money" width="150">充值表单金额</th>
					<th field="actualMoney" width="150">实际到账金额 </th>
					<th field="status" width="150">充值状态 </th>
					<th field="oktime" width="150">充值时间</th>
					<th field="source" width="150">来源网站</th>
	            </tr>
	        </thead>
        </table>
    </div>
    <div title="银行转账充值记录列表" style="padding:1px;">
        <table id="dg002" class="easyui-datagrid" width="100%" 
             url="${ctx}/admin/recharge/bankListData" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
				<th field="mobileNo" width="150">手机号 </th>
				<th field="tname" width="150">用户姓名</th>
				<th field="tradeNo" width="150">流水号</th>
				<th field="tradeAccountBank" width="150">收款银行</th>
				<th field="actualMoney" width="150">实际到账金额 </th>
				<th field="status" width="150">充值状态 </th>
				<th field="oktime" width="150">充值时间</th>
				<th field="source" width="150">来源网站</th>
            </tr>
        </thead>
    </table>
   
    </div>
    
     <div title="充值记录查询" style="padding:1px;">
        <table id="dg003" class="easyui-datagrid" width="100%" toolbar="#dg003Toolbar" url="${ctx}/admin/recharge/listRecharge" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
	                <!-- <th field="id" data-options="checkbox:true"></th> -->
					<th field="mobile" width="150">手机号 </th>
					<th field="tname" width="150">用户姓名</th>
					<th field="rechargeID" width="150">订单号</th>
					<th field="tradeNo" width="150">流水号</th>
					<th field="tradeAccountBankStr" width="150">交易银行</th>
					<th field="moneyStr" width="150">充值表单金额</th>
					<th field="typeStr" width="150">支付类型</th>
					<th field="paymentChannelStr" width="150">网银通道</th>
					<th field="addtimeStr" width="150">提交时间</th>
					<th field="actualMoneyStr" width="150">实际到账金额 </th>
					<th field="statusStr" width="150">充值状态 </th>
					<th field="okTimeStr" width="150">审核时间</th>
					<th field="sourceStr" width="150">来源网站</th>
	            </tr>
	        </thead>
    	</table>
    	<div id="dg003Toolbar">
	        <form id="queryForm" method="post">
		     <table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
		       <tr>
	                <td class="label right">用户姓名:</td>
	                <td>
	                    <input type="text" name="tname" />
	                </td>
	                <td class="label right">手机号:</td>
	                <td>
	                <input type="text" name="mobile" />
	                </td>
	            </tr>
	            <tr>
	                <td class="label right">支付类型:</td>
	                <td>
	                <input class="easyui-combobox" id="recharge_type" type="text" name="type" 
                       data-options="valueField:'id',textField:'text',url:'${ctx}/admin/recharge/dataMapCombobox?key=paytype&excludes=5'" />
	                </td>
	                <td class="label right">支付状态:</td>
	                <td>
	                    <select class="easyui-combobox" name="statusValue">
	                        <option value="">--请选择--</option>
	                        <option value="21">充值成功</option>
	                        <option value="1">失败</option>
	                        <option value="0">待处理</option>
	                    </select>
	                </td>
	            </tr>
	            <tr>
	                <td class="label right">充值时间:</td>
	                <td>
	                    <table class="many">
	                        <tr>
	                            <td><input class="easyui-datetimebox" name="oktimeStr_start"></td>
	                            <td>至</td>
	                            <td><input class="easyui-datetimebox" name="oktimeStr_end"></td>
	                        </tr>
	                    </table>
	                </td>
	                <td class="label right">订单号:</td>
	                <td>
	                    <input type="text" name="rechargeID" />
	                </td>
	            </tr>
	            <tr>
	            	<td class="label right">网银通道:</td>
	                <td>
	                    <select class="easyui-combobox" name="paymentChannel">
	                        <option value="">--请选择--</option>
	                        <option value="1">联动优势</option>
	                        <option value="2">币币支付</option>
	                    </select>
	                </td>
	                <td class="label right">来源网站:</td>
	                <td>
	                	<select class="easyui-combobox" name="source">
	                        <option value="">所有网站</option>
	                        <option value="1">维胜</option>
	                        <option value="2">配股宝</option>
	                    </select>
	                </td>
	            </tr>
	            <tr>
	                <td class="label">&nbsp;</td>
	                <td colspan="3">
		                <a id="btn" href="#" onclick="$.easyui.datagridQuery('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		                <a id="btn0012" href="#" onclick="$.easyui.exportExcel('dg003','queryForm')" class="easyui-linkbutton" data-options="iconCls:'icon-excel'">导出</a>
	                </td>
	            </tr>
	          </table>
	        </form>
	    </div>
    </div>
    
     
</div>
 </shiro:hasPermission>
   <shiro:lacksPermission name="sys:finance:recharge:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>