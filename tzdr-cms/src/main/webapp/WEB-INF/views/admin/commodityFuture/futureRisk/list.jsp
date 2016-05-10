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
<title>登录</title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript"
	src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/commodityFuture/futureRisk/list.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:view">
		<div id="commodityRiskTab" class="easyui-tabs"
			data-options="tools:'#tab-tools',border:false,fit:true"
			style="margin-top: 5px;">
			<div title="申请列表" data-options="tools:'#p-tools'"
				style="padding: 20px;">
				<div id="applyTool" style="padding: 5px; height: auto">
					<form id="applySearchForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>客户姓名：</span></td>
								<td><input class="easyui-validatebox"
									id="search_LIKE_username" name="search_LIKE_username">
								</td>
								<td class="label right"><span>手机号码：</span></td>
								<td><input class="easyui-validatebox"
									id="search_LIKE_mobile" name="search_LIKE_mobile"></td>
							</tr>
							<tr>
								<td class="label right"><span>申请时间：</span></td>
								<td><input id="startAppTime" name="search_date_GTE_appTime"
									class="Wdate" type="text"
									onFocus="var endTime=$dp.$('endAppTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endAppTime\')}'})" />
									至 <input id="endAppTime" name="search_date_LTE_appTime"
									class="Wdate" type="text"
									onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppTime\')}'})" />
								</td>
								<td class="label right"><span>交易品种：</span></td>
								<td><select id="search_IN_businessType"
									class="easyui-combobox" name="search_IN_businessType"
									style="width: 100px; height: 25px;">
										<option value="1,2,3,4,20">全部</option>
										<option value="1">沪金</option>
										<option value="2">沪银</option>
										<option value="3">沪铜</option>
										<option value="4">橡胶</option>
										<option value="20">商品综合</option>
								</select></td>
							</tr>
							<tr>
								<td class="label right"><span> </span></td>
								<td colspan="3" class="label"><a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search"
									onclick="datagridUtils.datagridQuery('applyGrid','applySearchForm')">查询</a>
								</td>
							</tr>
						</table>
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:audit">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit"
								onclick="futureRiskOptions.toAccount(300, 170, '分配账号', 1)">分配账号</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:audit">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit"
								onclick="futureRiskOptions.refuseApply()">拒绝申请</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:view">
							<a href="javascript:void(0)" class="easyui-linkbutton" 
								iconCls="icon-excel" 
								onclick="$.easyui.exportExcel('applyGrid','applySearchForm')">导出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:audit">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit"
								onclick="futureRiskOptions.toAccount(300, 170, '分配账号', 2)">修改</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="applyGrid" width="100%"></table>
			</div>
			<div title="补充保证金" data-options="tools:'#p-tools'"
				style="padding: 20px;">
				<div id="bondTool" style="padding: 5px; height: auto">
					<form id="bondSearchForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>操盘账号：</span></td>
								<td><input class="easyui-validatebox"
									id="search_LIKE_tranAccount" name="search_LIKE_tranAccount">
								</td>
								<td class="label right"><span>申请追加时间：</span></td>
								<td><input id="startAppendDate" name="search_date_GTE_appendDate"
									class="Wdate" type="text"
									onFocus="var endDate=$dp.$('endAppendDate');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endAppendDate\')}'})" />
									至 <input id="endAppendDate" name="search_date_LTE_appendDate"
									class="Wdate" type="text"
									onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppendDate\')}'})" />
								</td>
							</tr>
							<tr>
								<td class="label right"><span>交易品种：</span></td>
								<td><select id="search_IN_type"
									class="easyui-combobox" name="search_IN_type"
									style="width: 100px; height: 25px;">
										<option value="1,2,3,4,20">全部</option>
										<option value="1">沪金</option>
										<option value="2">沪银</option>
										<option value="3">沪铜</option>
										<option value="4">橡胶</option>
										<option value="20">商品综合</option>
								</select></td>
								<td class="label right"><span>状态：</span></td>
								<td><select id="search_IN_status"
									class="easyui-combobox" name="search_IN_status"
									style="width: 100px; height: 25px;">
										<option value="0,1">全部</option>
										<option value="0">未处理</option>
										<option value="1">已处理</option>
								</select></td>
							</tr>
							<tr>
								<td class="label right"><span> </span></td>
								<td colspan="3" class="label"><a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search"
									onclick="datagridUtils.datagridQuery('bondGrid','bondSearchForm')">查询</a>
								</td>
							</tr>
						</table>
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:audit">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit"
								onclick="futureRiskOptions.changeStatus()">已处理</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:view">
							<a href="javascript:void(0)" class="easyui-linkbutton" 
								iconCls="icon-excel" 
								onclick="$.easyui.exportExcel('bondGrid','bondSearchForm')">导出</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="bondGrid" width="100%"></table>
			</div>
			<div title="方案管理" data-options="tools:'#p-tools'"
				style="padding: 20px;">
				<div id="planTool" style="padding: 5px; height: auto">
					<form id="planSearchForm" method="post">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>手机号码：</span></td>
								<td><input class="easyui-validatebox"
									id="search_LIKE_mobile" name="search_LIKE_mobile"></td>
								<td class="label right"><span>启用时间：</span></td>
								<td><input id="startAppStartTime" name="search_date_GTE_appStartTime"
									class="Wdate" type="text"
									onFocus="var endDate=$dp.$('endAppStartTime');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endAppStartTime\')}'})" />
									至 <input id="endAppStartTime" name="search_date_LTE_appStartTime"
									class="Wdate" type="text"
									onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppStartTime\')}'})" />
								</td>
							</tr>
							<tr>
								<td class="label right"><span>客户姓名：</span></td>
								<td><input class="easyui-validatebox"
									id="search_LIKE_username" name="search_LIKE_username">
								</td>
								<td class="label right"><span>方案申请时间：</span></td>
								<td><input id="startAppTime2" name="search_date_GTE_appTime"
									class="Wdate" type="text"
									onFocus="var endDate=$dp.$('endAppTime2');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endAppTime2\')}'})" />
									至 <input id="endAppTime2" name="search_date_LTE_appTime"
									class="Wdate" type="text"
									onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppTime2\')}'})" />
								</td>
							</tr>
							<tr>
								<td class="label right"><span>操盘账号：</span></td>
								<td><input class="easyui-validatebox"
									id="search_LIKE_tranAccount" name="search_LIKE_tranAccount">
								</td>
								<td class="label right"><span>申请结算时间：</span></td>
								<td><input id="startAppEndTime" name="search_date_GTE_appEndTime"
									class="Wdate" type="text"
									onFocus="var endDate=$dp.$('endAppEndTime');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endAppEndTime\')}'})" />
									至 <input id="endAppEndTime" name="search_date_LTE_appEndTime"
									class="Wdate" type="text"
									onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppEndTime\')}'})" />
								</td>
							</tr>
							<tr>
								<td class="label right"><span>交易品种：</span></td>
								<td><select id="search_IN_businessType"
									class="easyui-combobox" name="search_IN_businessType"
									style="width: 100px; height: 25px;">
										<option value="1,2,3,4,20">全部</option>
										<option value="1">沪金</option>
										<option value="2">沪银</option>
										<option value="3">沪铜</option>
										<option value="4">橡胶</option>
										<option value="20">商品综合</option>
								</select></td>
								<td class="label right"><span>结算状态：</span></td>
								<td><select id="search_IN_stateType"
									class="easyui-combobox" name="search_IN_stateType"
									style="width: 100px; height: 25px;">
										<option value="2,3,4,6">全部</option>
										<option value="4">操盘中</option>
										<option value="2">申请结算</option>
										<option value="3">待结算</option>
										<option value="6">已结算</option>
								</select></td>
							</tr>
							<tr>
								
							</tr>
							<tr>
								<td class="label right"><span> </span></td>
								<td colspan="5" class="label"><a href="javascript:void(0)" class="easyui-linkbutton"
									iconCls="icon-search"
									onclick="datagridUtils.datagridQuery('planGrid','planSearchForm')">查询</a>
								</td>
							</tr>
						</table>
					</form>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:view">
							<a href="javascript:void(0)" class="easyui-linkbutton" 
								iconCls="icon-excel" 
								onclick="$.easyui.exportExcel('planGrid','planSearchForm')">导出</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:audit">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit"
								onclick="futureRiskOptions.toResult(450, 300, '录入结果', 2)">录入结果</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:commodityFutureRisk:audit">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-edit"
								onclick="futureRiskOptions.settle()">结算</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="planGrid" width="100%"></table>
			</div>
		</div>
		<!-- ADD window -->
		<div id="addWin" style="padding: 10px; top: 20px;"></div>
		<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:commodityFutureRisk:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>