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
<title></title>
</head>
<body>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/script/common/public.js"></script>
	<form method="post" id="addForm">
		<input type="hidden" id="id" name="id" value="${fSimpleFtseUserTrade.id}">
		<table class="conn" border="0" width="100%" cellpadding="0" cellspacing="0"  class="conn" >
			<tr>
				<td class="label right">手机号码:</td>
				<td>
					<input class="easyui-validatebox" readOnly="true" value="${mobile}" id="mobile" name="mobile">
				</td>
			</tr>
			<tr>
				<td class="label right">操盘账号:</td>
				<td>
					<input class="easyui-validatebox" readOnly="true" value="${fSimpleFtseUserTrade.tranAccount}" id="tranAccount" name="tranAccount">
				</td>
			</tr>
			<tr>
				<td class="label right">操盘保证金:</td>
				<td>
					<input class="easyui-numberbox" readOnly="true" value="${fSimpleFtseUserTrade.traderBond}" id="traderBond" name="traderBond" data-options="precision:2">
				</td>
			</tr>
			<tr>
				<td class="label right">补充保证金:</td>
				<td>
					<input class="easyui-numberbox" readOnly="true" value="${fSimpleFtseUserTrade.appendTraderBond}" id="appendTraderBond" name="appendTraderBond" data-options="precision:2">
				</td>
			</tr>
			<tr>
				<td class="label right">交易盈亏:</td>
				<td>
					<input class="easyui-numberbox" value="${fSimpleFtseUserTrade.tranProfitLoss}" id="tranProfitLoss" name="tranProfitLoss" data-options="required:true,precision:2">
					<span style="color: red;">盈亏为负，需添加 “-”号</span>
				</td>
			</tr>
			<tr>
				<td class="label right">交易手续费:</td>
				<td>
					<input class="easyui-numberbox" value="${fSimpleFtseUserTrade.tranFeesTotal}" id="tranFeesTotal" name="tranFeesTotal" data-options="required:true,precision:2">
					<span style="color: red;">不能输入负数</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  onclick="futureRiskOptions.cancel()">取消</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="futureRiskOptions.saveOrUpdate('admin/commodityFutureRisk/editResult', 'planGrid')">提交</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>