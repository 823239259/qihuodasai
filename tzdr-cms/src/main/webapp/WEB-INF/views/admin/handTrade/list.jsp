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
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/handTrade/list.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:handTrade:view">
	<div id="handTradeTab" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true" style="margin-top: 5px;">
			<div title="待审核列表" data-options="tools:'#p-tools'" style="padding:20px;">
					<div id="audittb" style="padding: 5px; height: auto">
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:handTrade:create">  
							<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-add"  plain="true" onclick="baseUtils.openAddIframeWin(650,450,'新增方案','handTrade')">新增方案</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="sys:riskmanager:handTrade:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="handTrade.auditTrade(true)">审核通过</a>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="sys:riskmanager:handTrade:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"   plain="true" onclick="handTrade.auditTrade(false)">审核不通过</a>
						</shiro:hasPermission>
						
					</div>
				</div>
				<table id="edatagrid" class="easyui-datagrid"
		            width="100%" pagination="true"
		            data-options="checkOnSelect:true,toolbar:'#audittb',
		            queryParams:{search_EQ_auditStatus:0},
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
							<th field="account" width="150">恒生帐号</th>
							<th field="leverMoney" width="150">保证金</th>
							<th field="lever" width="100">配资杠杆</th>
							<th field="naturalDays" width="100">使用期限</th>
							<th field="auditStatusValue" width="150">审核状态</th>
							<th field="createUser" width="150" sortable="true">创建人</th>
							<th field="createTimeValue" width="200">创建时间</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			<div title="已审核列表" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="hasAaudittb" style="padding: 5px; height: auto">
					<div>
						<form id="searchForm">
							<input type="hidden" name="search_GT_auditStatus" value="0">
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td class="label right">
										<span>手机号：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
									</td>
									<td class="label right">
										<span>方案编号：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_groupId" name="search_LIKE_groupId">					
									</td>
								</tr>
								<tr>
									<td class="label right">
										<span>审核状态：</span>
									</td>
									<td>
										<select id="search_EQ_auditStatus" class="easyui-combobox" name="search_EQ_auditStatus" style="width:100px;height:25px;">
										    <option value="">请选择</option>
										    <option value="1">通过</option>
										    <option  value="2">未通过</option>
										</select>
									</td>
									<td class="label right">
										<span> </span>
									</td>
									<td>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('hasAuditData','searchForm')">查询</a>
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
		            queryParams:{search_GT_auditStatus:0},
		            onLoadSuccess:function(data){
						datagridUtils.loadSuccess(data,'hasAuditData');
					}"
		            rownumbers="true" fitColumns="true" singleSelect="true">
			        <thead>
			            <tr>
			            	<th field="id" width="150" hidden="true"></th>
							<th field="mobile" width="180" sortable="true">手机号码</th>
							<th field="tname" width="150">客户姓名</th>
							<th field="groupId" width="150">方案编号</th>
							<th field="account" width="150">恒生帐号</th>
							<th field="leverMoney" width="150">保证金</th>
							<th field="lever" width="100">配资杠杆</th>
							<th field="naturalDays" width="100">使用期限</th>
							<th field="auditStatusValue" width="150">审核状态</th>
							<th field="auditUser" width="150" sortable="true">审核人</th>
							<th field="auditTimeValue" width="200">审核时间</th>
			            </tr>
			        </thead>
   				</table>
			</div>
			
				<div title="审核通过划款失败列表" data-options="tools:'#p-tools'" style="padding:20px;">
				<div id="transferFailtb" style="padding: 5px; height: auto">
					<div>
						<form id="transferFailSearchForm">
							<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td class="label right">
										<span>手机号：</span>
									</td>
									<td>
										<input class="easyui-validatebox" id="search_LIKE_mobile" name="search_LIKE_mobile">					
									</td>
									
									<td class="label right">
										<span> </span>
									</td>
									<td>
										<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('transferFailData','transferFailSearchForm')">查询</a>
									</td>
								</tr>
								
							</table>	
						</form>	
					</div>
					<div style="margin-bottom: 5px">
						<shiro:hasPermission name="sys:riskmanager:handTrade:audit">  
							<a href="javascript:void(0)" class="easyui-linkbutton"  iconCls="icon-add"  plain="true" onclick="handTrade.afterHandTransfer()">已处理</a>
						</shiro:hasPermission>
					</div>
				</div>
				<table id="transferFailData" class="easyui-datagrid"
		            width="100%"
		            pagination="true"
		            data-options="checkOnSelect:true,toolbar:'#transferFailtb',
		             singleSelect:true,
					frozenColumns:[[
			            {field:'ck',checkbox:true}
					]],
		            onLoadSuccess:function(data){
						datagridUtils.loadSuccess(data,'transferFailData');
					}"
		            rownumbers="true" fitColumns="true" singleSelect="true">
			        <thead>
			            <tr>
			            	<th field="id" width="150" hidden="true"></th>
							<th field="account" width="150">恒生帐号</th>
							<th field="mobile" width="150" sortable="true">手机号码</th>
							<th field="uname" width="150">用户姓名</th>
							<th field="totalLeverMoney" width="150">风险保证金</th>
							<th field="totalLending" width="150">配资金额</th>
							<th field="totalOperateMoney" width="150">总操盘资金</th>
							<th field="warning" width="150">亏损补仓线</th>
							<th field="open" width="150" sortable="true">亏损平仓线</th>
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
	<shiro:lacksPermission name="sys:riskmanager:handTrade:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>