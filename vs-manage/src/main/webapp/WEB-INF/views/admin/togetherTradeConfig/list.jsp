<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>股票合买参数</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/script/togetherTradeConfig/list.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:settingParams:togetherTradeConfig:view">
		<div>
			<shiro:hasPermission name="sys:settingParams:togetherTradeConfig:update">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" 
					onclick="togetherTradeConfigParamsOptions.update('admin/config/togetherTrade/updateParams')">保存</a>
			</shiro:hasPermission>
		</div>
		<div>
			<form method="post" id="addForm">
				<input type="hidden" value="${togetherConfig.id}" id="id" name="id" />
				<input type="hidden" value="${togetherConfig.version}" id="version" name="version" />
				<input type="hidden" value="${togetherConfig.createUser}" id="createUser" name="createUser" />
				<input type="hidden" value="${togetherConfig.createUserId}" id="createUserId" name="createUserId" />
				<input type="hidden" value="${togetherConfig.createTime}" id="createTime" name="createTime" />
				<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
				<table class="conn" border="0" width="100%" cellpadding="0" cellspacing="0" class="conn">
					<tr>
						<td class="label right">合买利息系数:</td>
						<td>
							<input class="easyui-numberbox"	value="${togetherConfig.foenusRatio}" id="foenusRatio" name="foenusRatio" 
								data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">合买管理费系数:</td>
						<td colspan="3">
							<input class="easyui-numberbox"	value="${togetherConfig.manageRatio}" id="manageRatio" name="manageRatio"	
								data-options="required:true,precision:2,min:0" />
						</td>
					</tr>
					<tr>
						<td class="label right">最小总操盘资金（元）:</td>
						<td>
							<input class="easyui-numberbox" value="${togetherConfig.minMoney}" id="minMoney" name="minMoney" 
								data-options="required:true,precision:2,min:250" />
						</td>
						<td class="label right">最大总操盘资金（元）:</td>
						<td>
							<input class="easyui-numberbox" value="${togetherConfig.maxMoney}" id="maxMoney" name="maxMoney" 
								data-options="required:true,precision:2,min:250" />
						</td>
						<td class="label right">推荐操盘金额:</td>
						<td>
							<input class="easyui-validatebox" value="${togetherConfig.recommendMoney}" id="recommendMoney" name="recommendMoney" 
								data-options="required:true" /><br/>
							<span style="color: red;">请使用英文分隔符“;”，形如：10000;50000;100000;300000;500000</span>
						</td>
					</tr>
					<tr>
						<td class="label right">合买者出资倍数:</td>
						<td>
							<input class="easyui-validatebox" value="${togetherConfig.moneyRatio}" id="moneyRatio" name="moneyRatio" 
								data-options="required:true" /><br/>
							<span style="color: red;">请使用英文分隔符“;”，形如：6;5;4;3;2;1</span>
						</td>
						<td class="label right">推荐操盘天数:</td>
						<td colspan="3">
							<input class="easyui-validatebox" value="${togetherConfig.recommendDay}" id="recommendDay" name="recommendDay" 
								data-options="required:true" /><br/>
							<span style="color: red;">请使用英文分隔符“;”，形如：30;25;20;15;10</span>
						</td>
					</tr>
					<tr>
						<td class="label right">合买者分成系数:</td>
						<td>
							<input class="easyui-numberbox" value="${togetherConfig.profitRatio}" id="profitRatio" name="profitRatio" 
								data-options="required:true,precision:4,min:0" />
						</td>
						<td colspan="4">&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
	</shiro:hasPermission>
	<!-- window -->
	<shiro:lacksPermission name="sys:settingParams:togetherTradeConfig:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>