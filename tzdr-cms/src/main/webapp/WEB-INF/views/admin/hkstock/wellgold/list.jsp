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
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/hkstock/wellgold/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:hkwellgold:view">
	<div id="wellGoldTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="待审核方案列表" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="audittb" style="padding: 5px; height: auto">
						<form id="auditSearchForm" method="post">
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td class="label right">
										<span>用户姓名：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname">					
									</td>
									<td class="label right">
										<span>手机号：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span>提交时间：</span>
									</td>
									<td>
										<input id="startTime" name="search_date_GTE_createTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
									      至  <input id="endTime" name="search_date_LTE_createTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>							      
									</td>
									<td class="label right">
										<span>交易开始时间：</span>
									</td>
									<td>
										<select id="search_EQ_tradeStart" class="easyui-combobox" name="search_EQ_tradeStart" style="width:100px;height:25px;">
										    <option value="">请选择</option>
										    <option value="0">立即交易</option>
										    <option  value="1">下个交易日</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span> </span>
									</td>
									<td >
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','auditSearchForm')">查询</a>
									</td>
								</tr>
							</table>	
						</form>	
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:hkwellgold:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="wellGold.auditTrade(true)">审核通过</a>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="sys:riskmanager:hkwellgold:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="wellGold.auditTrade(false)">审核不通过</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="edatagrid" class="easyui-datagrid"
		            width="100%" pagination="true"
		            data-options="checkOnSelect:true,toolbar:'#audittb',
		            singleSelect:true,
					frozenColumns:[[
			            {field:'ck',checkbox:true}
					]],
		            onLoadSuccess:function(data){
						datagridUtils.loadSuccess(data,'edatagrid');
					}"
		            rownumbers="true" fitColumns="true" singleSelect="true">
			        <thead>
			            <tr>
			            	<th field="id" hidden="true"></th>
			            	<th field="tradeId"  hidden="true"></th>
							<th field="mobile" width="180" sortable="true">手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="groupId" width="150">方案编号</th>
							<th field="leverMoney" width="150">操盘保证金(元)</th>
							<th field="money" width="150">配资金额(港元)</th>
							<th field="totalLeverMoney" width="150">总操盘资金(港元)</th>
							<th field="warning" width="150">亏损警戒线(港元)</th>
							<th field="openline" width="150">亏损平仓线(港元)</th>
							<th field="createTimeValue" width="180">提交时间</th>
							<th field="tradeStart" width="180">交易开始时间</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			<div title="已审核记录" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="hasAaudittb" style="padding: 5px; height: auto">
					<div>
						<form id="searchForm" method="post">
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td class="label right">
										<span>用户姓名：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_tname" name="search_LIKE_tname">					
									</td>
									<td class="label right">
										<span>手机号：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
									</td>
									<td class="label right">
										<span>提交时间：</span>
									</td>
									<td>
										<input id="startTime1" name="search_date_GTE_createTime" class="Wdate" type="text" onFocus="var endTime1=$dp.$('endTime1');WdatePicker({onpicked:function(){endTime1.focus();},maxDate:'#F{$dp.$D(\'endTime1\')}'})"/>
									      至  <input id="endTime1" name="search_date_LTE_createTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime1\')}'})"/>							      
									</td>
								<tr>
									<td class="label right">
										<span>审核状态：</span>
									</td>
									<td>
										<select id="search_EQ_auditStatus" class="easyui-combobox" name="search_EQ_auditStatus" style="width:100px;height:25px;">
										    <option value="">请选择</option>
										    <option  value="0">待审核</option>
										    <option value="1">通过</option>
										    <option  value="2">未通过</option>
										</select>
									</td>
									<td class="label right">
										<span>审核人：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_auditUser" name="search_LIKE_auditUser">					
									</td>
									<td class="label right">
										<span>审核时间：</span>
									</td>
									<td>
										<input id="startTime2" name="search_date_GTE_auditTime" class="Wdate" type="text" onFocus="var endTime2=$dp.$('endTime2');WdatePicker({onpicked:function(){endTime2.focus();},maxDate:'#F{$dp.$D(\'endTime2\')}'})"/>
									      至  <input id="endTime2" name="search_date_LTE_auditTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime2\')}'})"/>							      
									</td>
								</tr>
								<tr>
									
									<td class="label right"> 账户交易通道:</td>
									<td>
										<input class="easyui-combobox" id="tradeAccount" name="search_EQ_tradeChannel" data-options="
												url:'${ctx}/admin/recharge/dataMapCombobox?key=hktradechannel',
												valueField:'id',
												panelHeight:100,
												textField:'text'">
									</td>
									<td class="label right">
										<span> </span>
									</td>
									<td >
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('hasAuditData','searchForm')">查询</a>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" onclick="$.easyui.exportExcel('hasAuditData','searchForm')">导出</a>
									</td>
								</tr>
							</table>	
						</form>	
					</div>
				</div>
				<table id="hasAuditData" class="easyui-datagrid"
		            width="100%"
		            pagination="true"
		            data-options="checkOnSelect:true,toolbar:'#hasAaudittb',
		            onLoadSuccess:function(data){
						datagridUtils.loadSuccess(data,'hasAuditData');
					}"
		            rownumbers="true" fitColumns="true" singleSelect="true">
			        <thead>
			            <tr>
			            	<th field="id" width="150" hidden="true"></th>
							<th field="mobile" width="180" sortable="true">手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="accountNo" width="150">交易账户</th>
							<th field="accountName" width="150">交易账户名</th>
							<th field="groupId" width="150">方案编号</th>
							<th field="leverMoney" width="150">操盘保证金(元)</th>
							<th field="money" width="150">配资金额(港元)</th>
							<th field="totalLeverMoney" width="160">总操盘资金(港元)</th>
							<th field="warning" width="180">亏损警戒线(港元)</th>
							<th field="openline" width="180">亏损平仓线(港元)</th>
							<th field="tradeChannelValue" width="150">账户交易通道</th>
							<th field="auditStatusValue" width="150">审核状态</th>
							<th field="createTimeValue" width="260">提交时间</th>
							<th field="auditUser" width="140" >审核人</th>
							<th field="auditTimeValue" width="260">审核时间</th>
			            </tr>
			        </thead>
   				</table>
			</div>
	</div>	
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;">	
	</div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:hkwellgold:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>