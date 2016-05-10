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
<script type="text/javascript" src="${ctx}/static/script/userTrade/allList.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:allTrades:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
		<form id="searchForm" method="post">
			<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
				
				<tr>
				<input type="hidden" name="first" value="1">
					<td class="label right">
						<span>配资保证金：</span>
					</td>
					<td colspan="2">
						<input name="search_GTE_totalLeverMoney" class="easyui-validatebox" id="totalLeverMoney1"  data-options="validType:'money'">
						-
						<input name="search_LTE_totalLeverMoney" class="easyui-validatebox" id="totalLeverMoney2"  data-options="validType:'money'">
					</td>
					<td class="label right">
						<span>方案结束时间：</span>
					</td>
					<td colspan="2">
						<input name="search_date_GTE_endtime"  id="endtime1" class="Wdate" type="text" onFocus="var endtime2=$dp.$('endtime2');WdatePicker({onpicked:function(){endtime2.focus();},maxDate:'#F{$dp.$D(\'endtime2\')}'})"/>
					    - <input name="search_date_LTE_endtime" id="endtime2" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'endtime1\')}'})"/>
					</td>
				</tr>
				<tr>
					<td class="label right">
						<span>配资金额：</span>
					</td>
					<td colspan="2">
						<input name="search_GTE_totalLending" class="easyui-validatebox" id="totalLending1"  data-options="validType:'money'">
						-
						<input name="search_LTE_totalLending" class="easyui-validatebox" id="totalLending2"  data-options="validType:'money'">
					</td>
					<td class="label right">
						<span>方案开始时间：</span>
					</td>
					<td colspan="2">
						<input name="search_date_GTE_starttime" id="starttime1" class="Wdate" type="text" onFocus="var starttime2=$dp.$('starttime2');WdatePicker({onpicked:function(){starttime2.focus();},maxDate:'#F{$dp.$D(\'starttime2\')}'})"/>
					    - <input name="search_date_LTE_starttime" id="starttime2" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'starttime1\')}'})"/>
					</td>
				</tr>
				<tr>
					<td class="label right">
					  <span>累计追加保证金：</span>
					</td>
					<td colspan="2">
						<input name="search_GTE_totalAppendLeverMoney" class="easyui-validatebox" id="totalAppendLeverMoney1"  data-options="validType:'money'">
						-
						<input name="search_LTE_totalAppendLeverMoney" class="easyui-validatebox" id="totalAppendLeverMoney2"  data-options="validType:'money'">
					</td>
					<!-- <td class="label right">
					<span>累计提取利润：</span>
					</td>
					<td colspan="2">
						<input name="search_GTE_allExtractableProfit" class="easyui-validatebox" id="allExtractableProfit1"  data-options="validType:'money'">
						-
						<input name="search_LTE_allExtractableProfit" class="easyui-validatebox" id="allExtractableProfit2"  data-options="validType:'money'">
					</td> -->
					
					<td class="label right">
						<span>预计结束时间：</span>
					</td>
					<td colspan="2">
						<input name="search_date_GTE_estimateEndtime" id="estimateEndtime1" class="Wdate" type="text" onFocus="var estimateEndtime2=$dp.$('estimateEndtime2');WdatePicker({onpicked:function(){estimateEndtime.focus();},maxDate:'#F{$dp.$D(\'estimateEndtime2\')}'})"/>
					    - <input name="search_date_LTE_estimateEndtime" id="estimateEndtime2" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'estimateEndtime1\')}'})"/>
					</td>
					
				</tr>
				<tr>
					<td class="label right">
						<span>方案状态：</span>
					</td>
					<td colspan="2">
						<input class="easyui-combobox" id="tradeStatus" name="search_EQ_status" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=tradeStatus',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
					</td>
					<td class="label right">
						<span>方案创建时间：</span>
					</td>
					<td colspan="2">
						<input name="search_date_GTE_addtime" id="addtime1" class="Wdate" type="text" onFocus="var addtime2=$dp.$('addtime2');WdatePicker({onpicked:function(){addtime2.focus();},maxDate:'#F{$dp.$D(\'addtime2\')}'})"/>
					    - <input name="search_date_LTE_addtime" id="addtime2" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'addtime1\')}'})"/>
					</td>
				</tr>
				<tr>
					<td class="label right">
						<span>手机号码：</span>
					</td>
					<td colspan="2">
					<input name="search_LIKE_mobile" class="easyui-validatebox" id="moblie">
					</td>
					<td class="label right">
						<span>交易帐号：</span>
					</td>
					<td colspan="2">
					<input name="search_LIKE_account" class="easyui-validatebox" id="account">
					</td>
				</tr>
				<tr>
					<td class="label right">
						<span>交易账户名：</span>
					</td>
					<td colspan="2">
					<input name="search_LIKE_accountName" class="easyui-validatebox" id="accountName">
					</td>
					<td class="label right">
						<span>用户真实姓名：</span>
					</td>
					<td colspan="2">
					<input name="search_LIKE_uname" class="easyui-validatebox" id="uname">
					</td>
				</tr>
				<tr>
				<td class="label right">
						<span>交易账号类型：</span>
					</td>
					<td colspan="2">
						<input class="easyui-combobox"    id="feeType" name="search_IN_feeType" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=feetype',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
					</td>
					<td class="label right">
						<span>方案类型：</span>
					</td>
					<td colspan="2">
					<input class="easyui-combobox" id="activityType" name="search_EQ_activityType" data-options="valueField: 'id', textField:'text',
	                     url:'${ctx}/admin/component/dictCombobox?typeKey=activityType'">
					</td>
				</tr>
				<tr>
					<td class="label right">
						<span>用户类型：</span>
					</td>
					<td colspan="5">
						<select class="easyui-combobox" id="userType" name="search_EQ_userType" style="width:174px;">
							<option value="">--请选择--</option>
							<option value="0">普通用户</option>
							<option value="1">8800用户</option>
							<option value="2">6600用户</option>
						</select>
					</td>
					
				</tr>
				<tr>
					<td class="label right"></td>
					<td colspan="5">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="allTrade.doSearch()">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('edatagrid','searchForm')">导出</a>
					</td>
				</tr>
			</table>
			</form>				
		</div>
		<div style="margin-bottom: 5px;margin-top: 10px;">
			<shiro:hasPermission name="sys:riskmanager:allTrades:view">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="allTrade.openChildDetail('tradeDetail','','子方案列表')">查看子方案</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:riskmanager:allTrades:view">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="allTrade.openChildDetail('getUserFund',1,'利润提取记录')">利润提取记录</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:riskmanager:allTrades:view">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="allTrade.openChildDetail('getUserFund',2,'添加保证金记录')">添加保证金记录</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:riskmanager:allTrades:view">  
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="allTrade.openAgentInfo('admin/allTrades/queryThing','查看代理体系')">查看代理体系</a>
			</shiro:hasPermission>
		</div>
	</div>
	<table id="edatagrid"></table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:allTrades:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>